/****************************************************************/
/*                                                              */
/*          Advanced Navigation Packet Protocol Library         */
/*          .NET C# Language Spatial SDK, Version 2.4           */
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

namespace ANPPExample
{
    class ANPacket
    {
        public const int PACKET_ID_ACKNOWLEDGE = 0;
        public const int PACKET_ID_REQUEST = 1;
        public const int PACKET_ID_BOOT_MODE = 2;
        public const int PACKET_ID_DEVICE_INFORMATION = 3;
        public const int PACKET_ID_RESTORE_FACTORY_SETTINGS = 4;
        public const int PACKET_ID_RESET = 5;
        public const int PACKET_ID_PRINT = 6;
        public const int PACKET_ID_FILE_TRANSFER_REQUEST = 7;
        public const int PACKET_ID_FILE_TRANSFER_ACKNOWLEDGE = 8;
        public const int PACKET_ID_FILE_TRANSFER = 9;
        public const int PACKET_ID_PASS_THROUGH = 10;
        public const int PACKET_ID_NETWORK_SETTINGS = 11;
        public const int PACKET_ID_HOSTNAME = 12;

        public const int PACKET_ID_SYSTEM_STATE = 20;
        public const int PACKET_ID_UNIX_TIME = 21;
        public const int PACKET_ID_STATUS = 23;
        public const int PACKET_ID_REMOTE_UNIT_TRACK = 24;
        public const int PACKET_ID_REMOTE_STATE = 25;

        public const int PACKET_ID_RAW_SENSORS_PACKET = 28;
        public const int PACKET_ID_REMOTE_RAW_SENSORS_PACKET = 29;

        public const int PACKET_ID_EXTERNAL_POSITION_AND_VELOCITY = 44;
        public const int PACKET_ID_EXTERNAL_POSITION = 45;
        public const int PACKET_ID_EXTERNAL_VELOCITY = 46;
        public const int PACKET_ID_EXTERNAL_BODY_VELOCITY = 47;
        public const int PACKET_ID_EXTERNAL_HEADING = 48;
        public const int PACKET_ID_EXTERNAL_TIME = 52;
        public const int PACKET_ID_EXTERNAL_DEPTH = 53;
        public const int PACKET_ID_HEAVE = 58;

        public const int PACKET_ID_SUBSONUS_DATA_PACKET = 70;

        public const int PACKET_ID_CONFIGURATION_PACKETS_START = 180;
        public const int PACKET_ID_PACKET_PERIODS = 181;
        public const int PACKET_ID_SENSOR_RANGES = 184;
        public const int PACKET_ID_INSTALLATION_ALIGNMENT = 185;
        public const int PACKET_ID_SYSTEM_OPTIONS = 186;
        public const int PACKET_ID_FIXED_POSITION = 187;
        public const int PACKET_ID_DEVICE_ADDRESS = 192;
        public const int PACKET_ID_TIME_CONFIGURATION = 193;
        public const int PACKET_ID_NMEA_OUTPUT_CONFIGURATION = 195;
        public const int PACKET_ID_DEVICE_CONFIGURATION = 196;
        public const int PACKET_ID_PORT_CONFIGURATION = 197;
        public const int PACKET_ID_MODEM_CONFIGURATION = 198;
        public const int PACKET_ID_ACOUSTIC_DATA_PRIORITY = 200;

        public int id;
        public int length;
        public byte[] data;

        public ANPacket(int length, int id)
        {
            this.length = length;
            this.id = id;
            this.data = new byte[length];
        }
    }
}
