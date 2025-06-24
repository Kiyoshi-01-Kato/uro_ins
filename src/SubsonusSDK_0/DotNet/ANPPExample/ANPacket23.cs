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

using System;

namespace ANPPExample
{
    class ANPacket23
    {
        public const int GNSS_NO_FIX = 0;
        public const int GNSS_2D_FIX = 1;
        public const int GNSS_3D_FIX = 2;
        public const int GNSS_SBAS_FIX = 3;
        public const int GNSS_DIFFERENTIAL_FIX = 4;
        public const int GNSS_OMNISTAR_FIX = 5;
        public const int GNSS_RTK_FLOAT_FIX = 6;
        public const int GNSS_RTK_FIXED_FIX = 7;

        public Boolean statusAlert = false;

        public Boolean systemFailure = false;
        public Boolean accelerometerSensorFailure = false;
        public Boolean gyroscopeSensorFailure = false;
        public Boolean magnetometerSensorFailure = false;
        public Boolean pressureSensorFailure = false;
        public Boolean gnssFailure = false;
        public Boolean accelerometerOverRange = false;
        public Boolean gyroscopeOverRange = false;
        public Boolean magnetometerOverRange = false;
        public Boolean pressureOverRange = false;
        public Boolean maximumTemperatureAlarm = false;
        public Boolean OutOfWaterAlarm = false;
        public Boolean PressureDepthOffsetAlarm = false;
        public Boolean LoggingMemoryFailure = false;
        public Boolean FilterAidingCommunicationFailure = false;
        public Boolean FixedPositionWithGNSSAidingAlarm = false;
        public Boolean FixedPositionWithAcousticAidingAlarm = false;
        public Boolean MultipleAidingSourceAlarm = false;
        public Boolean AidedExternalINSAlarm = false;
        public Boolean AidingGNSSTimeSyncAlarm = false;
        public Boolean MultipleMasterAlarm = false;
        public Boolean ModemDataDisabledAlarm = false;
        public Boolean BrownoutAlarm = false;
	public Boolean AidingAlignmentAlarm = false;


        public Boolean gnssFix = false;

        public Boolean OrientationFilterInitialised = false;
        public Boolean PositionFilterInitialised = false;
        public Boolean HeadingInitialised = false;
        public Boolean utcTimeValid = false;
        public int gnssFixType = GNSS_NO_FIX;
        public Boolean AcousticHeadingActive = false;
        public Boolean AcousticPositionActive = false;
        public Boolean internalGnssActive = false;
        public Boolean magnetometersActive = false;
        public Boolean velocityHeadingActive = false;
        public Boolean PressureDepthPositionActive = false;
        public Boolean externalPositionActive = false;
        public Boolean externalVelocityActive = false;
        public Boolean externalHeadingActive = false;
        public Boolean DualAntennaHeadingActive = false;
        public Boolean FixedPositionActive = false;
        public Boolean FixedHeadingActive = false;
        public Boolean FixedRollPitchActive = false;
        public Boolean PressureDepthHeightActive = false;
        public Boolean ExternalGNSSEnabled = false;
        public Boolean PressureDepthInitialised = false;
        public Boolean InternalVOSInitialised = false;
        public Boolean USBLTrackingActive = false;

        public ANPacket23()
        {
        }

