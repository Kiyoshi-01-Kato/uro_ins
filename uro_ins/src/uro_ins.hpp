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

#ifndef URO_INS_HPP
#define URO_INS_HPP

#include "rclcpp/rclcpp.hpp"
#include <cstdlib>
#include <cstdio>
#include <cstdint>
#include <cstring>
#include <unistd.h>
#include <sys/types.h>
#include <sys/time.h>
#include <memory>

#include "std_msgs/msg/float32.hpp"
#include "std_msgs/msg/float64_multi_array.hpp"
#include "std_msgs/msg/int32_multi_array.hpp"
#include "geometry_msgs/msg/point_stamped.hpp"
#include "geometry_msgs/msg/transform_stamped.hpp"
#include "std_srvs/srv/trigger.hpp"
#include "interfaces/msg/bottom_track.hpp"
#include "interfaces/msg/ins.hpp"
#include "uro_ins_msgs/srv/set_gnss_point.hpp"
#include <tf2_ros/static_transform_broadcaster.h>
#include <tf2_ros/transform_broadcaster.h>
#include <tf2/LinearMath/Quaternion.h>
#include <visualization_msgs/msg/marker.hpp>
#include <visualization_msgs/msg/marker_array.hpp>

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

#include "uro_subsonus.hpp"

#define RADIANS_TO_DEGREES (180.0/M_PI)

class UroSubsonus;

class UroIns : public rclcpp::Node
{
  public:
    UroIns();
    void initialize();
    void set_subsonus(std::shared_ptr<UroSubsonus> usbl);
    void set_ship_data(subsonus_system_state_packet_t surface);
    void set_usbl_data(subsonus_track_packet_t subsea);

  private:
    void tf_init(std::string frame_name);
    void usbl_on_timer();

    void evaluate_usbl_reliability();
    void broadcast_usbl_tf();
    void publish_marker();
    void load_map_latlon();
    void broadcast_ship_tf();
    void draw_usbl();
    void draw_ship();
    void publish_usbl();
    void publish_odom_rov();
    void broadcast_odom_tf(std::string str);
    void get_bottom_track(const interfaces::msg::BottomTrack::SharedPtr msg);
    void get_dvl_posi(const interfaces::msg::INS::SharedPtr msg);
    void get_usbl_position(const std::shared_ptr<std_srvs::srv::Trigger::Request> request,
          std::shared_ptr<std_srvs::srv::Trigger::Response>      response);
    void set_gnss_point(const std::shared_ptr<uro_ins_msgs::srv::SetGnssPoint::Request> request,
          std::shared_ptr<uro_ins_msgs::srv::SetGnssPoint::Response>      response);

    std::shared_ptr<UroSubsonus> usbl_;
    std::shared_ptr<tf2_ros::TransformBroadcaster> tf_broadcast_;
    std::shared_ptr<tf2_ros::StaticTransformBroadcaster> static_tf_broadcast_;
    rclcpp::TimerBase::SharedPtr timer_subsonus_;
    rclcpp::TimerBase::SharedPtr timer_publish_marker_;
    rclcpp::Publisher<visualization_msgs::msg::Marker>::SharedPtr marker_ship_;
    rclcpp::Publisher<visualization_msgs::msg::Marker>::SharedPtr marker_usbl_;
    rclcpp::Publisher<std_msgs::msg::Float64MultiArray>::SharedPtr usbl_posi_;
    rclcpp::Publisher<std_msgs::msg::Float32>::SharedPtr from_ship_to_rov_dvl_;
    rclcpp::Publisher<std_msgs::msg::Float32>::SharedPtr from_ship_to_rov_usbl_;
    rclcpp::Publisher<std_msgs::msg::Float64MultiArray>::SharedPtr odom_rov_;
    rclcpp::Subscription<interfaces::msg::BottomTrack>::SharedPtr bottom_track_;
    rclcpp::Subscription<interfaces::msg::INS>::SharedPtr dvl_posi_; 
    rclcpp::Service<std_srvs::srv::Trigger>::SharedPtr usbl_position_;
    rclcpp::Service<uro_ins_msgs::srv::SetGnssPoint>::SharedPtr gnss_point_;
    rclcpp::Publisher<visualization_msgs::msg::MarkerArray>::SharedPtr gnss_marker_;

    geometry_msgs::msg::TransformStamped ship_tf;
    geometry_msgs::msg::TransformStamped odom_tf;
    geometry_msgs::msg::TransformStamped usbl_tf;

    subsonus_system_state_packet_t surface_;
    subsonus_track_packet_t subsea_;
    subsonus_track_packet_t subsea_old_;
    geometry_msgs::msg::Point dvl_pose_;
    float usbl_from_map_xyz[3];
    bool is_ship_data_received; //GNSS Compass(subsonus(surface))データ受信フラグ
    bool is_usbl_data_received; //音響測位データ受信フラグ
    bool is_map_latlon_loaded;  //mapフレームの緯度経度取得フラグ
    bool bt_dvl_received;       //DVLの対地速度（bottom track）取得済みフラグ
    int usbl_reliability_level; //０：未接続、１：未受信、２：速度3knot以上、３：傘の範囲外、４：正常
    float map_origin_lat;       //map原点の緯度
    float map_origin_lon;       //map原点の経度
    float map_origin_h;         //map原点の高度
    int ship_marker_count;      //前回shipマーカー描画からのカウント
    int usbl_marker_count;      //前回usblマーカー描画からのカウント
    float bottom_track_fom[3];  //DVLの対地速度（bottom track）のFOM
    int gnss_point_num;         //描画しているマーカーの数
};

#endif //URO_INS_HPP