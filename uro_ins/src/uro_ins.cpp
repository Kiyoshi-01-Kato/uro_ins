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

#include <algorithm>


#ifndef M_PI
#define M_PI (3.14159265358979323846)
#endif

#include "uro_ins.hpp"

using std::placeholders::_1;
#define RADIANS_TO_DEGREES (180.0/M_PI)

UroIns::UroIns() : Node("ins_node")
{
  using namespace std::chrono_literals;
  //subsonus_publisher_ = this->create_publisher<std_msgs::msg::Float64MultiArray>("subsonus_data", 10);
  tf_broadcast_ = std::make_shared<tf2_ros::TransformBroadcaster>(this);
  static_tf_broadcast_ = std::make_shared<tf2_ros::StaticTransformBroadcaster>(this);
  marker_ship_ = this->create_publisher<visualization_msgs::msg::Marker>("/ins_node/marker_ship", 10);
  marker_usbl_ = this->create_publisher<visualization_msgs::msg::Marker>("/ins_node/marker_usbl", 10);
  usbl_data_ = this->create_publisher<uro_ins_msgs::msg::UsblData>("/ins_node/usbl_data", 10);
  from_ship_to_rov_dvl_ = this->create_publisher<std_msgs::msg::Float32>("/ins_node/horizontal_distance_ship_rov_dvl", 10);
  from_ship_to_rov_usbl_ = this->create_publisher<std_msgs::msg::Float32>("/ins_node/horizontal_distance_ship_rov_usbl", 10);
  ins_data_ = this->create_publisher<uro_ins_msgs::msg::InsData>("/ins_node/ins_data", 10);

  //Nucleusのパケット取得
  bottom_track_ = this->create_subscription<interfaces::msg::BottomTrack>(
    "/nucleus_node/bottom_track_packets", 10, std::bind(&UroIns::get_bottom_track, this, _1));
  dvl_posi_ = this->create_subscription<interfaces::msg::INS>(
    "/nucleus_node/ins_packets", 10, std::bind(&UroIns::get_dvl_posi, this, _1));

  usbl_position_ = this->create_service<std_srvs::srv::Trigger>(
    "/ins_node/get_usbl_position",
    std::bind(&UroIns::get_usbl_position, this, std::placeholders::_1, std::placeholders::_2));
  gnss_point_ = this->create_service<uro_ins_msgs::srv::SetGnssPoint>(
    "/ins_node/set_gnss_point",
    std::bind(&UroIns::set_gnss_point, this, std::placeholders::_1, std::placeholders::_2));

  gnss_marker_ = this->create_publisher<visualization_msgs::msg::MarkerArray>("/ins_node/marker_gnss", 10);

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
  bt_dvl_received = false;
  usbl_reliability_level = 0;
  map_origin_lat = 0.0;
  map_origin_lon = 0.0;
  map_origin_h = 0.0;
  ship_marker_count = 0;
  usbl_marker_count = 0;
  gnss_point_num = 0;
  horizontal_distance_usbl = 0.0;
  surface_ = {};
  subsea_ = {};
  subsea_old_ = {};
  std::fill(std::begin(usbl_from_map_xyz), std::end(usbl_from_map_xyz), 0);
  std::fill(std::begin(bottom_track_fom), std::end(bottom_track_fom), 10);
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