        public ANPacket23(byte[] data, int offset)
        {
            if (data[offset] != 0)
            {
                statusAlert = true;
                if ((data[offset] & 0x01) != 0) systemFailure = true;
                if ((data[offset] & 0x02) != 0) accelerometerSensorFailure = true;
                if ((data[offset] & 0x04) != 0) gyroscopeSensorFailure = true;
                if ((data[offset] & 0x08) != 0) magnetometerSensorFailure = true;
                if ((data[offset] & 0x10) != 0) pressureSensorFailure = true;
                if ((data[offset] & 0x20) != 0) gnssFailure = true;
                if ((data[offset] & 0x40) != 0) accelerometerOverRange = true;
                if ((data[offset] & 0x80) != 0) gyroscopeOverRange = true;
            }
            if (data[offset + 1] != 0)
            {
                statusAlert = true;
                if ((data[offset + 1] & 0x01) != 0) magnetometerOverRange = true;
                if ((data[offset + 1] & 0x02) != 0) pressureOverRange = true;
                if ((data[offset + 1] & 0x04) != 0) maximumTemperatureAlarm = true;
                if ((data[offset + 1] & 0x08) != 0) OutOfWaterAlarm = true;
                if ((data[offset + 1] & 0x10) != 0) PressureDepthOffsetAlarm = true;
                if ((data[offset + 1] & 0x20) != 0) LoggingMemoryFailure = true;
                if ((data[offset + 1] & 0x40) != 0) FilterAidingCommunicationFailure = true;
                if ((data[offset + 1] & 0x80) != 0) FixedPositionWithGNSSAidingAlarm = true;
            }
            if (data[offset + 2] != 0)
            {
                statusAlert = true;
                if ((data[offset + 2] & 0x01) != 0) FixedPositionWithAcousticAidingAlarm = true;
                if ((data[offset + 2] & 0x02) != 0) MultipleAidingSourceAlarm = true;
                if ((data[offset + 2] & 0x04) != 0) AidedExternalINSAlarm = true;
                if ((data[offset + 2] & 0x08) != 0) AidingGNSSTimeSyncAlarm = true;
                if ((data[offset + 2] & 0x10) != 0) MultipleMasterAlarm = true;
                if ((data[offset + 2] & 0x20) != 0) ModemDataDisabledAlarm = true;
                if ((data[offset + 2] & 0x40) != 0) BrownoutAlarm = true;
                if ((data[offset + 2] & 0x80) != 0) AidingAlignmentAlarm = true;
            }

            if ((data[offset + 4] & 0x01) != 0) OrientationFilterInitialised = true;
            if ((data[offset + 4] & 0x02) != 0) PositionFilterInitialised = true;
            if ((data[offset + 4] & 0x04) != 0) HeadingInitialised = true;
            if ((data[offset + 4] & 0x08) != 0) utcTimeValid = true;
            gnssFixType = (data[offset + 4] & 0x70) >> 4;
            if (gnssFixType > GNSS_NO_FIX) gnssFix = true;
            if ((data[offset + 4] & 0x80) != 0) AcousticHeadingActive = true;

            if ((data[offset + 5] & 0x01) != 0) AcousticPositionActive = true;
            if ((data[offset + 5] & 0x02) != 0) internalGnssActive = true;
            if ((data[offset + 5] & 0x04) != 0) magnetometersActive = true;
            if ((data[offset + 5] & 0x08) != 0) velocityHeadingActive = true;
            if ((data[offset + 5] & 0x10) != 0) PressureDepthPositionActive = true;
            if ((data[offset + 5] & 0x20) != 0) externalPositionActive = true;
            if ((data[offset + 5] & 0x40) != 0) externalVelocityActive = true;
            if ((data[offset + 5] & 0x80) != 0) externalHeadingActive = true;

            if ((data[offset + 6] & 0x01) != 0) DualAntennaHeadingActive = true;
            if ((data[offset + 6] & 0x02) != 0) FixedPositionActive = true;
            if ((data[offset + 6] & 0x04) != 0) FixedHeadingActive = true;
            if ((data[offset + 6] & 0x08) != 0) FixedRollPitchActive = true;
            if ((data[offset + 6] & 0x10) != 0) PressureDepthHeightActive = true;
            if ((data[offset + 6] & 0x20) != 0) ExternalGNSSEnabled = true;
            if ((data[offset + 6] & 0x40) != 0) PressureDepthInitialised = true;
            if ((data[offset + 6] & 0x80) != 0) InternalVOSInitialised = true;

            if ((data[offset + 7] & 0x01) != 0) USBLTrackingActive = true;
        }
    }
}
