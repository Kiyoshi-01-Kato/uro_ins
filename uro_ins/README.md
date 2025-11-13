# uro_ins

プログラム机上確認方法（暫定）

uro_subsonus.cpp
    2025/11/13時点では116~160行目のコメントアウトを外す

#/*
# //プログラム確認用(実運用時コメントアウト)
#  static int i = 0;
#
#  surface_system_state_packet.latitude        = (35 - i%10000 * 0.00000001) *  DEGREES_TO_RADIANS;
#  surface_system_state_packet.longitude       = (139 + i%10000 * 0.00000001) * DEGREES_TO_RADIANS;
#  surface_system_state_packet.height          = 0 + i%10000 * 0.0001;
#  surface_system_state_packet.orientation[0]  = (0 + i%10000 * 0.0001) * DEGREES_TO_RADIANS;
#  surface_system_state_packet.orientation[1]  = (0 - i%10000 * 0.0001) * DEGREES_TO_RADIANS;
#  surface_system_state_packet.orientation[2]  = (0 + i%10000 * 0.01) * DEGREES_TO_RADIANS;
#  ins_->set_ship_data(surface_system_state_packet);
#
#  if (i%200 == 0){
#    subsonus_track_packet.observer_unix_time_seconds = i/50;
#    subsonus_track_packet.observer_microseconds = i/50;
#    subsonus_track_packet.observer_depth  = 1 + i%10000 * 0.0001;
#    subsonus_track_packet.ned_position[0] = i%10000 * 0.01;
#    subsonus_track_packet.ned_position[1] = - i%10000 * 0.02;
#    subsonus_track_packet.ned_position[2] = i%10000 * 0.05;
#    subsonus_track_packet.range           = 20 + i%10000 * 0.001;
#    subsonus_track_packet.azimuth         = (30 + i%10000 * 0.001) * DEGREES_TO_RADIANS;
#    subsonus_track_packet.elevation       = (73 + i%10000 * 0.001) * DEGREES_TO_RADIANS;
#    subsonus_track_packet.latitude        = (35 + i%10000 * 0.00000001) * DEGREES_TO_RADIANS;
#    subsonus_track_packet.longitude       = (139 - i%10000 * 0.00000002) * DEGREES_TO_RADIANS;
#    subsonus_track_packet.height          = 0 + i%10000 * 0.0002;
#    subsonus_track_packet.depth           = 10 + i%10000 * 0.001;
#    if ((i%400 ==0)){
#      ins_->set_usbl_data(subsonus_track_packet);
#    }
#  }
#  
#  subsea_system_state_packet.latitude        = (35 + i%10000 * 0.00000002) *  DEGREES_TO_RADIANS;
#  subsea_system_state_packet.longitude       = (139 - i%10000 * 0.00000004) * DEGREES_TO_RADIANS;
#  subsea_system_state_packet.height          = 0 + i%10000 * 0.0003;
#  subsea_system_state_packet.orientation[0]  = (0 - i%10000 * 0.0004) * DEGREES_TO_RADIANS;
#  subsea_system_state_packet.orientation[1]  = (0 + i%10000 * 0.0007) * DEGREES_TO_RADIANS;
#  subsea_system_state_packet.orientation[2]  = (0 - i%10000 * 0.06) * DEGREES_TO_RADIANS;
#  ins_->set_subsea_data(subsea_system_state_packet);
#  
#
#  if (i > 1000000){
#    i = 0;
# }
#  i = i + 1;
# */