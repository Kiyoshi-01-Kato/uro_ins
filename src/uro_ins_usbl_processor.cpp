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
#include <unistd.h>
#include <sys/types.h>
#include <sys/time.h>

#include "std_msgs/msg/float64_multi_array.hpp"
#include "std_msgs/msg/int32_multi_array.hpp"
#include "geometry_msgs/msg/point_stamped.hpp"
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
  process_usbl_data();
}

void UroIns::usbl_on_timer()
{
  usbl_->receive_subsonus();
}

void UroIns::process_usbl_data()
{
  evaluate_usbl_reliability();
  if (is_map_tf_created == true){
    broadcast_usbl_tf();
    draw_usbl();
  }
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

  if (elapsed_time * v_limit < diff_distance){
    usbl_reliability_level = 1;
    return;
  }

  float elevation_deg = subsea_.elevation * RADIANS_TO_DEGREES;
  if (elevation_deg > 55){
    usbl_reliability_level = 2;
    return;
  }

  usbl_reliability_level = 3;

}

void UroIns::broadcast_usbl_tf()
{
  using namespace GeographicLib;
  LocalCartesian prij(map_origin_lat, map_origin_lon, map_origin_h);
  double x, y, z;
  prij.Forward(subsea_.latitude, subsea_.longitude, subsea_.height, x, y, z);

  auto now = this->get_clock()->now();
  geometry_msgs::msg::TransformStamped usbl_transform;
  usbl_transform.header.stamp = now;
  //ship_transform.header.stamp.sec = time.tv_sec;
  //ship_transform.header.stamp.nanosec = time.tv_nsec;
  usbl_transform.header.frame_id = "map";
  usbl_transform.child_frame_id = "usbl";
  usbl_transform.transform.translation.x = y;
  usbl_transform.transform.translation.y = - x;
  usbl_transform.transform.translation.z = - subsea_.depth;
  tf2::Quaternion quat;
  quat.setRPY(0.0, 0.0, 0.0);
  usbl_transform.transform.rotation.x = quat.x();
  usbl_transform.transform.rotation.y = quat.y();
  usbl_transform.transform.rotation.z = quat.z();
  usbl_transform.transform.rotation.w = quat.w();
  tf_broadcast_->sendTransform(usbl_transform);

  auto usbl_posi = std_msgs::msg::Float64MultiArray();
  usbl_posi.data.push_back(y);
  usbl_posi.data.push_back(- x);
  usbl_posi.data.push_back(- z);
  usbl_posi_->publish(usbl_posi);
}

void UroIns::draw_usbl()
{
  auto now = this->get_clock()->now();
  visualization_msgs::msg::Marker marker_usbl;
  marker_usbl.header.frame_id = "usbl";
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

  marker_usbl.pose.position.x = 0;
  marker_usbl.pose.position.y = 0;
  marker_usbl.pose.position.z = 0;

  marker_usbl.pose.orientation.x = 0;
  marker_usbl.pose.orientation.y = 0;
  marker_usbl.pose.orientation.z = 0;
  marker_usbl.pose.orientation.w = 1;
  if (usbl_reliability_level == 3){
    marker_usbl.color.r = 0.0f;
    marker_usbl.color.g = 0.0f;
    marker_usbl.color.b = 0.7f;
  }
  else if (usbl_reliability_level == 2){
    marker_usbl.color.r = 0.8f;
    marker_usbl.color.g = 0.6f;
    marker_usbl.color.b = 0.0f;
  }
  else if (usbl_reliability_level == 1){
    marker_usbl.color.r = 1.0f;
    marker_usbl.color.g = 0.0f;
    marker_usbl.color.b = 0.0f;
  }
  else{
    //何もしない
  }
  marker_usbl_->publish(marker_usbl);
}

void UroIns::publish_ship_marker()
{
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
  is_ship_data_received = false;
}


void UroIns::broadcast_map_tf()
{
  map_origin_lat = surface_.latitude;
  map_origin_lon = surface_.longitude;
  map_origin_h = surface_.height;

  auto now = this->get_clock()->now();
  
  //struct timespec time;
  //clock_gettime(CLOCK_REALTIME, &time);

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


void UroIns::broadcast_ship_tf()
{
  using namespace GeographicLib;
  LocalCartesian prij(map_origin_lat, map_origin_lon, map_origin_h);
  double x, y, z;
  prij.Forward(surface_.latitude, surface_.longitude, surface_.height, x, y, z);

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
}


void UroIns::draw_ship()
{
  auto now = this->get_clock()->now();
  visualization_msgs::msg::Marker marker_ship;
  marker_ship.header.frame_id = "ship";
  marker_ship.header.stamp = now;
  marker_ship.ns = "marker_ship";
  marker_ship.id = 0;
  marker_ship.type = visualization_msgs::msg::Marker::CUBE;
  marker_ship.action = visualization_msgs::msg::Marker::ADD;
  marker_ship.lifetime.sec = 100000;
  marker_ship.lifetime.nanosec = 0;
  marker_ship.scale.x = 30.0;
  marker_ship.scale.y = 13.0;
  marker_ship.scale.z = 10.0;
  marker_ship.pose.position.x = 4;
  marker_ship.pose.position.y = 6.5;
  marker_ship.pose.position.z = 0;
  marker_ship.pose.orientation.x = 0;
  marker_ship.pose.orientation.y = 0;
  marker_ship.pose.orientation.z = 0;
  marker_ship.pose.orientation.w = 1;
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
  marker_ship_->publish(marker_ship);
}

void UroIns::check_usbl_receive()
{
  if (is_usbl_data_received == false){
    usbl_reliability_level = 0;
    auto now = this->get_clock()->now();
    visualization_msgs::msg::Marker marker_usbl;
    marker_usbl.header.frame_id = "usbl";
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

    marker_usbl.pose.position.x = 0;
    marker_usbl.pose.position.y = 0;
    marker_usbl.pose.position.z = 0;

    marker_usbl.pose.orientation.x = 0;
    marker_usbl.pose.orientation.y = 0;
    marker_usbl.pose.orientation.z = 0;
    marker_usbl.pose.orientation.w = 1;
    marker_usbl.color.r = 0.6f;
    marker_usbl.color.g = 0.6f;
    marker_usbl.color.b = 0.6f;
    marker_usbl_->publish(marker_usbl);
  }
  is_usbl_data_received = false;
}

