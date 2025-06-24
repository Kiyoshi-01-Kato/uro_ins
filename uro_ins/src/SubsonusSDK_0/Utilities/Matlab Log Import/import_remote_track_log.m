% /****************************************************************/
% /*                                                              */
% /*          Advanced Navigation Packet Protocol Library         */
% /*                Matlab Log Import, Version 2.4                */
% /*              Copyright 2020, Advanced Navigation             */
% /*                                                              */
% /****************************************************************/
% /*
% * Copyright (C) 2020 Advanced Navigation
% *
% * Permission is hereby granted, free of charge, to any person obtaining
% * a copy of this software and associated documentation files (the "Software"),
% * to deal in the Software without restriction, including without limitation
% * the rights to use, copy, modify, merge, publish, distribute, sublicense,
% * and/or sell copies of the Software, and to permit persons to whom the
% * Software is furnished to do so, subject to the following conditions:
% *
% * The above copyright notice and this permission notice shall be included
% * in all copies or substantial portions of the Software.
% *
% * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
% * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
% * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
% * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
% * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
% * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
% * DEALINGS IN THE SOFTWARE.
% */

function remote_track = import_remote_track_log(filename)
    data = csvread(filename, 2, 1);
    offset = 1;
    remote_track.unix_time_seconds = data(:,offset) + data(:,offset+1)/1000000; offset=offset+2;
    remote_track.device_address = data(:,offset); offset=offset+1;
    remote_track.data_connection_active  = data(:,offset); offset=offset+1;
    remote_track.depth_correction_applied  = data(:,offset); offset=offset+1;
    
            
    remote_track.system_status.system_failure = data(:,offset); offset=offset+1;
    remote_track.system_status.accelerometer_failure = data(:,offset); offset=offset+1;
    remote_track.system_status.gyroscope_failure = data(:,offset); offset=offset+1;
    remote_track.system_status.magnetometer_failure = data(:,offset); offset=offset+1;
    remote_track.system_status.pressure_failure = data(:,offset); offset=offset+1;
    remote_track.system_status.gnss_failure = data(:,offset); offset=offset+1;
    remote_track.system_status.accelerometer_overrange = data(:,offset); offset=offset+1;
    remote_track.system_status.gyroscope_overrange = data(:,offset); offset=offset+1;
    remote_track.system_status.magnetometer_overrange = data(:,offset); offset=offset+1;
    remote_track.system_status.pressure_overrange = data(:,offset); offset=offset+1;
    remote_track.system_status.maximum_temperature = data(:,offset); offset=offset+1;
    remote_track.system_status.out_of_water = data(:,offset); offset=offset+1;
    remote_track.system_status.pressure_depth_offset_failure = data(:,offset); offset=offset+1;
    remote_track.system_status.logging_memory_failure = data(:,offset); offset=offset+1;
    remote_track.system_status.filter_aiding_comms = data(:,offset); offset=offset+1;
    remote_track.system_status.fixed_position_gnss_aiding_alarm = data(:,offset); offset=offset+1;
    remote_track.system_status.fixed_position_acoustic_aiding_alarm = data(:,offset); offset=offset+1;
    remote_track.system_status.multiple_aiding_source_alarm = data(:,offset); offset=offset+1;
    remote_track.system_status.aided_external_ins_alarm = data(:,offset); offset=offset+1;
    remote_track.system_status.aiding_gnss_time_sync_alarm = data(:,offset); offset=offset+1;
    remote_track.system_status.multiple_master_alarm = data(:,offset); offset=offset+1;
    remote_track.system_status.modem_data_disabled_alarm = data(:,offset); offset=offset+1;
    remote_track.system_status.brownout_alarm = data(:,offset); offset=offset+1;
    remote_track.system_status.aiding_alignment_alarm = data(:,offset); offset=offset+1;
        
    remote_track.filter_status.orientation_initialised = data(:,offset); offset=offset+1;
    remote_track.filter_status.navigation_filter_initialised = data(:,offset); offset=offset+1;
    remote_track.filter_status.heading_initialised = data(:,offset); offset=offset+1;
    remote_track.filter_status.utc_time_initialised = data(:,offset); offset=offset+1;
    remote_track.filter_status.gnss_fix_type = data(:,offset); offset=offset+1;
    remote_track.filter_status.acoustic_heading_active = data(:,offset); offset=offset+1;
    remote_track.filter_status.acoustic_position_active = data(:,offset); offset=offset+1;
    remote_track.filter_status.internal_gnss_enabled = data(:,offset); offset=offset+1;
    remote_track.filter_status.magnetometers_enabled = data(:,offset); offset=offset+1;
    remote_track.filter_status.velocity_heading_enabled = data(:,offset); offset=offset+1;
    remote_track.filter_status.pressure_depth_position_active = data(:,offset); offset=offset+1;
    remote_track.filter_status.external_position_active = data(:,offset); offset=offset+1;
    remote_track.filter_status.external_velocity_active = data(:,offset); offset=offset+1;
    remote_track.filter_status.external_heading_active = data(:,offset); offset=offset+1;
    remote_track.filter_status.dual_antenna_heading_active = data(:,offset); offset=offset+1;
    remote_track.filter_status.fixed_position_active = data(:,offset); offset=offset+1;
    remote_track.filter_status.fixed_heading_active = data(:,offset); offset=offset+1;
    remote_track.filter_status.fixed_roll_pitch_active = data(:,offset); offset=offset+1;
    remote_track.filter_status.pressure_depth_height_active = data(:,offset); offset=offset+1;
    remote_track.filter_status.external_gnss_enabled = data(:,offset); offset=offset+1;
    remote_track.filter_status.pressure_depth_initialised = data(:,offset); offset=offset+1;
    remote_track.filter_status.internal_velocity_of_sound_active = data(:,offset); offset=offset+1;
    remote_track.filter_status.usbl_tracking_active = data(:,offset); offset=offset+1;
    
    remote_track.filter_status.local_time_valid = data(:,offset); offset=offset+1;
    remote_track.filter_status.local_position_valid = data(:,offset); offset=offset+1;
    remote_track.filter_status.local_velocity_valid = data(:,offset); offset=offset+1;
    remote_track.filter_status.local_orientation_valid = data(:,offset); offset=offset+1;
    remote_track.filter_status.local_position_error_valid = data(:,offset); offset=offset+1;
    remote_track.filter_status.local_orientation_error_valid = data(:,offset); offset=offset+1;
    remote_track.filter_status.local_depth_valid = data(:,offset); offset=offset+1;
    remote_track.filter_status.remote_age_valid = data(:,offset); offset=offset+1;
    remote_track.filter_status.remote_range_valid = data(:,offset); offset=offset+1;
    remote_track.filter_status.remote_azimuth_valid = data(:,offset); offset=offset+1;
    remote_track.filter_status.remote_elevation_valid = data(:,offset); offset=offset+1;
    remote_track.filter_status.remote_raw_XYZ_valid = data(:,offset); offset=offset+1;
    remote_track.filter_status.remote_roll_pitch_corrected_XYZ_valid = data(:,offset); offset=offset+1;
    remote_track.filter_status.remote_NED_valid = data(:,offset); offset=offset+1;
    remote_track.filter_status.remote_geodeticPosition_valid = data(:,offset); offset=offset+1;
    remote_track.filter_status.remote_range_error_valid = data(:,offset); offset=offset+1;
    remote_track.filter_status.remote_azimuth_error_valid = data(:,offset); offset=offset+1;
    remote_track.filter_status.remote_elevation_error_valid = data(:,offset); offset=offset+1;
    remote_track.filter_status.remote_position_error_valid = data(:,offset); offset=offset+1;
    remote_track.filter_status.remote_depth_valid = data(:,offset); offset=offset+1;
    remote_track.filter_status.signal_level_valid = data(:,offset); offset=offset+1;
    remote_track.filter_status.signal_noise_ratio_valid = data(:,offset); offset=offset+1;
    remote_track.filter_status.signal_correlation_ratio_valid = data(:,offset); offset=offset+1;
    remote_track.filter_status.signal_correlation_interference_valid = data(:,offset); offset=offset+1;
       
        
    remote_track.local_position = data(:,offset:offset+2); offset=offset+3;
    remote_track.local_velocity = data(:,offset:offset+2); offset=offset+3;
    remote_track.local_orientation = data(:,offset:offset+2); offset=offset+3;
    remote_track.local_position_error = data(:,offset:offset+2); offset=offset+3;
    remote_track.local_orientation_error = data(:,offset:offset+2); offset=offset+3;
    remote_track.local_depth = data(:,offset); offset=offset+1;
        
    remote_track.remote_age_us = data(:,offset); offset=offset+1;
    
    remote_track.remote_range = data(:,offset); offset=offset+1;
    remote_track.remote_azimuth = data(:,offset); offset=offset+1;
    remote_track.remote_elevation = data(:,offset); offset=offset+1;
    
    remote_track.remote_raw_XYZ = data(:,offset:offset+2); offset=offset+3;
    remote_track.remote_roll_pitch_corrected_XYZ = data(:,offset:offset+2); offset=offset+3;
    remote_track.remote_NED = data(:,offset:offset+2); offset=offset+3;
    remote_track.remote_position = data(:,offset:offset+2); offset=offset+3;
    remote_track.remote_range_error = data(:,offset); offset=offset+1;
    remote_track.remote_azimuth_error = data(:,offset); offset=offset+1;
    remote_track.remote_elevation_error = data(:,offset); offset=offset+1;
    remote_track.remote_position_error = data(:,offset:offset+2); offset=offset+3;
    remote_track.remote_depth = data(:,offset); offset=offset+1;
    
    remote_track.signal_level = data(:,offset); offset=offset+1;
    remote_track.signal_noise_ratio = data(:,offset); offset=offset+1;
    remote_track.signal_correlation_ratio = data(:,offset); offset=offset+1;
    remote_track.signal_correlation_interference = data(:,offset);
        
end
