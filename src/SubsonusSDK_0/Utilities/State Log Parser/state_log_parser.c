/****************************************************************/
/*                                                              */
/*          Advanced Navigation Packet Protocol Library         */
/*                 State Log Parser, Version 2.4                */
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

#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <string.h>
#include <math.h>

#include "../../C/lib/an_packet_protocol.h"
#include "../../C/lib/subsonus_packets.h"

#define DEGREES_TO_RADIANS (M_PI/180.0)

int main(int argc, char *argv[])
{
    FILE *log_file;
    char buffer[512];
    subsonus_system_state_packet_t system_state_packet;
    int valid_line_count = 0;
    
    if(argc != 2)
    {
        printf("Incorrect number of arguments supplied\n");
        printf("Usage: state_log_parser.exe State.csv\n");
        exit(EXIT_FAILURE);
    }
    
    log_file = fopen(argv[1], "r");
    if(log_file == NULL)
    {
        printf("Error opening log file\n");
        exit(EXIT_FAILURE);
    }
    fgets(buffer, sizeof(buffer), log_file);
    
    while(fgets(buffer, sizeof(buffer), log_file) != NULL)
    {
        int token_count = 0;
        char *token;
        system_state_packet.system_status.r = 0;
        system_state_packet.filter_status.r = 0;
        strtok(buffer,",");
        while((token = strtok(NULL,",")) != NULL)
        {
			if(token_count == 0) ;
            else if(token_count == 1) system_state_packet.unix_time_seconds = strtoul(token, NULL, 10);
            else if(token_count == 2) system_state_packet.microseconds = atoi(token);
			else if(token_count == 31) system_state_packet.filter_status.b.gnss_fix_type = atoi(token);
            else if(token_count < 48)
            {
                uint8_t boolean = 0;
                if(!strcmp(token,"1") || !strcmp(token, "true"))
                {
                    boolean = 1;
                }
                else if (!strcmp(token,"0") || !strcmp(token, "false"))
                {
					boolean = 0;
				}
				else
				{
					printf("Error on Token: %d, contains: %s\n",token_count,token);
				}
				
                if(token_count < 26)
                {
                    system_state_packet.system_status.r |= boolean<<(token_count-3);
                }
                else
                {
                    system_state_packet.filter_status.r |= boolean<<(token_count-29);
                }
            }
            else if(token_count == 48) system_state_packet.latitude = atof(token)*DEGREES_TO_RADIANS;
            else if(token_count == 49) system_state_packet.longitude = atof(token)*DEGREES_TO_RADIANS;
            else if(token_count == 50) system_state_packet.height = atof(token);
            else if(token_count == 51) system_state_packet.velocity[0] = atof(token);
            else if(token_count == 52) system_state_packet.velocity[1] = atof(token);
            else if(token_count == 53) system_state_packet.velocity[2] = atof(token);
            else if(token_count == 54) system_state_packet.body_acceleration[0] = atof(token);
            else if(token_count == 55) system_state_packet.body_acceleration[1] = atof(token);
            else if(token_count == 56) system_state_packet.body_acceleration[2] = atof(token);
            else if(token_count == 57) system_state_packet.g_force = atof(token);
            else if(token_count == 58) system_state_packet.orientation[0] = atof(token)*DEGREES_TO_RADIANS;
            else if(token_count == 59) system_state_packet.orientation[1] = atof(token)*DEGREES_TO_RADIANS;
            else if(token_count == 60) system_state_packet.orientation[2] = atof(token)*DEGREES_TO_RADIANS;
            else if(token_count == 61) system_state_packet.angular_velocity[0] = atof(token)*DEGREES_TO_RADIANS;
            else if(token_count == 62) system_state_packet.angular_velocity[1] = atof(token)*DEGREES_TO_RADIANS;
            else if(token_count == 63) system_state_packet.angular_velocity[2] = atof(token)*DEGREES_TO_RADIANS;
            else if(token_count == 64) system_state_packet.position_standard_deviation[0] = atof(token);
            else if(token_count == 65) system_state_packet.position_standard_deviation[1] = atof(token);
            else if(token_count == 66) system_state_packet.position_standard_deviation[2] = atof(token);
            else if(token_count == 67) system_state_packet.orientation_standard_deviation[0] = atof(token);
            else if(token_count == 68) system_state_packet.orientation_standard_deviation[0] = atof(token);
            else if(token_count == 69) system_state_packet.orientation_standard_deviation[0] = atof(token);
            
            token_count++;
        }

        if(token_count == 70)
        {
            valid_line_count++;
            
            printf("Latitude = %.12f, Filter Ready = %d\n", system_state_packet.latitude/DEGREES_TO_RADIANS, system_state_packet.filter_status.b.internal_vos_initialised);
            
            /*
             * At this point all the fields of the system_state_packet have been filled with a line from
             * the log file and the data can be processed as required.
             * e.g. printf("Latitude = %.12f, Filter Ready = %d\n", system_state_packet.latitude, system_state_packet.filter_status.b.position_filter_initialised);
             */
		 }
		 else{
			 printf("Wrong Token Count: (%d), Check the version of SubsonusTools.jar and the Subsonus Firmware that produced the log is v2.0 or greater\n", token_count);
		 }
        
    }
    fclose(log_file);
    
    printf("%d lines successfully parsed from log file\n", valid_line_count);
    return EXIT_SUCCESS;
}
