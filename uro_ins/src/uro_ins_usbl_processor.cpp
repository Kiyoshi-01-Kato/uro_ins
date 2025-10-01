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

#include <cmath>
#include <chrono>
#include <thread>

#include <GeographicLib/LocalCartesian.hpp>

#ifndef M_PI
#define M_PI (3.14159265358979323846)
#endif

#include "uro_ins.hpp"

using namespace GeographicLib;

#define RADIANS_TO_DEGREES (180.0/M_PI)
#define DEGREES_TO_RADIANS (M_PI/180.0)

void UroIns::initialize()
{
  tf_init("map");
  tf_init("ship");
  tf_init("odom");
  tf_init("usbl");
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
  geometry_msgs::msg::TransformStamped init_tf;
  auto now = this->get_clock()->now();
  init_tf.header.stamp = now;

  init_tf.transform.translation.x = 0;
  init_tf.transform.translation.y = 0;
  init_tf.transform.translation.z = 0;
  tf2::Quaternion quat;
  quat.setRPY(0.0, 0.0, 0.0);
  init_tf.transform.rotation.x = quat.x();
  init_tf.transform.rotation.y = quat.y();
  init_tf.transform.rotation.z = quat.z();
  init_tf.transform.rotation.w = quat.w();

  if (frame_name == "map"){
    init_tf.header.frame_id = "world";
    init_tf.child_frame_id = "map";
    static_tf_broadcast_->sendTransform(init_tf);
  }else if (frame_name == "ship"){
    init_tf.header.frame_id = "map";
    init_tf.child_frame_id = "ship";
    ship_tf = init_tf;
    tf_broadcast_->sendTransform(ship_tf);
  }else if (frame_name == "odom"){
    init_tf.header.frame_id = "map";
    init_tf.child_frame_id = "odom";
    odom_tf = init_tf;
    tf_broadcast_->sendTransform(odom_tf);
  }else if (frame_name == "usbl"){
    init_tf.header.frame_id = "map";
    init_tf.child_frame_id = "usbl";
    usbl_tf = init_tf;
    tf_broadcast_->sendTransform(usbl_tf);
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
    publish_usbl();

    //対地速度が一度でも取得したらtrue
    if ((bottom_track_fom[0] < 1) && (bottom_track_fom[1] < 1)){
      bt_dvl_received = true;
    }
    //対地速度を一度も取得していない場合
    if (bt_dvl_received == false){
      broadcast_odom_tf("ship");
    }
    else{
      broadcast_odom_tf("keep");
    }
  
  is_ship_data_received = false;
  is_usbl_data_received = false;

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
  LocalCartesian prij(map_origin_lat, map_origin_lon, map_origin_h);
  double x, y, z;
  prij.Forward(surface_.latitude * RADIANS_TO_DEGREES, surface_.longitude * RADIANS_TO_DEGREES, surface_.height, x, y, z);

  auto now = this->get_clock()->now();
  ship_tf.header.stamp = now;
  ship_tf.transform.translation.x = x;
  ship_tf.transform.translation.y = - y;
  tf2::Quaternion quat;
  quat.setRPY(0.0, 0.0, - surface_.orientation[2]);
  ship_tf.transform.rotation.x = quat.x();
  ship_tf.transform.rotation.y = quat.y();
  ship_tf.transform.rotation.z = quat.z();
  ship_tf.transform.rotation.w = quat.w();
  tf_broadcast_->sendTransform(ship_tf);
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
  LocalCartesian prij(map_origin_lat, map_origin_lon, map_origin_h);
  double x, y, z;
  prij.Forward(subsea_.latitude * RADIANS_TO_DEGREES, subsea_.longitude * RADIANS_TO_DEGREES, subsea_.height, x, y, z);
  usbl_from_map_xyz[0] = x;
  usbl_from_map_xyz[1] = - y;
  usbl_from_map_xyz[2] = - subsea_.depth;
  auto now = this->get_clock()->now();
  usbl_tf.header.stamp = now;
  usbl_tf.transform.translation.x = usbl_from_map_xyz[0];
  usbl_tf.transform.translation.y = usbl_from_map_xyz[1];
  usbl_tf.transform.translation.z = usbl_from_map_xyz[2];
  tf2::Quaternion quat;
  //tfを同じ位置で更新するため、90degごとに回転
  float spin_90 = (M_PI / 2) * usbl_marker_count;
  quat.setRPY(0.0, 0.0, spin_90);
  usbl_tf.transform.rotation.x = quat.x();
  usbl_tf.transform.rotation.y = quat.y();
  usbl_tf.transform.rotation.z = quat.z();
  usbl_tf.transform.rotation.w = quat.w();
  tf_broadcast_->sendTransform(usbl_tf);
}

void UroIns::draw_usbl()
{
  auto now = this->get_clock()->now();
  visualization_msgs::msg::Marker marker_usbl;
  marker_usbl.header.frame_id = "usbl";
  marker_usbl.header.stamp = now;
  marker_usbl.ns = "marker_usbl";
  marker_usbl.id = 0;
  marker_usbl.action = visualization_msgs::msg::Marker::ADD;
  marker_usbl.lifetime.sec = 100000;
  marker_usbl.lifetime.nanosec = 0;
  marker_usbl.scale.x = 1.0;
  marker_usbl.scale.y = 1.0;
  marker_usbl.scale.z = 1.0;

  marker_usbl.pose.position.x = 0;
  marker_usbl.pose.position.y = 0;
  marker_usbl.pose.position.z = 0;

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


void UroIns::publish_usbl()
{
  auto usbl_posi = std_msgs::msg::Float64MultiArray();
  usbl_posi.data.push_back(subsea_.latitude * RADIANS_TO_DEGREES);
  usbl_posi.data.push_back(subsea_.longitude * RADIANS_TO_DEGREES);
  usbl_posi.data.push_back(subsea_.ned_position[0]);
  usbl_posi.data.push_back(subsea_.ned_position[1]);
  usbl_posi.data.push_back(subsea_.depth);
  usbl_posi.data.push_back(surface_.latitude * RADIANS_TO_DEGREES);
  usbl_posi.data.push_back(surface_.longitude * RADIANS_TO_DEGREES);
  usbl_posi.data.push_back(usbl_reliability_level);
  usbl_posi.data.push_back(subsea_.elevation * RADIANS_TO_DEGREES);
  usbl_posi_->publish(usbl_posi);

  auto from_ship_to_rov_dvl = std_msgs::msg::Float32();
  float delta_dvl_x = dvl_pose_.x + odom_tf.transform.translation.x - ship_tf.transform.translation.x;
  float delta_dvl_y = dvl_pose_.y + odom_tf.transform.translation.y - ship_tf.transform.translation.y;
  from_ship_to_rov_dvl.data = std::sqrt(std::pow(delta_dvl_x, 2) + std::pow(delta_dvl_y, 2));
  from_ship_to_rov_dvl_->publish(from_ship_to_rov_dvl);

  auto from_ship_to_rov_usbl = std_msgs::msg::Float32();
  from_ship_to_rov_usbl.data = std::sqrt(std::pow(subsea_.ned_position[0], 2) + std::pow(subsea_.ned_position[1], 2));
  from_ship_to_rov_usbl_->publish(from_ship_to_rov_usbl);
}

void UroIns::broadcast_odom_tf(std::string str)
{
  auto now = this->get_clock()->now();
  odom_tf.header.stamp = now;

  if (str == "ship"){
    odom_tf.transform.translation = ship_tf.transform.translation;
  }
  else if (str == "usbl"){
    odom_tf.transform.translation.x = usbl_tf.transform.translation.x - dvl_pose_.x;
    odom_tf.transform.translation.y = usbl_tf.transform.translation.y - dvl_pose_.y;
  }
  else{
    //何もしない
  }
  tf_broadcast_->sendTransform(odom_tf);
}

void UroIns::get_bottom_track(const interfaces::msg::BottomTrack::SharedPtr msg)
{
    bottom_track_fom[0] = msg->fom_x;
    bottom_track_fom[1] = msg->fom_y;
    bottom_track_fom[2] = msg->fom_z;
}

void UroIns::get_dvl_posi(const interfaces::msg::INS::SharedPtr msg)
{
    dvl_pose_.x = msg->position_frame_x;
    dvl_pose_.y = - msg->position_frame_y;
    dvl_pose_.z = msg->position_frame_z;
}

void UroIns::get_usbl_position(const std::shared_ptr<std_srvs::srv::Trigger::Request> request,
          std::shared_ptr<std_srvs::srv::Trigger::Response>      response)
{
  (void)request;
  if ((bottom_track_fom[0] >= 1) || (bottom_track_fom[1] >= 1)){
    response->success = false;
    response->message = "fail : usbl is unreliable";
    return;
  }

  if (usbl_reliability_level <= 2){
    response->success = false;
    response->message = "fail : bottom track is unreliable";
  }
  else{
    broadcast_odom_tf("usbl");
    response->success = true;
    response->message = "success";
  }
}

void UroIns::set_gnss_point(const std::shared_ptr<uro_ins_msgs::srv::SetGnssPoint::Request> request,
          std::shared_ptr<uro_ins_msgs::srv::SetGnssPoint::Response>      response)
{
  visualization_msgs::msg::MarkerArray marker_array;
  std::vector<geometry_msgs::msg::Point> points;

  //前回のマーカーを削除
  for (int i = 0; i < gnss_point_num; ++i) {
    visualization_msgs::msg::Marker delete_marker;
    delete_marker.header.frame_id = "map";
    delete_marker.header.stamp = this->get_clock()->now();
    delete_marker.ns = "points";
    delete_marker.id = i;
    delete_marker.action = visualization_msgs::msg::Marker::DELETE;
    marker_array.markers.push_back(delete_marker);

    visualization_msgs::msg::Marker delete_text;
    delete_text.header = delete_marker.header;
    delete_text.ns = "labels";
    delete_text.id = 100 + i;
    delete_text.action = visualization_msgs::msg::Marker::DELETE;
    marker_array.markers.push_back(delete_text);
  }

  visualization_msgs::msg::Marker delete_line;
  delete_line.header.frame_id = "map";
  delete_line.header.stamp = this->get_clock()->now();
  delete_line.ns = "lines";
  delete_line.id = 999;
  delete_line.action = visualization_msgs::msg::Marker::DELETE;
  marker_array.markers.push_back(delete_line);

  //今回のマーカーを描画
  gnss_point_num = request->poses.size();
  LocalCartesian prij(map_origin_lat, map_origin_lon, map_origin_h);
 
  for (int i = 0; i < gnss_point_num; ++i) {
    geometry_msgs::msg::Point pt;
    double x, y, z;
    prij.Forward(request->poses[i].position.latitude, request->poses[i].position.longitude, 0.0, x, y, z);
    pt.x = x;
    pt.y = - y;
    pt.z = - request->poses[i].position.altitude;
    points.push_back(pt);

    visualization_msgs::msg::Marker marker;
    marker.header.frame_id = "map";
    marker.header.stamp = this->get_clock()->now();
    marker.ns = "points";
    marker.id = i;
    marker.type = visualization_msgs::msg::Marker::SPHERE;
    marker.action = visualization_msgs::msg::Marker::ADD;
    marker.pose.position = pt;
    marker.pose.orientation.x = 0.0;
    marker.pose.orientation.y = 0.0;
    marker.pose.orientation.z = 0.0;
    marker.pose.orientation.w = 1.0;
    marker.scale.x = 2;
    marker.scale.y = 2;
    marker.scale.z = 2;
    marker.color.a = 0.2;
    marker.color.r = 0.0;
    marker.color.g = 0.8;
    marker.color.b = 0.0;
    marker_array.markers.push_back(marker);

    visualization_msgs::msg::Marker text;
    text.header = marker.header;
    text.ns = "labels";
    text.id = 50 + i;
    text.type = visualization_msgs::msg::Marker::TEXT_VIEW_FACING;
    text.action = visualization_msgs::msg::Marker::ADD;
    text.pose.position = pt;
    text.pose.position.x += 1.0;  // Slightly above the sphere
    marker.pose.orientation.x = 0.0;
    marker.pose.orientation.y = 0.0;
    marker.pose.orientation.z = 0.0;
    marker.pose.orientation.w = 1.0;
    text.scale.z = 1.0;
    text.color.a = 0.5;
    text.color.r = 0.2;
    text.color.g = 0.2;
    text.color.b = 0.2;
    text.text = "No." + std::to_string(i+1);
    marker_array.markers.push_back(text);
  }

  visualization_msgs::msg::Marker line;
  line.header.frame_id = "map";
  line.header.stamp = this->get_clock()->now();
  line.ns = "lines";
  line.id = 100;
  line.type = visualization_msgs::msg::Marker::LINE_STRIP;
  line.action = visualization_msgs::msg::Marker::ADD;
  line.scale.x = 0.5;
  line.color.a = 0.2;
  line.color.r = 0.0;
  line.color.g = 0.8;
  line.color.b = 0.0;
  line.points = points;
  marker_array.markers.push_back(line);

  gnss_marker_->publish(marker_array);
  response->success = true;
}