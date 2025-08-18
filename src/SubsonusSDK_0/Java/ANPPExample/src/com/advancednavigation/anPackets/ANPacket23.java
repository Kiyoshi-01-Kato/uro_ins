/****************************************************************/
/*                                                              */
/*      Advanced Navigation Packet Protocol Library             */
/*            Java Language SDK, Version 2.4                    */
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


package com.advancednavigation.anPackets;

public class ANPacket23
{
	public static final int GNSS_NO_FIX = 0;
	public static final int GNSS_2D_FIX = 1;
	public static final int GNSS_3D_FIX = 2;
	public static final int GNSS_SBAS_FIX = 3;
	public static final int GNSS_DIFFERENTIAL_FIX = 4;
	public static final int GNSS_OMNISTAR_FIX = 5;
	public static final int GNSS_RTK_FLOAT_FIX = 6;
	public static final int GNSS_RTK_FIXED_FIX = 7;

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
	public Boolean PressureDepthVelocityActive = false;
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
		gnssFixType = (data[offset + 4] & 0x70) >>> 4;
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
	
	public String gnssFixString()
	{
		switch(gnssFixType)
		{
			case GNSS_2D_FIX:
				return "2D Fix";
			case GNSS_3D_FIX:
				return "3D Fix";
			case GNSS_SBAS_FIX:
				return "SBAS Fix";
			case GNSS_DIFFERENTIAL_FIX:
				return "Differential Fix";
			case GNSS_OMNISTAR_FIX:
				return "Omnistar Fix";
			case GNSS_RTK_FLOAT_FIX:
				return "RTK Float Fix";
			case GNSS_RTK_FIXED_FIX:
				return "RTK Fixed Fix";
			default:
				return "No Fix";
		}
	}
	
	public String getCSV_Header(){
		
		return
			// System Status
			"System Failure,Accelerometer Failure,Gyroscope Failure,Magnetometer Failure,Pressure Failure,"
			+ "GNSS Failure,Accelerometer Overrange,Gyroscope Overrange,Magnetometer Overrange,Pressure Overrange,"
			+ "Maximum Temperature Shutdown,Out Of Water Alarm,Pressure Depth Offset Alarm,Logging Memory Failure,Filter Aiding Communication Failure,"
			+ "Fixed Position with GNSS Aiding,Fixed Position with Acoustic Aiding,Multiple Aiding Sources,Aided External INS,Aiding GNSS Time Sync,"
			+ "Multiple Master,Modem Data Disabled,Brownout,Aiding Alignment Alarm,"
			// Filter Status
			+ "Orientation Ready,Navigation Ready,Heading Ready,Time Ready,"
			+ "GNSS Fix Type,Acoustic Heading Active,Acoustic Position Active,Internal GNSS Enabled,Magnetic Heading Enabled,"
			+ "Velocity Heading Enabled,Pressure Depth Position Enabled,External Position Active,External Velocity Active,External Heading Active,"
			+ "Dual Antenna Heading Active,Fixed Position Active,Fixed Heading Active,Fixed Roll/Pitch Active,Pressure Depth Velocity Active,External GNSS Enabled,"
			+ "Pressure Depth Initialised,Internal VOS Initialised,USBL Tracking Active";
	}
	
