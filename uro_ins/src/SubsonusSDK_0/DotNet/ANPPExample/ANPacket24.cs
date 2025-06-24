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
    class ANPacket24
    {
        public int RemoteAddress;
        public Boolean DataConnectionActive = false;
        public Boolean DepthCorrectionApplied = false;
        public ANPacket23 status;

        public Boolean LocalTimeValid = false;
        public Boolean LocalPositionValid = false;
        public Boolean LocalVelocityValid = false;
        public Boolean LocalOrientationValid = false;
        public Boolean LocalPositionStandardDeviationValid = false;
        public Boolean LocalOrientationStandardDeviationValid = false;
        public Boolean LocalDepthValid = false;
        public Boolean RemoteAgeValid = false;
        public Boolean RemoteRangeValid = false;
        public Boolean RemoteAzimuthValid = false;
        public Boolean RemoteElevationValid = false;
        public Boolean RemoteRawXYZValid = false;
        public Boolean RemoteRollPitchCorrectedXYZValid = false;
        public Boolean RemoteNEDValid = false;
        public Boolean RemoteGeodeticPositionValid = false;
        public Boolean RemoteRangeStandardDeviationValid = false;
        public Boolean RemoteAzimuthStandardDeviationValid = false;
        public Boolean RemoteElevationStandardDeviationValid = false;
        public Boolean RemotePositionStandardDeviationValid = false;
        public Boolean RemoteDepthValid = false;
        public Boolean SignalLevelValid = false;
        public Boolean SignalNoiseRatioValid = false;
        public Boolean SignalCorrelationRatioValid = false;
        public Boolean SignalCorrelationInterferenceValid = false;

        public long LocalUnixTimeStamp;
        public long LocalMicroseconds;
        public DateTime LocalTime;
        public double[] LocalPosition;
        public float[] LocalVelocity;
        public float[] LocalOrientation;
        public float[] LocalPositionStandardDeviation;
        public float[] LocalOrientationStandardDeviation;
        public float Localdepth;
        public float RemoteAgeMicroseconds;
        public float RemoteRange;
        public float RemoteAzimuth;
        public float RemoteElevation;

        public float[] RemoteRawPosition;
        public float[] RemoteCorrectedPosition;
        public float[] RemoteNED;
        public double[] RemoteGeodeticPosition;

        public float RemoteRangeStandardDeviation;
        public float RemoteAzimuthStandardDeviation;
        public float RemoteElevationStandardDeviation;

        public float[] RemotePositionStandardDeviation;

        public float RemoteDepth;

        public int SignalLevel;
        public int SignalToNoise;
        public int SignalCorrelation;
        public int SignalCorrelationInterference;

        public ANPacket24()
        {
            RemoteAddress = 0;
            DataConnectionActive = false;
            DepthCorrectionApplied = false;
            status = new ANPacket23();

            LocalTimeValid = false;
            LocalPositionValid = false;
            LocalVelocityValid = false;
            LocalOrientationValid = false;
            LocalPositionStandardDeviationValid = false;
            LocalOrientationStandardDeviationValid = false;
            LocalDepthValid = false;
            RemoteAgeValid = false;
            RemoteRangeValid = false;
            RemoteAzimuthValid = false;
            RemoteElevationValid = false;
            RemoteRawXYZValid = false;
            RemoteRollPitchCorrectedXYZValid = false;
            RemoteNEDValid = false;
            RemoteGeodeticPositionValid = false;
            RemoteRangeStandardDeviationValid = false;
            RemoteAzimuthStandardDeviationValid = false;
            RemoteElevationStandardDeviationValid = false;
            RemotePositionStandardDeviationValid = false;
            RemoteDepthValid = false;
            SignalLevelValid = false;
            SignalNoiseRatioValid = false;
            SignalCorrelationRatioValid = false;
            SignalCorrelationInterferenceValid = false;

            LocalUnixTimeStamp = 0;
            LocalMicroseconds = 0;
            LocalTime = new DateTime(0);

            LocalPosition = new double[3];
            LocalPosition[0] = 0;
            LocalPosition[1] = 0;
            LocalPosition[2] = 0;

            LocalVelocity = new float[3];
            LocalVelocity[0] = 0;
            LocalVelocity[1] = 0;
            LocalVelocity[2] = 0;

            LocalOrientation = new float[3];
            LocalOrientation[0] = 0;
            LocalOrientation[1] = 0;
            LocalOrientation[2] = 0;

            LocalPositionStandardDeviation = new float[3];
            LocalPositionStandardDeviation[0] = (float)1000000.0;
            LocalPositionStandardDeviation[1] = (float)1000000.0;
            LocalPositionStandardDeviation[2] = (float)1000000.0;

            LocalOrientationStandardDeviation = new float[3];
            LocalOrientationStandardDeviation[0] = (float)1000000.0;
            LocalOrientationStandardDeviation[1] = (float)1000000.0;
            LocalOrientationStandardDeviation[2] = (float)1000000.0;
            Localdepth = 0;
            RemoteAgeMicroseconds = 0;
            RemoteRange = 0;
            RemoteAzimuth = 0;
            RemoteElevation = 0;

            RemoteRawPosition = new float[3];
            RemoteRawPosition[0] = 0;
            RemoteRawPosition[1] = 0;
            RemoteRawPosition[2] = 0;

            RemoteCorrectedPosition = new float[3];
            RemoteCorrectedPosition[0] = 0;
            RemoteCorrectedPosition[1] = 0;
            RemoteCorrectedPosition[2] = 0;

            RemoteNED = new float[3];
            RemoteNED[0] = 0;
            RemoteNED[1] = 0;
            RemoteNED[2] = 0;

            RemoteGeodeticPosition = new double[3];
            RemoteGeodeticPosition[0] = 0;
            RemoteGeodeticPosition[1] = 0;
            RemoteGeodeticPosition[2] = 0;

            RemoteRangeStandardDeviation = 0;
            RemoteAzimuthStandardDeviation = 0;
            RemoteElevationStandardDeviation = 0;

            RemotePositionStandardDeviation = new float[3];
            RemotePositionStandardDeviation[0] = (float)1000000.0;
            RemotePositionStandardDeviation[1] = (float)1000000.0;
            RemotePositionStandardDeviation[2] = (float)1000000.0;

            RemoteDepth = 0;

            SignalLevel = 0;
            SignalToNoise = 0;
            SignalCorrelation = 0;
            SignalCorrelationInterference = 0;
        }

        public ANPacket24(ANPacket packet)
        {
            RemoteAddress = (int)BitConverter.ToUInt16(packet.data, 0);

            if ((packet.data[2] & 0x01) != 0) DataConnectionActive = true;
            if ((packet.data[2] & 0x02) != 0) DepthCorrectionApplied = true;

            status = new ANPacket23(packet.data, 3);

            int offset = 11; // Data Valid Flags Offset		
            if ((packet.data[offset] & 0x01) != 0) LocalTimeValid = true;
            if ((packet.data[offset] & 0x02) != 0) LocalPositionValid = true;
            if ((packet.data[offset] & 0x04) != 0) LocalVelocityValid = true;
            if ((packet.data[offset] & 0x08) != 0) LocalOrientationValid = true;
            if ((packet.data[offset] & 0x10) != 0) LocalPositionStandardDeviationValid = true;
            if ((packet.data[offset] & 0x20) != 0) LocalOrientationStandardDeviationValid = true;
            if ((packet.data[offset] & 0x40) != 0) LocalDepthValid = true;
            if ((packet.data[offset] & 0x80) != 0) RemoteAgeValid = true;

            if ((packet.data[offset + 1] & 0x01) != 0) RemoteRangeValid = true;
            if ((packet.data[offset + 1] & 0x02) != 0) RemoteAzimuthValid = true;
            if ((packet.data[offset + 1] & 0x04) != 0) RemoteElevationValid = true;
            if ((packet.data[offset + 1] & 0x08) != 0) RemoteRawXYZValid = true;
            if ((packet.data[offset + 1] & 0x10) != 0) RemoteRollPitchCorrectedXYZValid = true;
            if ((packet.data[offset + 1] & 0x20) != 0) RemoteNEDValid = true;
            if ((packet.data[offset + 1] & 0x40) != 0) RemoteGeodeticPositionValid = true;
            if ((packet.data[offset + 1] & 0x80) != 0) RemoteRangeStandardDeviationValid = true;

            if ((packet.data[offset + 2] & 0x01) != 0) RemoteAzimuthStandardDeviationValid = true;
            if ((packet.data[offset + 2] & 0x02) != 0) RemoteElevationStandardDeviationValid = true;
            if ((packet.data[offset + 2] & 0x04) != 0) RemotePositionStandardDeviationValid = true;
            if ((packet.data[offset + 2] & 0x08) != 0) RemoteDepthValid = true;
            if ((packet.data[offset + 2] & 0x10) != 0) SignalLevelValid = true;
            if ((packet.data[offset + 2] & 0x20) != 0) SignalNoiseRatioValid = true;
            if ((packet.data[offset + 2] & 0x40) != 0) SignalCorrelationRatioValid = true;
            if ((packet.data[offset + 2] & 0x80) != 0) SignalCorrelationInterferenceValid = true;

            LocalUnixTimeStamp = BitConverter.ToUInt32(packet.data, 15);
            LocalMicroseconds = BitConverter.ToUInt32(packet.data, 19);
            LocalTime = new DateTime(LocalUnixTimeStamp * 1000 + LocalMicroseconds / 1000);

            LocalPosition = new double[3];
            LocalPosition[0] = BitConverter.ToDouble(packet.data, 23);
            LocalPosition[1] = BitConverter.ToDouble(packet.data, 31);
            LocalPosition[2] = BitConverter.ToDouble(packet.data, 39);

            LocalVelocity = new float[3];
            LocalVelocity[0] = BitConverter.ToSingle(packet.data, 47);
            LocalVelocity[1] = BitConverter.ToSingle(packet.data, 51);
            LocalVelocity[2] = BitConverter.ToSingle(packet.data, 55);

            LocalOrientation = new float[3];
            LocalOrientation[0] = BitConverter.ToSingle(packet.data, 59);
            LocalOrientation[1] = BitConverter.ToSingle(packet.data, 63);
            LocalOrientation[2] = BitConverter.ToSingle(packet.data, 67);

            LocalPositionStandardDeviation = new float[3];
            LocalPositionStandardDeviation[0] = BitConverter.ToSingle(packet.data, 71);
            LocalPositionStandardDeviation[1] = BitConverter.ToSingle(packet.data, 75);
            LocalPositionStandardDeviation[2] = BitConverter.ToSingle(packet.data, 79);

            LocalOrientationStandardDeviation = new float[3];
            LocalOrientationStandardDeviation[0] = BitConverter.ToSingle(packet.data, 83);
            LocalOrientationStandardDeviation[1] = BitConverter.ToSingle(packet.data, 87);
            LocalOrientationStandardDeviation[2] = BitConverter.ToSingle(packet.data, 91);

            Localdepth = BitConverter.ToSingle(packet.data, 95);
            RemoteAgeMicroseconds = BitConverter.ToUInt32(packet.data, 99);
            RemoteRange = BitConverter.ToSingle(packet.data, 103);
            RemoteAzimuth = BitConverter.ToSingle(packet.data, 107);
            RemoteElevation = BitConverter.ToSingle(packet.data, 111);

            RemoteRawPosition = new float[3];
            RemoteRawPosition[0] = BitConverter.ToSingle(packet.data, 115);
            RemoteRawPosition[1] = BitConverter.ToSingle(packet.data, 119);
            RemoteRawPosition[2] = BitConverter.ToSingle(packet.data, 123);

            RemoteCorrectedPosition = new float[3];
            RemoteCorrectedPosition[0] = BitConverter.ToSingle(packet.data, 127);
            RemoteCorrectedPosition[1] = BitConverter.ToSingle(packet.data, 131);
            RemoteCorrectedPosition[2] = BitConverter.ToSingle(packet.data, 135);

            RemoteNED = new float[3];
            RemoteNED[0] = BitConverter.ToSingle(packet.data, 139);
            RemoteNED[1] = BitConverter.ToSingle(packet.data, 143);
            RemoteNED[2] = BitConverter.ToSingle(packet.data, 147);

            RemoteGeodeticPosition = new double[3];
            RemoteGeodeticPosition[0] = BitConverter.ToDouble(packet.data, 151);
            RemoteGeodeticPosition[1] = BitConverter.ToDouble(packet.data, 159);
            RemoteGeodeticPosition[2] = BitConverter.ToDouble(packet.data, 167);

            RemoteRangeStandardDeviation = BitConverter.ToSingle(packet.data, 175);
            RemoteAzimuthStandardDeviation = BitConverter.ToSingle(packet.data, 179);
            RemoteElevationStandardDeviation = BitConverter.ToSingle(packet.data, 183);

            RemotePositionStandardDeviation = new float[3];
            RemotePositionStandardDeviation[0] = BitConverter.ToSingle(packet.data, 187);
            RemotePositionStandardDeviation[1] = BitConverter.ToSingle(packet.data, 191);
            RemotePositionStandardDeviation[2] = BitConverter.ToSingle(packet.data, 195);

            RemoteDepth = BitConverter.ToSingle(packet.data, 199);

            SignalLevel = (int)(packet.data[203] & 0xFF);
            SignalToNoise = (int)packet.data[204];
            SignalCorrelation = (int)(packet.data[205] & 0xFF);
            SignalCorrelationInterference = (int)(packet.data[206] & 0xFF);
        }
    }
    /*class ANPacket24
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

        public int RemoteAddress;
        public Boolean DataConnectionActive = false;
        public Boolean DepthCorrectionApplied = false;

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
        public Boolean PressureDepthVelocityActive = false;
        public Boolean ExternalGNSSEnabled = false;
        public Boolean PressureDepthInitialised = false;
        public Boolean InternalVOSInitialised = false;
        public Boolean USBLTrackingActive = false;

        public Boolean LocalTimeValid = false;
        public Boolean LocalPositionValid = false;
        public Boolean LocalVelocityValid = false;
        public Boolean LocalOrientationValid = false;
        public Boolean LocalPositionStandardDeviationValid = false;
        public Boolean LocalOrientationStandardDeviationValid = false;
        public Boolean LocalDepthValid = false;
        public Boolean RemoteAgeValid = false;
        public Boolean RemoteRangeValid = false;
        public Boolean RemoteAzimuthValid = false;
        public Boolean RemoteElevationValid = false;
        public Boolean RemoteRawXYZValid = false;
        public Boolean RemoteRollPitchCorrectedXYZValid = false;
        public Boolean RemoteNEDValid = false;
        public Boolean RemoteGeodeticPositionValid = false;
        public Boolean RemoteRangeStandardDeviationValid = false;
        public Boolean RemoteAzimuthStandardDeviationValid = false;
        public Boolean RemoteElevationStandardDeviationValid = false;
        public Boolean RemotePositionStandardDeviationValid = false;
        public Boolean RemoteDepthValid = false;
        public Boolean SignalLevelValid = false;
        public Boolean SignalNoiseRatioValid = false;
        public Boolean SignalCorrelationRatioValid = false;
        public Boolean SignalCorrelationInterferenceValid = false;

        public long LocalUnixTimeStamp;
        public long LocalMicroseconds;
        public DateTime LocalTime;
        public double[] LocalPosition;
        public float[] LocalVelocity;
        public float[] LocalOrientation;
        public float[] LocalPositionStandardDeviation;
        public float[] LocalOrientationStandardDeviation;
        public float Localdepth;
        public float RemoteAgeMicroseconds;
        public float RemoteRange;
        public float RemoteAzimuth;
        public float RemoteElevation;

        public float[] RemoteRawPosition;
        public float[] RemoteCorrectedPosition;
        public float[] RemoteNED;
        public double[] RemoteGeodeticPosition;

        public float RemoteRangeStandardDeviation;
        public float RemoteAzimuthStandardDeviation;
        public float RemoteElevationStandardDeviation;

        public float[] RemotePositionStandardDeviation;

        public float RemoteDepth;

        public int SignalLevel;
        public int SignalToNoise;
        public int SignalCorrelation;
        public int SignalCorrelationInterference;

        public ANPacket24()
        {
            RemoteAddress = 0;
            DataConnectionActive = false;
            DepthCorrectionApplied = false;

            LocalTimeValid = false;
            LocalPositionValid = false;
            LocalVelocityValid = false;
            LocalOrientationValid = false;
            LocalPositionStandardDeviationValid = false;
            LocalOrientationStandardDeviationValid = false;
            LocalDepthValid = false;
            RemoteAgeValid = false;
            RemoteRangeValid = false;
            RemoteAzimuthValid = false;
            RemoteElevationValid = false;
            RemoteRawXYZValid = false;
            RemoteRollPitchCorrectedXYZValid = false;
            RemoteNEDValid = false;
            RemoteGeodeticPositionValid = false;
            RemoteRangeStandardDeviationValid = false;
            RemoteAzimuthStandardDeviationValid = false;
            RemoteElevationStandardDeviationValid = false;
            RemotePositionStandardDeviationValid = false;
            RemoteDepthValid = false;
            SignalLevelValid = false;
            SignalNoiseRatioValid = false;
            SignalCorrelationRatioValid = false;
            SignalCorrelationInterferenceValid = false;

            LocalUnixTimeStamp = 0;
            LocalMicroseconds = 0;
            LocalTime = new DateTime(0);

            LocalPosition = new double[3];
            LocalPosition[0] = 0;
            LocalPosition[1] = 0;
            LocalPosition[2] = 0;

            LocalVelocity = new float[3];
            LocalVelocity[0] = 0;
            LocalVelocity[1] = 0;
            LocalVelocity[2] = 0;

            LocalOrientation = new float[3];
            LocalOrientation[0] = 0;
            LocalOrientation[1] = 0;
            LocalOrientation[2] = 0;

            LocalPositionStandardDeviation = new float[3];
            LocalPositionStandardDeviation[0] = (float)1000000.0;
            LocalPositionStandardDeviation[1] = (float)1000000.0;
            LocalPositionStandardDeviation[2] = (float)1000000.0;

            LocalOrientationStandardDeviation = new float[3];
            LocalOrientationStandardDeviation[0] = (float)1000000.0;
            LocalOrientationStandardDeviation[1] = (float)1000000.0;
            LocalOrientationStandardDeviation[2] = (float)1000000.0;
            Localdepth = 0;
            RemoteAgeMicroseconds = 0;
            RemoteRange = 0;
            RemoteAzimuth = 0;
            RemoteElevation = 0;

            RemoteRawPosition = new float[3];
            RemoteRawPosition[0] = 0;
            RemoteRawPosition[1] = 0;
            RemoteRawPosition[2] = 0;

            RemoteCorrectedPosition = new float[3];
            RemoteCorrectedPosition[0] = 0;
            RemoteCorrectedPosition[1] = 0;
            RemoteCorrectedPosition[2] = 0;

            RemoteNED = new float[3];
            RemoteNED[0] = 0;
            RemoteNED[1] = 0;
            RemoteNED[2] = 0;

            RemoteGeodeticPosition = new double[3];
            RemoteGeodeticPosition[0] = 0;
            RemoteGeodeticPosition[1] = 0;
            RemoteGeodeticPosition[2] = 0;

            RemoteRangeStandardDeviation = 0;
            RemoteAzimuthStandardDeviation = 0;
            RemoteElevationStandardDeviation = 0;

            RemotePositionStandardDeviation = new float[3];
            RemotePositionStandardDeviation[0] = (float)1000000.0;
            RemotePositionStandardDeviation[1] = (float)1000000.0;
            RemotePositionStandardDeviation[2] = (float)1000000.0;

            RemoteDepth = 0;

            SignalLevel = 0;
            SignalToNoise = 0;
            SignalCorrelation = 0;
            SignalCorrelationInterference = 0;
        }

        public ANPacket24(ANPacket packet)
        {
            RemoteAddress = BitConverter.ToUInt16(packet.data, 0);

            if ((packet.data[2] & 0x01) != 0) DataConnectionActive = true;
            if ((packet.data[2] & 0x02) != 0) DepthCorrectionApplied = true;

            // System Status Flags
            int offset = 3;
            if (packet.data[offset + 0] != 0)
            {
                statusAlert = true;
                if ((packet.data[offset + 0] & 0x01) != 0) systemFailure = true;
                if ((packet.data[offset + 0] & 0x02) != 0) accelerometerSensorFailure = true;
                if ((packet.data[offset + 0] & 0x04) != 0) gyroscopeSensorFailure = true;
                if ((packet.data[offset + 0] & 0x08) != 0) magnetometerSensorFailure = true;
                if ((packet.data[offset + 0] & 0x10) != 0) pressureSensorFailure = true;
                if ((packet.data[offset + 0] & 0x20) != 0) gnssFailure = true;
                if ((packet.data[offset + 0] & 0x40) != 0) accelerometerOverRange = true;
                if ((packet.data[offset + 0] & 0x80) != 0) gyroscopeOverRange = true;
            }
            if (packet.data[offset + 1] != 0)
            {
                statusAlert = true;
                if ((packet.data[offset + 1] & 0x01) != 0) magnetometerOverRange = true;
                if ((packet.data[offset + 1] & 0x02) != 0) pressureOverRange = true;
                if ((packet.data[offset + 1] & 0x04) != 0) maximumTemperatureAlarm = true;
                if ((packet.data[offset + 1] & 0x08) != 0) OutOfWaterAlarm = true;
                if ((packet.data[offset + 1] & 0x10) != 0) PressureDepthOffsetAlarm = true;
                if ((packet.data[offset + 1] & 0x20) != 0) LoggingMemoryFailure = true;
                if ((packet.data[offset + 1] & 0x40) != 0) FilterAidingCommunicationFailure = true;
                if ((packet.data[offset + 1] & 0x80) != 0) FixedPositionWithGNSSAidingAlarm = true;
            }
            if (packet.data[offset + 2] != 0)
            {
                statusAlert = true;
                if ((packet.data[offset + 2] & 0x01) != 0) FixedPositionWithAcousticAidingAlarm = true;
                if ((packet.data[offset + 2] & 0x02) != 0) MultipleAidingSourceAlarm = true;
                if ((packet.data[offset + 2] & 0x04) != 0) AidedExternalINSAlarm = true;
                if ((packet.data[offset + 2] & 0x08) != 0) AidingGNSSTimeSyncAlarm = true;
                if ((packet.data[offset + 2] & 0x10) != 0) MultipleMasterAlarm = true;
                if ((packet.data[offset + 2] & 0x20) != 0) ModemDataDisabledAlarm = true;
                if ((packet.data[offset + 2] & 0x40) != 0) BrownoutAlarm = true;
            }

            if ((packet.data[offset + 4] & 0x01) != 0) OrientationFilterInitialised = true;
            if ((packet.data[offset + 4] & 0x02) != 0) insFilterInitialised = true;
            if ((packet.data[offset + 4] & 0x04) != 0) HeadingInitialised = true;
            if ((packet.data[offset + 4] & 0x08) != 0) utcTimeValid = true;
            gnssFixType = (packet.data[offset + 4] & 0x70) >> 4;
            if (gnssFixType > GNSS_NO_FIX) gnssFix = true;
            if ((packet.data[offset + 4] & 0x80) != 0) AcousticHeadingActive = true;

            if ((packet.data[offset + 5] & 0x01) != 0) AcousticPositionActive = true;
            if ((packet.data[offset + 5] & 0x02) != 0) internalGnssActive = true;
            if ((packet.data[offset + 5] & 0x04) != 0) magnetometersActive = true;
            if ((packet.data[offset + 5] & 0x08) != 0) velocityHeadingActive = true;
            if ((packet.data[offset + 5] & 0x10) != 0) PressureDepthPositionActive = true;
            if ((packet.data[offset + 5] & 0x20) != 0) externalPositionActive = true;
            if ((packet.data[offset + 5] & 0x40) != 0) externalVelocityActive = true;
            if ((packet.data[offset + 5] & 0x80) != 0) externalHeadingActive = true;

            if ((packet.data[offset + 6] & 0x01) != 0) DualAntennaHeadingActive = true;
            if ((packet.data[offset + 6] & 0x02) != 0) FixedPositionActive = true;
            if ((packet.data[offset + 6] & 0x04) != 0) FixedHeadingActive = true;
            if ((packet.data[offset + 6] & 0x08) != 0) FixedRollPitchActive = true;
            if ((packet.data[offset + 6] & 0x10) != 0) PressureDepthVelocityActive = true;
            if ((packet.data[offset + 6] & 0x20) != 0) ExternalGNSSEnabled = true;
            if ((packet.data[offset + 6] & 0x40) != 0) PressureDepthInitialised = true;
            if ((packet.data[offset + 6] & 0x80) != 0) InternalVOSInitialised = true;

            if ((packet.data[offset + 7] & 0x01) != 0) USBLTrackingActive = true;

            // Data Valid Flags Offset	
            offset = 11;
            if ((packet.data[offset] & 0x01) != 0) LocalTimeValid = true;
            if ((packet.data[offset] & 0x02) != 0) LocalPositionValid = true;
            if ((packet.data[offset] & 0x04) != 0) LocalVelocityValid = true;
            if ((packet.data[offset] & 0x08) != 0) LocalOrientationValid = true;
            if ((packet.data[offset] & 0x10) != 0) LocalPositionStandardDeviationValid = true;
            if ((packet.data[offset] & 0x20) != 0) LocalOrientationStandardDeviationValid = true;
            if ((packet.data[offset] & 0x40) != 0) LocalDepthValid = true;
            if ((packet.data[offset] & 0x80) != 0) RemoteAgeValid = true;

            if ((packet.data[offset + 1] & 0x01) != 0) RemoteRangeValid = true;
            if ((packet.data[offset + 1] & 0x02) != 0) RemoteAzimuthValid = true;
            if ((packet.data[offset + 1] & 0x04) != 0) RemoteElevationValid = true;
            if ((packet.data[offset + 1] & 0x08) != 0) RemoteRawXYZValid = true;
            if ((packet.data[offset + 1] & 0x10) != 0) RemoteRollPitchCorrectedXYZValid = true;
            if ((packet.data[offset + 1] & 0x20) != 0) RemoteNEDValid = true;
            if ((packet.data[offset + 1] & 0x40) != 0) RemoteGeodeticPositionValid = true;
            if ((packet.data[offset + 1] & 0x80) != 0) RemoteRangeStandardDeviationValid = true;

            if ((packet.data[offset + 2] & 0x01) != 0) RemoteAzimuthStandardDeviationValid = true;
            if ((packet.data[offset + 2] & 0x02) != 0) RemoteElevationStandardDeviationValid = true;
            if ((packet.data[offset + 2] & 0x04) != 0) RemotePositionStandardDeviationValid = true;
            if ((packet.data[offset + 2] & 0x08) != 0) RemoteDepthValid = true;
            if ((packet.data[offset + 2] & 0x10) != 0) SignalLevelValid = true;
            if ((packet.data[offset + 2] & 0x20) != 0) SignalNoiseRatioValid = true;
            if ((packet.data[offset + 2] & 0x40) != 0) SignalCorrelationRatioValid = true;
            if ((packet.data[offset + 2] & 0x80) != 0) SignalCorrelationInterferenceValid = true;

            LocalUnixTimeStamp = BitConverter.ToUInt32(packet.data, 15);
            LocalMicroseconds = BitConverter.ToUInt32(packet.data, 19);
            LocalTime = new DateTime(LocalUnixTimeStamp * 10000000 + LocalMicroseconds * 10);

            LocalPosition = new double[3];
            LocalPosition[0] = BitConverter.ToDouble(packet.data, 23);
            LocalPosition[1] = BitConverter.ToDouble(packet.data, 31);
            LocalPosition[2] = BitConverter.ToDouble(packet.data, 39);

            LocalVelocity = new float[3];
            LocalVelocity[0] = BitConverter.ToSingle(packet.data, 47);
            LocalVelocity[1] = BitConverter.ToSingle(packet.data, 51);
            LocalVelocity[2] = BitConverter.ToSingle(packet.data, 55);

            LocalOrientation = new float[3];
            LocalOrientation[0] = BitConverter.ToSingle(packet.data, 59);
            LocalOrientation[1] = BitConverter.ToSingle(packet.data, 63);
            LocalOrientation[2] = BitConverter.ToSingle(packet.data, 67);

            LocalPositionStandardDeviation = new float[3];
            LocalPositionStandardDeviation[0] = BitConverter.ToSingle(packet.data, 71);
            LocalPositionStandardDeviation[1] = BitConverter.ToSingle(packet.data, 75);
            LocalPositionStandardDeviation[2] = BitConverter.ToSingle(packet.data, 79);

            LocalOrientationStandardDeviation = new float[3];
            LocalOrientationStandardDeviation[0] = BitConverter.ToSingle(packet.data, 83);
            LocalOrientationStandardDeviation[1] = BitConverter.ToSingle(packet.data, 87);
            LocalOrientationStandardDeviation[2] = BitConverter.ToSingle(packet.data, 91);

            Localdepth = BitConverter.ToSingle(packet.data, 95);
            RemoteAgeMicroseconds = BitConverter.ToUInt32(packet.data, 99);
            RemoteRange = BitConverter.ToSingle(packet.data, 103);
            RemoteAzimuth = BitConverter.ToSingle(packet.data, 107);
            RemoteElevation = BitConverter.ToSingle(packet.data, 111);

            RemoteRawPosition = new float[3];
            RemoteRawPosition[0] = BitConverter.ToSingle(packet.data, 115);
            RemoteRawPosition[1] = BitConverter.ToSingle(packet.data, 119);
            RemoteRawPosition[2] = BitConverter.ToSingle(packet.data, 123);

            RemoteCorrectedPosition = new float[3];
            RemoteCorrectedPosition[0] = BitConverter.ToSingle(packet.data, 127);
            RemoteCorrectedPosition[1] = BitConverter.ToSingle(packet.data, 131);
            RemoteCorrectedPosition[2] = BitConverter.ToSingle(packet.data, 135);

            RemoteNED = new float[3];
            RemoteNED[0] = BitConverter.ToSingle(packet.data, 139);
            RemoteNED[1] = BitConverter.ToSingle(packet.data, 143);
            RemoteNED[2] = BitConverter.ToSingle(packet.data, 147);

            RemoteGeodeticPosition = new double[3];
            RemoteGeodeticPosition[0] = BitConverter.ToDouble(packet.data, 151);
            RemoteGeodeticPosition[1] = BitConverter.ToDouble(packet.data, 159);
            RemoteGeodeticPosition[2] = BitConverter.ToDouble(packet.data, 167);

            RemoteRangeStandardDeviation = BitConverter.ToSingle(packet.data, 175);
            RemoteAzimuthStandardDeviation = BitConverter.ToSingle(packet.data, 179);
            RemoteElevationStandardDeviation = BitConverter.ToSingle(packet.data, 183);

            RemotePositionStandardDeviation = new float[3];
            RemotePositionStandardDeviation[0] = BitConverter.ToSingle(packet.data, 187);
            RemotePositionStandardDeviation[1] = BitConverter.ToSingle(packet.data, 191);
            RemotePositionStandardDeviation[2] = BitConverter.ToSingle(packet.data, 195);

            RemoteDepth = BitConverter.ToSingle(packet.data, 199);

            SignalLevel = (int)packet.data[203];
            SignalToNoise = (int)packet.data[204];
            SignalCorrelation = (int)(packet.data[205] & 0xFF);
            SignalCorrelationInterference = (int)(packet.data[206] & 0xFF);
        }
    }*/
}
