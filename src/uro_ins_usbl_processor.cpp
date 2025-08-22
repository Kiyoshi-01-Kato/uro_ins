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
#include "geometry_msgs/msg/point.hpp"
#include "geometry_msgs/msg/transform_stamped.hpp"
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
  //printf("receive usbl data\n");
  process_usbl_data();
}

void UroIns::usbl_on_timer()
{
  usbl_->receive_subsonus();
}

void UroIns::process_usbl_data()
{
  evaluate_usbl_reliability();
  printf("usbl_reliability_level = %d\n", usbl_reliability_level);
  /* 8月横須賀の暫定機能
  if (is_map_tf_created == true){
    broadcast_usbl_tf();
    draw_usbl();
  }
  */
  rov_pose_ = rov_pose_new_;
  //broadcast_ship_tf();
}

void UroIns::evaluate_usbl_reliability()
{
  int elapsed_sec = subsea_.observer_unix_time_seconds - subsea_old_.observer_unix_time_seconds;
  int elapsed_us = subsea_.observer_microseconds - subsea_old_.observer_microseconds;
  float elapsed_time = elapsed_sec + elapsed_us * 0.001 * 0.001;

  float diff_x = subsea_.ned_position[0] - subsea_old_.ned_position[0];
  float diff_y = subsea_.ned_position[1] - subsea_old_.ned_position[1];
  float diff_distance = sqrt((diff_x * diff_x) + (diff_y * diff_y));

  float v_limit = 1.54332;  //3knot

  /*
  printf("subsea_.ned_position[0] = %f\n", subsea_.ned_position[0]);
  printf("elapsed_time = %f\n", elapsed_time);
  printf("subsea_.observer_unix_time_seconds = %d\n", subsea_.observer_unix_time_seconds);
  printf("subsea_old_.observer_unix_time_seconds = %d\n", subsea_old_.observer_unix_time_seconds);
  printf("subsea_.latitude = %f\n", subsea_.latitude);
  */

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

/*8月横須賀の暫定
void UroIns::broadcast_usbl_tf()
{
  using namespace GeographicLib;
  LocalCartesian prij(map_origin_lat, map_origin_lon, map_origin_h);
  double x, y, z;
  //この出力はxが東、yが北
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
  quat.setRPY(0.0, 0.0, 0.0);
  usbl_transform.transform.rotation.x = quat.x();
  usbl_transform.transform.rotation.y = quat.y();
  usbl_transform.transform.rotation.z = quat.z();
  usbl_transform.transform.rotation.w = quat.w();
  tf_broadcast_->sendTransform(usbl_transform);
}
*/

/*8月横須賀の暫定
void UroIns::draw_usbl()
{
  auto now = this->get_clock()->now();
  visualization_msgs::msg::Marker marker_usbl;
  marker_usbl.header.frame_id = "map";
  marker_usbl.header.stamp = now;
  marker_usbl.ns = "marker_usbl";
  marker_usbl.id = 0;
  marker_usbl.type = visualization_msgs::msg::Marker::SPHERE;
  marker_usbl.action = visualization_msgs::msg::Marker::ADD;
  marker_usbl.lifetime.sec = 100000;
  marker_usbl.lifetime.nanosec = 0;
  marker_usbl.scale.x = 0.5;
  marker_usbl.scale.y = 0.5;
  marker_usbl.scale.z = 0.5;

  marker_usbl.pose.position.x = usbl_from_map_xyz[0];
  marker_usbl.pose.position.y = usbl_from_map_xyz[1];
  marker_usbl.pose.position.z = usbl_from_map_xyz[2];

  marker_usbl.pose.orientation.x = 0;
  marker_usbl.pose.orientation.y = 0;
  marker_usbl.pose.orientation.z = 0;
  marker_usbl.pose.orientation.w = 1;
  marker_usbl.color.a = 0.7f;
  if (usbl_reliability_level == 4){
    marker_usbl.color.r = 0.0f;
    marker_usbl.color.g = 0.6f;
    marker_usbl.color.b = 1.0f;
  }
  else if (usbl_reliability_level == 3){
    marker_usbl.color.r = 0.8f;
    marker_usbl.color.g = 0.4f;
    marker_usbl.color.b = 0.0f;
  }
  else if (usbl_reliability_level == 2){
    marker_usbl.color.r = 1.0f;
    marker_usbl.color.g = 0.0f;
    marker_usbl.color.b = 0.0f;
  }
  else{
    return;
  }
  printf("draw:usbl_reliability_level = %d\n", usbl_reliability_level);
  marker_usbl_->publish(marker_usbl);
}
*/

void UroIns::publish_ship_marker()
{
  //8月横須賀の暫定機能
  /*
  if (is_map_tf_created == false){
    if (is_ship_data_received == false){
      return;
    }
    else{
      broadcast_map_tf();
      is_map_tf_created = true;
    }
  }
  broadcast_ship_tf();
  draw_ship();
  */

  auto ship_latlon = std_msgs::msg::Float64MultiArray();
  ship_latlon.data.push_back(surface_.latitude * RADIANS_TO_DEGREES);
  ship_latlon.data.push_back(surface_.longitude * RADIANS_TO_DEGREES);
  ship_latlon_->publish(ship_latlon);
  //delete_ship();
  //std::this_thread::sleep_for(std::chrono::milliseconds(500));
  broadcast_ship_tf();
  draw_ship();
}

/* 8月横須賀の暫定
void UroIns::broadcast_map_tf()
{
  map_origin_lat = surface_.latitude * RADIANS_TO_DEGREES;
  map_origin_lon = surface_.longitude * RADIANS_TO_DEGREES;
  map_origin_h = surface_.height;

  auto now = this->get_clock()->now();

  geometry_msgs::msg::TransformStamped map_transform;
  map_transform.header.stamp = now;
  //map_transform.header.stamp.sec = time.tv_sec;
  //map_transform.header.stamp.nanosec = time.tv_nsec;
  map_transform.header.frame_id = "world";
  map_transform.child_frame_id = "map";
  map_transform.transform.translation.x = 0;
  map_transform.transform.translation.y = 0;
  map_transform.transform.translation.z = 0;
  tf2::Quaternion quat;
  quat.setRPY(0.0, 0.0, 0.0);
  map_transform.transform.rotation.x = quat.x();
  map_transform.transform.rotation.y = quat.y();
  map_transform.transform.rotation.z = quat.z();
  map_transform.transform.rotation.w = quat.w();
  static_tf_broadcast_->sendTransform(map_transform);
}
*/


void UroIns::broadcast_ship_tf()
{
  using namespace GeographicLib;
  LocalCartesian prij(map_origin_lat, map_origin_lon, map_origin_h);
  double x, y, z;
  //この出力はxが東、yが北
  prij.Forward(surface_.latitude * RADIANS_TO_DEGREES, surface_.longitude * RADIANS_TO_DEGREES, surface_.height, x, y, z);

  geometry_msgs::msg::TransformStamped ship_transform;
  ship_transform.header.stamp = this->get_clock()->now();
  //ship_transform.header.stamp.sec = time.tv_sec;
  //ship_transform.header.stamp.nanosec = time.tv_nsec;
  /* 8月横須賀の暫定機能
  ship_transform.header.frame_id = "map";
  ship_transform.child_frame_id = "ship";
  ship_transform.transform.translation.x = y;
  ship_transform.transform.translation.y = - x;
  ship_transform.transform.translation.z = 0;
  */
  ship_transform.header.frame_id = "world";
  ship_transform.child_frame_id = "ship";
  ship_transform.transform.translation.x = rov_pose_.x - subsea_.ned_position[0];
  ship_transform.transform.translation.y = rov_pose_.y - (- subsea_.ned_position[1]);
  ship_transform.transform.translation.z = 0;
  tf2::Quaternion quat;
  quat.setRPY(0.0, 0.0, - surface_.orientation[2]);
  ship_transform.transform.rotation.x = quat.x();
  ship_transform.transform.rotation.y = quat.y();
  ship_transform.transform.rotation.z = quat.z();
  ship_transform.transform.rotation.w = quat.w();
  tf_broadcast_->sendTransform(ship_transform);
}


void UroIns::draw_ship()
{
  visualization_msgs::msg::Marker marker_ship;
  marker_ship.header.stamp = this->get_clock()->now();;
  /*
  marker_ship.header.frame_id = "ship";
  marker_ship.ns = "marker_ship";
  */
  marker_ship.header.frame_id = "ship";
  marker_ship.ns = "marker_ship";
  marker_ship.id = 0;
  marker_ship.type = visualization_msgs::msg::Marker::LINE_STRIP;
  marker_ship.action = visualization_msgs::msg::Marker::ADD;
  //marker_ship.lifetime.sec = 100000;
  //marker_ship.lifetime.nanosec = 0;
  //marker_ship.scale.x = 18.8;
  //marker_ship.scale.y = 4.5;
  //marker_ship.scale.z = 1.6;
  marker_ship.scale.x = 0.1;
  //surfaceから船の中心までの距離
  //float surface_to_ship_forward = - (18.8/2 - 4);
  //float surface_to_ship_right = 4.5/2;
  geometry_msgs::msg::Point point;
  point.x = 0;
  point.y = 0;
  point.z = 0;
  marker_ship.points.push_back(point);
  point.x = 4;
  point.y = -4.5/2;
  point.z = 0;
  marker_ship.points.push_back(point);
  point.x = 0;
  point.y = -4.5;
  point.z = 0;
  marker_ship.points.push_back(point);
  point.x = -14.8;
  point.y = -4.5;
  point.z = 0;
  marker_ship.points.push_back(point);
  point.x = -14.8;
  point.y = 0;
  point.z = 0;
  marker_ship.points.push_back(point);
  point.x = 0;
  point.y = 0;
  point.z = 0;
  marker_ship.points.push_back(point);


  //marker_ship.pose.position.x = surface_to_ship_forward;
  //marker_ship.pose.position.y = - surface_to_ship_right;
  //marker_ship.pose.position.z = 0;
  //marker_ship.scale.x = 30.0;
  //marker_ship.scale.y = 13.0;
  //marker_ship.scale.z = 10.0;
  //marker_ship.pose.position.x = 4;
  //marker_ship.pose.position.y = 6.5;
  //marker_ship.pose.position.z = 0;
  //marker_ship.pose.orientation.x = 0;
  //marker_ship.pose.orientation.y = 0;
  //marker_ship.pose.orientation.z = 0;
  //marker_ship.pose.orientation.w = 1;
  marker_ship.color.a = 0.8f;
  /*
  if (is_ship_data_received == true){
    marker_ship.color.r = 0.3f;
    marker_ship.color.g = 0.3f;
    marker_ship.color.b = 1.0f;
  }
  else{
    marker_ship.color.r = 0.6f;
    marker_ship.color.g = 0.6f;
    marker_ship.color.b = 0.6f;
  }
  */
  if (usbl_reliability_level == 4){
    marker_ship.color.r = 0.0f;
    marker_ship.color.g = 0.6f;
    marker_ship.color.b = 1.0f;
  }
  else if (usbl_reliability_level == 3){
    marker_ship.color.r = 0.8f;
    marker_ship.color.g = 0.4f;
    marker_ship.color.b = 0.0f;
  }
  else if (usbl_reliability_level == 2){
    marker_ship.color.r = 0.7f;
    marker_ship.color.g = 0.7f;
    marker_ship.color.b = 0.7f;
  }
  else if (usbl_reliability_level == 1){
    marker_ship.color.r = 0.4f;
    marker_ship.color.g = 0.4f;
    marker_ship.color.b = 0.4f;
  }
  else{
    marker_ship.color.r = 0.1f;
    marker_ship.color.g = 0.1f;
    marker_ship.color.b = 0.1f;
  }
  printf("draw:usbl_reliability_level = %d\n", usbl_reliability_level);
  printf("draw_usbl\n");
  marker_ship_->publish(marker_ship);
}
/*
void UroIns::delete_ship()
{
  visualization_msgs::msg::Marker delete_ship;
  delete_ship.header.stamp = this->get_clock()->now();
  delete_ship.header.frame_id = "ship";
  delete_ship.ns = "marker_ship";
  delete_ship.id = 0;
  delete_ship.action = visualization_msgs::msg::Marker::DELETE;
  marker_ship_->publish(delete_ship);
}
*/

void UroIns::check_usbl_receive()
{
  if ((is_usbl_data_received == false)&&(is_ship_data_received == true)){
    printf("gray\n");
    usbl_reliability_level = 1;
    /*
    auto now = this->get_clock()->now();
    visualization_msgs::msg::Marker marker_usbl;
    marker_usbl.header.frame_id = "map";
    marker_usbl.header.stamp = now;
    marker_usbl.ns = "marker_usbl";
    marker_usbl.id = 0;
    marker_usbl.type = visualization_msgs::msg::Marker::SPHERE;
    marker_usbl.action = visualization_msgs::msg::Marker::ADD;
    marker_usbl.lifetime.sec = 100000;
    marker_usbl.lifetime.nanosec = 0;
    marker_usbl.scale.x = 0.5;
    marker_usbl.scale.y = 0.5;
    marker_usbl.scale.z = 0.5;

    marker_usbl.pose.position.x = usbl_from_map_xyz[0];
    marker_usbl.pose.position.y = usbl_from_map_xyz[1];
    marker_usbl.pose.position.z = usbl_from_map_xyz[2];

    marker_usbl.pose.orientation.x = 0;
    marker_usbl.pose.orientation.y = 0;
    marker_usbl.pose.orientation.z = 0;
    marker_usbl.pose.orientation.w = 1;
    marker_usbl.color.a = 0.4f;
    marker_usbl.color.r = 0.6f;
    marker_usbl.color.g = 0.6f;
    marker_usbl.color.b = 0.6f;
    marker_usbl_->publish(marker_usbl);
    */
  }
  is_usbl_data_received = false;
}

void UroIns::check_ship_receive()
{
  pulish_usbl_data();

  if (is_ship_data_received == false){
    printf("black\n");
    usbl_reliability_level = 0;
    /*
    auto now = this->get_clock()->now();
    visualization_msgs::msg::Marker marker_usbl;
    marker_usbl.header.frame_id = "map";
    marker_usbl.header.stamp = now;
    marker_usbl.ns = "marker_usbl";
    marker_usbl.id = 0;
    marker_usbl.type = visualization_msgs::msg::Marker::SPHERE;
    marker_usbl.action = visualization_msgs::msg::Marker::ADD;
    marker_usbl.lifetime.sec = 100000;
    marker_usbl.lifetime.nanosec = 0;
    marker_usbl.scale.x = 0.5;
    marker_usbl.scale.y = 0.5;
    marker_usbl.scale.z = 0.5;

    marker_usbl.pose.position.x = usbl_from_map_xyz[0];
    marker_usbl.pose.position.y = usbl_from_map_xyz[1];
    marker_usbl.pose.position.z = usbl_from_map_xyz[2];

    marker_usbl.pose.orientation.x = 0;
    marker_usbl.pose.orientation.y = 0;
    marker_usbl.pose.orientation.z = 0;
    marker_usbl.pose.orientation.w = 1;
    marker_usbl.color.a = 0.9f;
    marker_usbl.color.r = 0.1f;
    marker_usbl.color.g = 0.1f;
    marker_usbl.color.b = 0.1f;
    marker_usbl_->publish(marker_usbl);
    */
  }
  is_ship_data_received = false;
}

void UroIns::pulish_usbl_data()
{
  auto usbl_posi = std_msgs::msg::Float64MultiArray();
  usbl_posi.data.push_back(subsea_.latitude * RADIANS_TO_DEGREES);
  usbl_posi.data.push_back(subsea_.longitude * RADIANS_TO_DEGREES);
  usbl_posi.data.push_back(subsea_.ned_position[0]);
  usbl_posi.data.push_back(subsea_.ned_position[1]);
  usbl_posi.data.push_back(subsea_.ned_position[2]);
  usbl_posi.data.push_back(usbl_reliability_level);
  usbl_posi.data.push_back(subsea_.elevation * RADIANS_TO_DEGREES);
  usbl_posi_->publish(usbl_posi);
}

  

//8月横須賀の暫定機能
void UroIns::get_rov_posi(const uro_control_msgs::msg::RovControlData::SharedPtr msg)
{
    rov_pose_new_ = msg->pose.position;
}