	public String getCSV_ValidHeader(){
		
		return
			// System Status
			"System Failure Valid,Accelerometer Failure Valid,Gyroscope Failure Valid,Magnetometer Failure Valid,Pressure Failure Valid,"
			+ "GNSS Failure Valid,Accelerometer Overrange Valid,Gyroscope Overrange Valid,Magnetometer Overrange Valid,Pressure Overrange Valid,"
			+ "Maximum Temperature Shutdown Valid,Out Of Water Alarm Valid,Pressure Depth Offset Alarm Valid,Logging Memory Failure Valid,Filter Aiding Communication Failure Valid,"
			+ "Fixed Position with GNSS Aiding Valid,Fixed Position with Acoustic Aiding Valid,Multiple Aiding Sources Valid,Aided External INS Valid,Aiding GNSS Time Sync Valid,"
			+ "Multiple Master Valid,Modem Data Disabled Valid,Brownout Valid,Aiding Alignment Alarm Valid,"
			// Filter Status
			+ "Orientation Ready Valid,Navigation Ready Valid,Heading Ready Valid,Time Ready Valid,"
			+ "GNSS Fix Type Valid,Acoustic Heading Active Valid,Acoustic Position Active Valid,Internal GNSS Enabled Valid,Magnetic Heading Enabled Valid,"
			+ "Velocity Heading Enabled Valid,Pressure Depth Position Enabled Valid,External Position Active Valid,External Velocity Active Valid,External Heading Active Valid,"
			+ "Dual Antenna Heading Active Valid,Fixed Position Active Valid,Fixed Heading Active Valid,Fixed Roll/Pitch Active Valid,Pressure Depth Height Active Valid,External GNSS Enabled Valid,"
			+ "Pressure Depth Initialised Valid,Internal VOS Initialised Valid,USBL Tracking Active Valid";
	}
		
	public String getCSV_Data(){
		return (systemFailure ? 1 : 0) + "," + (accelerometerSensorFailure ? 1 : 0) + "," + (gyroscopeSensorFailure ? 1 : 0) + "," + (magnetometerSensorFailure ? 1 : 0) + "," + (pressureSensorFailure ? 1 : 0) + ","
			+ (gnssFailure ? 1 : 0) + "," + (accelerometerOverRange ? 1 : 0) + "," + (gyroscopeOverRange ? 1 : 0) + "," + (magnetometerOverRange ? 1 : 0) + "," + (pressureOverRange ? 1 : 0) + ","
			+ (maximumTemperatureAlarm ? 1 : 0) + "," + (OutOfWaterAlarm ? 1 : 0) + "," + (PressureDepthOffsetAlarm ? 1 : 0) + "," + (LoggingMemoryFailure ? 1 : 0) + "," + (FilterAidingCommunicationFailure ? 1 : 0) + ","
			+ (FixedPositionWithGNSSAidingAlarm ? 1 : 0) + "," + (FixedPositionWithAcousticAidingAlarm ? 1 : 0) + "," + (MultipleAidingSourceAlarm ? 1 : 0) + "," + (AidedExternalINSAlarm ? 1 : 0) + "," + (AidingGNSSTimeSyncAlarm ? 1 : 0) + ","
			+ (MultipleMasterAlarm ? 1 : 0) + "," + (ModemDataDisabledAlarm ? 1 : 0) + "," + (BrownoutAlarm ? 1 : 0) + "," + (AidingAlignmentAlarm ? 1 : 0) + ","
			+ (OrientationFilterInitialised ? 1 : 0) + "," + (PositionFilterInitialised ? 1 : 0) + "," + (HeadingInitialised ? 1 : 0) + "," + (utcTimeValid ? 1 : 0) + ","
			+ gnssFixType + "," + (AcousticHeadingActive ? 1 : 0) + "," + (AcousticPositionActive ? 1 : 0) + "," + (internalGnssActive ? 1 : 0) + "," + (magnetometersActive ? 1 : 0) + ","
			+ (velocityHeadingActive ? 1 : 0) + "," + (PressureDepthPositionActive ? 1 : 0) + "," + (externalPositionActive ? 1 : 0) + "," + (externalVelocityActive ? 1 : 0) + "," + (externalHeadingActive ? 1 : 0) + ","
			+ (DualAntennaHeadingActive ? 1 : 0) + "," + (FixedPositionActive ? 1 : 0) + "," + (FixedHeadingActive ? 1 : 0) + "," + (FixedRollPitchActive ? 1 : 0) + "," + (PressureDepthHeightActive ? 1 : 0) + "," + (ExternalGNSSEnabled ? 1 : 0) + ","
			+ (PressureDepthInitialised ? 1 : 0) + "," + (InternalVOSInitialised ? 1 : 0) + "," + (USBLTrackingActive ? 1 : 0);				
	}
}
