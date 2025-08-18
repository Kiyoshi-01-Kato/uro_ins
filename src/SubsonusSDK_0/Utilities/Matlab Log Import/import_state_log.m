% /****************************************************************/
% /*                                                              */
% /*          Advanced Navigation Packet Protocol Library         */
% /*                Matlab Log Import, Version 2.04                */
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

function state_data = import_state_log(filename)
     data = csvread(filename, 2, 1);
    offset = 1;
    state_data.unix_time_seconds = data(:,offset) + data(:,offset+1)/1000000; offset=offset+2;
        
    state_data.system_status.system_failure = data(:,offset); offset=offset+1;
    state_data.system_status.accelerometer_failure = data(:,offset); offset=offset+1;
    state_data.system_status.gyroscope_failure = data(:,offset); offset=offset+1;
    state_data.system_status.magnetometer_failure = data(:,offset); offset=offset+1;
    state_data.system_status.pressure_failure = data(:,offset); offset=offset+1;
    state_data.system_status.gnss_failure = data(:,offset); offset=offset+1;
    state_data.system_status.accelerometer_overrange = data(:,offset); offset=offset+1;
    state_data.system_status.gyroscope_overrange = data(:,offset); offset=offset+1;
    state_data.system_status.magnetometer_overrange = data(:,offset); offset=offset+1;
    state_data.system_status.pressure_overrange = data(:,offset); offset=offset+1;
    state_data.system_status.maximum_temperature = data(:,offset); offset=offset+1;
    state_data.system_status.out_of_water = data(:,offset); offset=offset+1;
    state_data.system_status.pressure_depth_failure = data(:,offset); offset=offset+1;
    state_data.system_status.logging_memory_failure = data(:,offset); offset=offset+1;
    state_data.system_status.filter_aiding_comms = data(:,offset); offset=offset+1;
    state_data.system_status.fixed_position_gnss_aiding_alarm = data(:,offset); offset=offset+1;
    state_data.system_status.fixed_position_acoustic_aiding_alarm = data(:,offset); offset=offset+1;
    state_data.system_status.multiple_aiding_source_alarm = data(:,offset); offset=offset+1;
    state_data.system_status.aided_external_ins_alarm = data(:,offset); offset=offset+1;
    state_data.system_status.aiding_gnss_time_sync_alarm = data(:,offset); offset=offset+1;
    state_data.system_status.multiple_master_alarm = data(:,offset); offset=offset+1;
    state_data.system_status.modem_data_disabled_alarm = data(:,offset); offset=offset+1;
    state_data.system_status.brownout_alarm = data(:,offset); offset=offset+1;
    state_data.system_status.aiding_alignment_alarm = data(:,offset); offset=offset+1;
        
    state_data.filter_status.orientation_initialised = data(:,offset); offset=offset+1;
    state_data.filter_status.navigation_filter_initialised = data(:,offset); offset=offset+1;
    state_data.filter_status.heading_initialised = data(:,offset); offset=offset+1;
    state_data.filter_status.utc_time_initialised = data(:,offset); offset=offset+1;
    state_data.filter_status.gnss_fix_type = data(:,offset); offset=offset+1;
    state_data.filter_status.acoustic_heading_active = data(:,offset); offset=offset+1;
    state_data.filter_status.acoustic_position_active = data(:,offset); offset=offset+1;
    state_data.filter_status.internal_gnss_enabled = data(:,offset); offset=offset+1;
    state_data.filter_status.magnetometers_enabled = data(:,offset); offset=offset+1;
    state_data.filter_status.velocity_heading_enabled = data(:,offset); offset=offset+1;
    state_data.filter_status.pressure_depth_position_active = data(:,offset); offset=offset+1;
    state_data.filter_status.external_position_active = data(:,offset); offset=offset+1;
    state_data.filter_status.external_velocity_active = data(:,offset); offset=offset+1;
    state_data.filter_status.external_heading_active = data(:,offset); offset=offset+1;
    state_data.filter_status.dual_antenna_heading_active = data(:,offset); offset=offset+1;
    state_data.filter_status.fixed_position_active = data(:,offset); offset=offset+1;
    state_data.filter_status.fixed_heading_active = data(:,offset); offset=offset+1;
    state_data.filter_status.fixed_roll_pitch_active = data(:,offset); offset=offset+1;
    state_data.filter_status.pressure_depth_height_active = data(:,offset); offset=offset+1;
    state_data.filter_status.external_gnss_enabled = data(:,offset); offset=offset+1;
    state_data.filter_status.pressure_depth_initialised = data(:,offset); offset=offset+1;
    state_data.filter_status.internal_velocity_of_sound_active = data(:,offset); offset=offset+1;
    state_data.filter_status.usbl_tracking_active = data(:,offset); offset=offset+1;    
    
    state_data.position = data(:,offset:offset+2); offset=offset+3;
    state_data.velocity = data(:,offset:offset+2); offset=offset+3;
    state_data.acceleration = data(:,offset:offset+2); offset=offset+3;
    state_data.g_force = data(:,offset); offset=offset+1; 
    state_data.orientation = data(:,offset:offset+2); offset=offset+3;
    state_data.angular_velocity = data(:,offset:offset+2); offset=offset+3;
    state_data.position_error = data(:,offset:offset+2); offset=offset+3;
    state_data.orientation_error = data(:,offset:offset+2); offset=offset+3;
        
end
