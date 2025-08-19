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

#ifndef URO_SUBSONUS_HPP
#define URO_SUBSONUS_HPP

#include "rclcpp/rclcpp.hpp"
#include <cstdlib>
#include <cstdio>
#include <cstdint>
#include <cstring>
#include <unistd.h>
#include <sys/types.h>
#include <sys/time.h>
#include <memory>

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

extern "C" {
#include "SubsonusSDK_0/C/lib/an_packet_protocol.h"
#include "SubsonusSDK_0/C/lib/subsonus_packets.h"
}

//#include "uro_ins.hpp"

class UroIns;

class UroSubsonus
{
  public:
    explicit UroSubsonus(std::shared_ptr<UroIns> ins);
    void receive_subsonus();
    void initialize();

  private:
    std::shared_ptr<UroIns> ins_;
    
  #ifdef _WIN32
    SOCKET top_socket;
  #else
    int tcp_socket;
  #endif

    unsigned int bytes_received;
    int count;

    struct timeval t_wait;
    fd_set readfds;

    an_decoder_t an_decoder;
    an_packet_t *an_packet;

    subsonus_system_state_packet_t system_state_packet;
    subsonus_track_packet_t subsonus_track_packet;

    //rclcpp::Publisher<std_msgs::msg::Float64MultiArray>::SharedPtr subsonus_publisher_;
};


#endif //URO_SUBSONUS_HPP

