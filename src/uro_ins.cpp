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
#include <algorithm>
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

UroIns::UroIns() : Node("ins_node")
{
  using namespace std::chrono_literals;
  //subsonus_publisher_ = this->create_publisher<std_msgs::msg::Float64MultiArray>("subsonus_data", 10);
  tf_broadcast_ = std::make_shared<tf2_ros::TransformBroadcaster>(this);
  static_tf_broadcast_ = std::make_shared<tf2_ros::StaticTransformBroadcaster>(this);
  marker_ship_ = this->create_publisher<visualization_msgs::msg::Marker>("marker_ship", 10);
  marker_usbl_ = this->create_publisher<visualization_msgs::msg::Marker>("marker_usbl", 10);
  usbl_posi_ = this->create_publisher<std_msgs::msg::Float64MultiArray>("usbl_posi", 10);

  //subsonusデータ受信(音響測位データ受診時にUSBL描画)
  timer_subsonus_ = this->create_wall_timer(
    10ms, 
    std::bind(&UroIns::usbl_on_timer, this)
  );

  //ship描画
  timer_publish_marker_ = this->create_wall_timer(
    200ms, 
    std::bind(&UroIns::publish_marker, this)
  );

  is_ship_data_received = false;
  is_usbl_data_received = false;
  is_map_latlon_loaded = false;
  usbl_reliability_level = 0;
  map_origin_lat = 0.0;
  map_origin_lon = 0.0;
  map_origin_h = 0.0;
  ship_marker_count = 0;
  usbl_marker_count = 0;
  surface_ = {};
  subsea_ = {};
  subsea_old_ = {};
  std::fill(std::begin(usbl_from_map_xyz), std::end(usbl_from_map_xyz), 0);
}

int main(int argc, char ** argv)
{
  //(void) argc;
  //(void) argv;

  rclcpp::init(argc, argv);
  auto ins = std::make_shared<UroIns>();
  auto usbl = std::make_shared<UroSubsonus>(ins);
  usbl->initialize();
  ins->initialize();
  ins->set_subsonus(usbl);
  rclcpp::spin(ins);
  rclcpp::shutdown();


	return EXIT_SUCCESS;
}
