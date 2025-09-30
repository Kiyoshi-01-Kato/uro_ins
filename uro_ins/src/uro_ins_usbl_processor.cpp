/****************************************************************/
/*                                                              */
/*          Advanced Navigation Packet Protocol Library         */
/*          C Language Static Subsonus SDK, Version 2.4         */
/*              Copyright 2020, Advanced Navigation             */
/*                                                              */
/****************************************************************/
/*
 * Copyright (C) 2020 Advanced Navigation
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

#include "rclcpp/rclcpp.hpp"
#include <cstdlib>
#include <cstdio>
#include <cstdint>
#include <cstring>
#include <cmath>
#include <chrono>
#include <thread>
#include <unistd.h>
#include <sys/types.h>
#include <sys/time.h>

#include "std_msgs/msg/float64_multi_array.hpp"
#include "std_msgs/msg/int32_multi_array.hpp"
#include "geometry_msgs/msg/point_stamped.hpp"
#include "geometry_msgs/msg/transform_stamped.hpp"
#include "interfaces/msg/bottom_track.hpp"
#include <tf2_ros/static_transform_broadcaster.h>
#include <tf2/LinearMath/Quaternion.h>
#include <visualization_msgs/msg/marker.hpp>
#include <GeographicLib/LocalCartesian.hpp>

#ifndef M_PI
#define M_PI (3.14159265358979323846)
#endif

#ifdef _WIN32
#include <windows.h>
#include <winsock2.h>
#include <ws2tcpip.h>
#else
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#endif

#include "uro_ins.hpp"
#include "uro_subsonus.hpp"

#define RADIANS_TO_DEGREES (180.0/M_PI)
#define DEGREES_TO_RADIANS (M_PI/180.0)

void UroIns::initialize()
{
  tf_init("map");
  tf_init("ship");
  tf_init("odom");
}

void UroIns::set_subsonus(std::shared_ptr<UroSubsonus> usbl)
{
  usbl_ = usbl;
}

void UroIns::set_ship_data(subsonus_system_state_packet_t surface)
{
  surface_ = surface;
  is_ship_data_received = true;
}

void UroIns::set_usbl_data(subsonus_track_packet_t subsea)
{
  subsea_old_ = subsea_;
  subsea_ = subsea;
  is_usbl_data_received = true;
}

void UroIns::usbl_on_timer()
{
  usbl_->receive_subsonus();
}


void UroIns::tf_init(std::string frame_name)
{
  geometry_msgs::msg::TransformStamped init_transform;
  auto now = this->get_clock()->now();
  init_transform.header.stamp = now;

  init_transform.transform.translation.x = 0;
  init_transform.transform.translation.y = 0;
  init_transform.transform.translation.z = 0;
  tf2::Quaternion quat;
  quat.setRPY(0.0, 0.0, 0.0);
  init_transform.transform.rotation.x = quat.x();
  init_transform.transform.rotation.y = quat.y();
  init_transform.transform.rotation.z = quat.z();
  init_transform.transform.rotation.w = quat.w();

  if (frame_name == "map"){
    init_transform.header.frame_id = "world";
    init_transform.child_frame_id = "map";
    static_tf_broadcast_->sendTransform(init_transform);
  }else if (frame_name == "ship"){
    init_transform.header.frame_id = "map";
    init_transform.child_frame_id = "ship";
    tf_broadcast_->sendTransform(init_transform);
  }else if (frame_name == "odom"){
    init_transform.header.frame_id = "map";
    init_transform.child_frame_id = "odom";
    tf_broadcast_->sendTransform(init_transform);
  }else{
    //何もしない
  }

}

void UroIns::publish_marker()
{
  evaluate_usbl_reliability();
  if (is_map_latlon_loaded == false){
    if (is_ship_data_received == false){
      return;
    }
    else{
      load_map_latlon();
      is_map_latlon_loaded = true;
    }
  }else{
    broadcast_ship_tf();
    draw_ship();
    broadcast_usbl_tf();
    draw_usbl();
  }
  
  is_ship_data_received = false;
  is_usbl_data_received = false;

  //対地速度が一度でも取得したらtrue
  if ((bottom_track_fom[0] < 1) && (bottom_track_fom[1] < 1)){
    bt_dvl_received = true;
  }
  //対地速度を一度も取得していない場合
  if (bt_dvl_received = false){
    broadcast_odom_tf("ship");
  }
  
}

void UroIns::evaluate_usbl_reliability()
{
  if (is_ship_data_received ==true){
    ship_marker_count = 0;
  }else{
    ship_marker_count++;
  }
  if (ship_marker_count >= 5){
    usbl_reliability_level = 0;
    return;
  }

  if (is_usbl_data_received ==true){
    usbl_marker_count = 0;
  }else{
    usbl_marker_count++;
  }
  if (usbl_marker_count >= 30){
    usbl_reliability_level = 1;
    return;
  }

  int elapsed_sec = subsea_.observer_unix_time_seconds - subsea_old_.observer_unix_time_seconds;
  int elapsed_us = subsea_.observer_microseconds - subsea_old_.observer_microseconds;
  float elapsed_time = elapsed_sec + elapsed_us * 0.001 * 0.001;

  float diff_x = subsea_.ned_position[0] - subsea_old_.ned_position[0];
  float diff_y = subsea_.ned_position[1] - subsea_old_.ned_position[1];
  float diff_distance = sqrt((diff_x * diff_x) + (diff_y * diff_y));

  float v_limit = 1.54332;  //3knot

  if (elapsed_time * v_limit < diff_distance){
    usbl_reliability_level = 2;
    return;
  }

  float elevation_deg = subsea_.elevation * RADIANS_TO_DEGREES;
  if (elevation_deg > 75){
    usbl_reliability_level = 3;
    return;
  }

  usbl_reliability_level = 4;

}

void UroIns::load_map_latlon()
{
  map_origin_lat = surface_.latitude * RADIANS_TO_DEGREES;
  map_origin_lon = surface_.longitude * RADIANS_TO_DEGREES;
  map_origin_h = surface_.height;
}


void UroIns::broadcast_ship_tf()
{
  using namespace GeographicLib;
  LocalCartesian prij(map_origin_lat, map_origin_lon, map_origin_h);
  double x, y, z;
  prij.Forward(surface_.latitude * RADIANS_TO_DEGREES, surface_.longitude * RADIANS_TO_DEGREES, surface_.height, x, y, z);

  auto now = this->get_clock()->now();
  geometry_msgs::msg::TransformStamped ship_transform;
  ship_transform.header.stamp = now;
  //ship_transform.header.stamp.sec = time.tv_sec;
  //ship_transform.header.stamp.nanosec = time.tv_nsec;
  ship_transform.header.frame_id = "map";
  ship_transform.child_frame_id = "ship";
  ship_transform.transform.translation.x = y;
  ship_transform.transform.translation.y = - x;
  ship_transform.transform.translation.z = 0;
  tf2::Quaternion quat;
  quat.setRPY(0.0, 0.0, - surface_.orientation[2]);
  ship_transform.transform.rotation.x = quat.x();
  ship_transform.transform.rotation.y = quat.y();
  ship_transform.transform.rotation.z = quat.z();
  ship_transform.transform.rotation.w = quat.w();
  tf_broadcast_->sendTransform(ship_transform);
  ship_tf = ship_transform;
}


void UroIns::draw_ship()
{
  auto now = this->get_clock()->now();
  visualization_msgs::msg::Marker marker_ship;
  marker_ship.header.frame_id = "ship";
  marker_ship.header.stamp = now;
  marker_ship.ns = "marker_ship";
  marker_ship.id = 0;
  marker_ship.type = visualization_msgs::msg::Marker::LINE_STRIP;
  marker_ship.action = visualization_msgs::msg::Marker::ADD;
  marker_ship.lifetime.sec = 100000;
  marker_ship.lifetime.nanosec = 0;

  marker_ship.scale.x = 0.1;
  float length = 23.36;
  float width = 5.4;
  geometry_msgs::msg::Point point;
  point.x = 0;
  point.y = 0;
  point.z = 0;
  marker_ship.points.push_back(point);
  point.x = width;
  point.y = -width/2;
  point.z = 0;
  marker_ship.points.push_back(point);
  point.x = 0;
  point.y = -width;
  point.z = 0;
  marker_ship.points.push_back(point);
  point.x = -length;
  point.y = -width;
  point.z = 0;
  marker_ship.points.push_back(point);
  point.x = -length;
  point.y = 0;
  point.z = 0;
  marker_ship.points.push_back(point);
  point.x = 0;
  point.y = 0;
  point.z = 0;
  marker_ship.points.push_back(point);

  marker_ship.color.a = 0.8f;
  if (usbl_reliability_level >= 1){
    marker_ship.color.r = 0.3f;
    marker_ship.color.g = 0.3f;
    marker_ship.color.b = 1.0f;
  }
  else{
    marker_ship.color.r = 0.0f;
    marker_ship.color.g = 0.0f;
    marker_ship.color.b = 0.0f;
  }
  marker_ship_->publish(marker_ship);
}

void UroIns::broadcast_usbl_tf()
{
  using namespace GeographicLib;
  LocalCartesian prij(map_origin_lat, map_origin_lon, map_origin_h);
  double x, y, z;
  prij.Forward(subsea_.latitude * RADIANS_TO_DEGREES, subsea_.longitude * RADIANS_TO_DEGREES, subsea_.height, x, y, z);
  usbl_from_map_xyz[0] = y;
  usbl_from_map_xyz[1] = - x;
  usbl_from_map_xyz[2] = - subsea_.depth;
  auto now = this->get_clock()->now();
  geometry_msgs::msg::TransformStamped usbl_transform;
  usbl_transform.header.stamp = now;
  //ship_transform.header.stamp.sec = time.tv_sec;
  //ship_transform.header.stamp.nanosec = time.tv_nsec;
  usbl_transform.header.frame_id = "map";
  usbl_transform.child_frame_id = "usbl";
  usbl_transform.transform.translation.x = usbl_from_map_xyz[0];
  usbl_transform.transform.translation.y = usbl_from_map_xyz[1];
  usbl_transform.transform.translation.z = usbl_from_map_xyz[2];
  tf2::Quaternion quat;
  //tfを同じ位置で更新するため、90degごとに回転
  float spin_90 = (M_PI / 2) * usbl_marker_count;
  quat.setRPY(0.0, 0.0, spin_90);
  usbl_transform.transform.rotation.x = quat.x();
  usbl_transform.transform.rotation.y = quat.y();
  usbl_transform.transform.rotation.z = quat.z();
  usbl_transform.transform.rotation.w = quat.w();
  tf_broadcast_->sendTransform(usbl_transform);

  auto usbl_posi = std_msgs::msg::Float64MultiArray();
  usbl_posi.data.push_back(y);
  usbl_posi.data.push_back(- x);
  usbl_posi.data.push_back(subsea_.elevation * RADIANS_TO_DEGREES);
  usbl_posi_->publish(usbl_posi);
}

void UroIns::draw_usbl()
{
  auto now = this->get_clock()->now();
  visualization_msgs::msg::Marker marker_usbl;
  marker_usbl.header.frame_id = "map";
  marker_usbl.header.stamp = now;
  marker_usbl.ns = "marker_usbl";
  marker_usbl.id = 0;
  marker_usbl.action = visualization_msgs::msg::Marker::ADD;
  marker_usbl.lifetime.sec = 100000;
  marker_usbl.lifetime.nanosec = 0;
  marker_usbl.scale.x = 1.0;
  marker_usbl.scale.y = 1.0;
  marker_usbl.scale.z = 1.0;

  marker_usbl.pose.position.x = usbl_from_map_xyz[0];
  marker_usbl.pose.position.y = usbl_from_map_xyz[1];
  marker_usbl.pose.position.z = usbl_from_map_xyz[2];

  marker_usbl.pose.orientation.x = 0;
  marker_usbl.pose.orientation.y = 0;
  marker_usbl.pose.orientation.z = 0;
  marker_usbl.pose.orientation.w = 1;
  marker_usbl.color.a = 0.7f;
  if (usbl_reliability_level == 4){
    marker_usbl.type = visualization_msgs::msg::Marker::SPHERE;
    marker_usbl.color.r = 0.0f;
    marker_usbl.color.g = 0.0f;
    marker_usbl.color.b = 0.7f;
  }
  else if (usbl_reliability_level == 3){
    marker_usbl.type = visualization_msgs::msg::Marker::SPHERE;
    marker_usbl.color.r = 0.8f;
    marker_usbl.color.g = 0.4f;
    marker_usbl.color.b = 0.0f;
  }
  else if (usbl_reliability_level == 2){
    marker_usbl.type = visualization_msgs::msg::Marker::SPHERE;
    marker_usbl.color.r = 0.5f;
    marker_usbl.color.g = 0.5f;
    marker_usbl.color.b = 0.5f;
  }
  else if (usbl_reliability_level == 1){
    marker_usbl.type = visualization_msgs::msg::Marker::CUBE;
    marker_usbl.color.r = 0.5f;
    marker_usbl.color.g = 0.5f;
    marker_usbl.color.b = 0.5f;
  }
  else if (usbl_reliability_level == 0){
    marker_usbl.type = visualization_msgs::msg::Marker::CUBE;
    marker_usbl.color.r = 0.0f;
    marker_usbl.color.g = 0.0f;
    marker_usbl.color.b = 0.0f;
  }
  else{
    return;
  }

  marker_usbl_->publish(marker_usbl);
}

void UroIns::broadcast_odom_tf(std::string str)
{
  geometry_msgs::msg::TransformStamped odom_transform = ship_tf;
  auto now = this->get_clock()->now();
  odom_transform.header.stamp = now;
  odom_transform.child_frame_id = "odom";
  if (str == "usbl"){

  }
  tf_broadcast_->sendTransform(odom_transform);
}

void UroIns::get_bottom_track(const interfaces::msg::BottomTrack::SharedPtr msg)
{
    bottom_track_fom[0] = msg->fom_x;
    bottom_track_fom[1] = msg->fom_y;
    bottom_track_fom[2] = msg->fom_z;
}
/*
void UroIns::get_usbl_position(const std::shared_ptr<std_srvs::srv::Trigger::Request> request,
          std::shared_ptr<std_srvs::srv::Trigger::Response>      response)
{
  //対地速度が一度でも取得したらtrue
  if ((bottom_track_fom[0] < 1) && (bottom_track_fom[1] < 1)){
    if (usbl_reliability_level >= 3){
      broadcast_odom_tf("usbl");
    }
  }
}
*/
void UroIns::set_gnss_point(const std::shared_ptr<uro_ins_msgs::srv::SetGnssPoint::Request> request,
          std::shared_ptr<uro_ins_msgs::srv::SetGnssPoint::Response>      response)
{
  response->success = true;
}