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
    class ANPacket20
    {
        public ANPacket23 status;

        public long unixTimeStamp;
        public long microseconds;
        public DateTime time;

        public double[] position;
        public float[] velocity;
        public float[] acceleration;
        public float gForce;
        public float[] orientation;
        public float[] angularVelocity;
        public float[] positionStandardDeviation;
        public float[] orientationStandardDeviation;

        public ANPacket20()
        {
            status = new ANPacket23();
            unixTimeStamp = 0;
            microseconds = 0;
            time = new DateTime(0);
            position = new double[3];
            position[0] = 0;
            position[1] = 0;
            position[2] = 0;
            velocity = new float[3];
            velocity[0] = 0;
            velocity[1] = 0;
            velocity[2] = 0;
            acceleration = new float[3];
            acceleration[0] = 0;
            acceleration[1] = 0;
            acceleration[2] = 0;
            gForce = 0;
            orientation = new float[3];
            orientation[0] = 0;
            orientation[1] = 0;
            orientation[2] = 0;
            angularVelocity = new float[3];
            angularVelocity[0] = 0;
            angularVelocity[1] = 0;
            angularVelocity[2] = 0;
            positionStandardDeviation = new float[3];
            positionStandardDeviation[0] = (float)1000000.0;
            positionStandardDeviation[1] = (float)1000000.0;
            positionStandardDeviation[2] = (float)1000000.0;
            orientationStandardDeviation = new float[3];
            orientationStandardDeviation[0] = (float)1000000.0;
            orientationStandardDeviation[1] = (float)1000000.0;
            orientationStandardDeviation[2] = (float)1000000.0;

        }

        public ANPacket20(ANPacket packet)
        {
            status = new ANPacket23(packet.data, 0);

            unixTimeStamp = BitConverter.ToUInt32(packet.data, 8);
            microseconds = BitConverter.ToUInt32(packet.data, 12);
            time = new DateTime(unixTimeStamp * 1000 + microseconds / 1000);

            position = new double[3];
            position[0] = BitConverter.ToDouble(packet.data, 16);
            position[1] = BitConverter.ToDouble(packet.data, 24);
            position[2] = BitConverter.ToDouble(packet.data, 32);

            velocity = new float[3];
            velocity[0] = BitConverter.ToSingle(packet.data, 40);
            velocity[1] = BitConverter.ToSingle(packet.data, 44);
            velocity[2] = BitConverter.ToSingle(packet.data, 48);

            acceleration = new float[3];
            acceleration[0] = BitConverter.ToSingle(packet.data, 52);
            acceleration[1] = BitConverter.ToSingle(packet.data, 56);
            acceleration[2] = BitConverter.ToSingle(packet.data, 60);
            gForce = BitConverter.ToSingle(packet.data, 64);

            orientation = new float[3];
            orientation[0] = BitConverter.ToSingle(packet.data, 68);
            orientation[1] = BitConverter.ToSingle(packet.data, 72);
            orientation[2] = BitConverter.ToSingle(packet.data, 76);
            if (orientation[2] < 0) orientation[2] += (float) (2 * Math.PI);

            angularVelocity = new float[3];
            angularVelocity[0] = BitConverter.ToSingle(packet.data, 80);
            angularVelocity[1] = BitConverter.ToSingle(packet.data, 84);
            angularVelocity[2] = BitConverter.ToSingle(packet.data, 88);

            positionStandardDeviation = new float[3];
            positionStandardDeviation[0] = BitConverter.ToSingle(packet.data, 92);
            positionStandardDeviation[1] = BitConverter.ToSingle(packet.data, 96);
            positionStandardDeviation[2] = BitConverter.ToSingle(packet.data, 100);

            orientationStandardDeviation = new float[3];
            orientationStandardDeviation[0] = BitConverter.ToSingle(packet.data, 104);
            orientationStandardDeviation[1] = BitConverter.ToSingle(packet.data, 108);
            orientationStandardDeviation[2] = BitConverter.ToSingle(packet.data, 112);
        }
    }
    /*class ANPacket20
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

        public Boolean gnssFix = false;

        public Boolean insFilterInitialised = false;
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

        public UInt32 utc_time;
        public UInt32 microseconds;
        public DateTime time;

        public double[] position;
        public float[] velocity;
        public float[] acceleration;
        public float gForce;
        public float[] orientation;
        public float[] angularVelocity;
        public float[] positionStandardDeviation;
        public float[] orientationStandardDeviation;

        public float depth;
        public float vos;
        public int unitID;

        public ANPacket20()
        {
            //unixTimeStamp = 0;
            microseconds = 0;
            time = new DateTime(0);
            position = new double[3];
            position[0] = 0;
            position[1] = 0;
            position[2] = 0;
            velocity = new float[3];
            velocity[0] = 0;
            velocity[1] = 0;
            velocity[2] = 0;
            acceleration = new float[3];
            acceleration[0] = 0;
            acceleration[1] = 0;
            acceleration[2] = 0;
            gForce = 0;
            orientation = new float[3];
            orientation[0] = 0;
            orientation[1] = 0;
            orientation[2] = 0;
            angularVelocity = new float[3];
            angularVelocity[0] = 0;
            angularVelocity[1] = 0;
            angularVelocity[2] = 0;
            positionStandardDeviation = new float[3];
            positionStandardDeviation[0] = (float)1000000.0;
            positionStandardDeviation[1] = (float)1000000.0;
            positionStandardDeviation[2] = (float)1000000.0;
            orientationStandardDeviation = new float[3];
            orientationStandardDeviation[0] = (float)1000000.0;
            orientationStandardDeviation[1] = (float)1000000.0;
            orientationStandardDeviation[2] = (float)1000000.0;
        }

        public ANPacket20(ANPacket packet)
        {
            if (packet.data[0] != 0)
            {
                statusAlert = true;
                if ((packet.data[0] & 0x01) != 0) systemFailure = true;
                if ((packet.data[0] & 0x02) != 0) accelerometerSensorFailure = true;
                if ((packet.data[0] & 0x04) != 0) gyroscopeSensorFailure = true;
                if ((packet.data[0] & 0x08) != 0) magnetometerSensorFailure = true;
                if ((packet.data[0] & 0x10) != 0) pressureSensorFailure = true;
                if ((packet.data[0] & 0x20) != 0) gnssFailure = true;
                if ((packet.data[0] & 0x40) != 0) accelerometerOverRange = true;
                if ((packet.data[0] & 0x80) != 0) gyroscopeOverRange = true;
            }
            if (packet.data[1] != 0)
            {
                statusAlert = true;
                if ((packet.data[1] & 0x01) != 0) magnetometerOverRange = true;
                if ((packet.data[1] & 0x02) != 0) pressureOverRange = true;
                if ((packet.data[1] & 0x04) != 0) maximumTemperatureAlarm = true;
                if ((packet.data[1] & 0x08) != 0) OutOfWaterAlarm = true;
                if ((packet.data[1] & 0x10) != 0) PressureDepthOffsetAlarm = true;
                if ((packet.data[1] & 0x20) != 0) LoggingMemoryFailure = true;
                if ((packet.data[1] & 0x40) != 0) FilterAidingCommunicationFailure = true;
                if ((packet.data[1] & 0x80) != 0) FixedPositionWithGNSSAidingAlarm = true;
            }
            if (packet.data[2] != 0)
            {
                statusAlert = true;
                if ((packet.data[2] & 0x01) != 0) FixedPositionWithAcousticAidingAlarm = true;
                if ((packet.data[2] & 0x02) != 0) MultipleAidingSourceAlarm = true;
                if ((packet.data[2] & 0x04) != 0) AidedExternalINSAlarm = true;
                if ((packet.data[2] & 0x08) != 0) AidingGNSSTimeSyncAlarm = true;
                if ((packet.data[2] & 0x10) != 0) MultipleMasterAlarm = true;
                if ((packet.data[2] & 0x20) != 0) ModemDataDisabledAlarm = true;
                if ((packet.data[2] & 0x40) != 0) BrownoutAlarm = true;
            }

            if ((packet.data[4] & 0x01) != 0) OrientationFilterInitialised = true;
            if ((packet.data[4] & 0x02) != 0) insFilterInitialised = true;
            if ((packet.data[4] & 0x04) != 0) HeadingInitialised = true;
            if ((packet.data[4] & 0x08) != 0) utcTimeValid = true;
            gnssFixType = (packet.data[4] & 0x70) >> 4;
            if (gnssFixType > GNSS_NO_FIX) gnssFix = true;
            if ((packet.data[4] & 0x80) != 0) AcousticHeadingActive = true;

            if ((packet.data[5] & 0x01) != 0) AcousticPositionActive = true;
            if ((packet.data[5] & 0x02) != 0) internalGnssActive = true;
            if ((packet.data[5] & 0x04) != 0) magnetometersActive = true;
            if ((packet.data[5] & 0x08) != 0) velocityHeadingActive = true;
            if ((packet.data[5] & 0x10) != 0) PressureDepthPositionActive = true;
            if ((packet.data[5] & 0x20) != 0) externalPositionActive = true;
            if ((packet.data[5] & 0x40) != 0) externalVelocityActive = true;
            if ((packet.data[5] & 0x80) != 0) externalHeadingActive = true;

            if ((packet.data[6] & 0x01) != 0) DualAntennaHeadingActive = true;
            if ((packet.data[6] & 0x02) != 0) FixedPositionActive = true;
            if ((packet.data[6] & 0x04) != 0) FixedHeadingActive = true;
            if ((packet.data[6] & 0x08) != 0) FixedRollPitchActive = true;
            if ((packet.data[6] & 0x10) != 0) PressureDepthHeightActive = true;
            if ((packet.data[6] & 0x20) != 0) ExternalGNSSEnabled = true;
            if ((packet.data[6] & 0x40) != 0) PressureDepthInitialised = true;
            if ((packet.data[6] & 0x80) != 0) InternalVOSInitialised = true;

            if ((packet.data[7] & 0x01) != 0) USBLTrackingActive = true;

            utc_time = BitConverter.ToUInt32(packet.data, 8);
            microseconds = BitConverter.ToUInt32(packet.data, 12);
            time = new DateTime(utc_time * 10000000 + microseconds * 10);

            position = new double[3];
            position[0] = BitConverter.ToDouble(packet.data, 16);
            position[1] = BitConverter.ToDouble(packet.data, 24);
            position[2] = BitConverter.ToDouble(packet.data, 32);

            velocity = new float[3];
            velocity[0] = BitConverter.ToSingle(packet.data, 40);
            velocity[1] = BitConverter.ToSingle(packet.data, 44);
            velocity[2] = BitConverter.ToSingle(packet.data, 48);

            acceleration = new float[3];
            acceleration[0] = BitConverter.ToSingle(packet.data, 52);
            acceleration[1] = BitConverter.ToSingle(packet.data, 56);
            acceleration[2] = BitConverter.ToSingle(packet.data, 60);
            gForce = BitConverter.ToSingle(packet.data, 64);

            orientation = new float[3];
            orientation[0] = BitConverter.ToSingle(packet.data, 68);
            orientation[1] = BitConverter.ToSingle(packet.data, 72);
            orientation[2] = BitConverter.ToSingle(packet.data, 76);

            angularVelocity = new float[3];
            angularVelocity[0] = BitConverter.ToSingle(packet.data, 80);
            angularVelocity[1] = BitConverter.ToSingle(packet.data, 84);
            angularVelocity[2] = BitConverter.ToSingle(packet.data, 88);

            positionStandardDeviation = new float[3];
            positionStandardDeviation[0] = BitConverter.ToSingle(packet.data, 92);
            positionStandardDeviation[1] = BitConverter.ToSingle(packet.data, 96);
            positionStandardDeviation[2] = BitConverter.ToSingle(packet.data, 100);

            orientationStandardDeviation = new float[3];
            orientationStandardDeviation[0] = BitConverter.ToSingle(packet.data, 104);
            orientationStandardDeviation[1] = BitConverter.ToSingle(packet.data, 108);
            orientationStandardDeviation[2] = BitConverter.ToSingle(packet.data, 112);
        }
    }*/
}
