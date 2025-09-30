set(CMAKE_AUTOMOC OFF)

cmake_minimum_required(VERSION 3.5)
project(uro_ins)

# Default to C99
if(NOT CMAKE_C_STANDARD)
  set(CMAKE_C_STANDARD 99)
endif()

# Default to C++14
if(NOT CMAKE_CXX_STANDARD)
  set(CMAKE_CXX_STANDARD 14)
endif()

enable_language(C CXX)

if(CMAKE_COMPILER_IS_GNUCXX OR CMAKE_CXX_COMPILER_ID MATCHES "Clang")
  add_compile_options(-Wall -Wextra -Wpedantic)
endif()

#set(CMAKE_AUTOMOC ON)
# set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -Wunused-parameter")

set(CMAKE_MODULE_PATH ${CMAKE_MODULE_PATH} "/usr/share/cmake/geographiclib")

# find dependencies
find_package(ament_cmake REQUIRED)
find_package(rclcpp REQUIRED)
find_package(geometry_msgs REQUIRED)
find_package(tf2 REQUIRED)
find_package(tf2_ros REQUIRED)
find_package(tf2_msgs REQUIRED)
find_package(visualization_msgs REQUIRED)
find_package(GeographicLib REQUIRED)
find_package(geographic_msgs REQUIRED)
find_package(rosidl_default_generators REQUIRED)
find_package(interfaces REQUIRED)
find_package(uro_ins_msgs REQUIRED)
# uncomment the following section in order to fill in
# further dependencies manually.
# find_package(<dependency> REQUIRED)

set(SOURCES
  src/uro_ins.cpp
  src/uro_ins_usbl_processor.cpp
  src/uro_subsonus.cpp
)

add_library(my_c_code STATIC
  src/SubsonusSDK_0/C/lib/an_packet_protocol.c
  src/SubsonusSDK_0/C/lib/subsonus_device_packets.c
  src/SubsonusSDK_0/C/lib/subsonus_packets.c
)


add_executable(uro_ins ${SOURCES})
target_include_directories(uro_ins PUBLIC
  src
)


target_link_libraries(uro_ins
  my_c_code
  Geographic
)

ament_target_dependencies(uro_ins 
rclcpp
tf2
tf2_ros
tf2_msgs
geometry_msgs
visualization_msgs
geographic_msgs
interfaces
uro_ins_msgs
)

install(TARGETS
  uro_ins
  my_c_code
  DESTINATION lib/${PROJECT_NAME})

if(BUILD_TESTING)
  find_package(ament_lint_auto REQUIRED)
  # the following line skips the linter which checks for copyrights
  # uncomment the line when a copyright and license is not present in all source files
  #set(ament_cmake_copyright_FOUND TRUE)
  # the following line skips cpplint (only works in a git repo)
  # uncomment the line when this package is not in a git repo
  #set(ament_cmake_cpplint_FOUND TRUE)
  ament_lint_auto_find_test_dependencies()
endif()

ament_package()
