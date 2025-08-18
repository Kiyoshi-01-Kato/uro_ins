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

import java.util.Date;

public class ANPacket24
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
	public Date LocalTime;
	public double[] LocalPosition;
	public float[] LocalVelocity;
	public float[] LocalOrientation;
	public float[]  LocalPositionStandardDeviation;
	public float[]  LocalOrientationStandardDeviation;
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
		LocalTime= new Date(0);
		
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
		LocalPositionStandardDeviation[0] = (float) 1000000.0;
		LocalPositionStandardDeviation[1] = (float) 1000000.0;
		LocalPositionStandardDeviation[2] = (float) 1000000.0;
		
		LocalOrientationStandardDeviation = new float[3];
		LocalOrientationStandardDeviation[0] = (float) 1000000.0;
		LocalOrientationStandardDeviation[1] = (float) 1000000.0;
		LocalOrientationStandardDeviation[2] = (float) 1000000.0;
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
		RemotePositionStandardDeviation[0] = (float) 1000000.0;
		RemotePositionStandardDeviation[1] = (float) 1000000.0;
		RemotePositionStandardDeviation[2] = (float) 1000000.0;

		RemoteDepth = 0;
		
		SignalLevel = 0;
		SignalToNoise = 0;
		SignalCorrelation = 0;
		SignalCorrelationInterference = 0;		
	}

	public ANPacket24(ANPacket packet)
	{
		RemoteAddress = (int)TypeConversion.bytesToUInt16(packet.data, 0);
		
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
		
		LocalUnixTimeStamp = TypeConversion.bytesToUInt32(packet.data, 15);
		LocalMicroseconds = TypeConversion.bytesToUInt32(packet.data, 19);
		LocalTime = new Date(LocalUnixTimeStamp * 1000 + LocalMicroseconds / 1000);
		
		LocalPosition = new double[3];
		LocalPosition[0] = TypeConversion.bytesToDouble(packet.data, 23);
		LocalPosition[1] = TypeConversion.bytesToDouble(packet.data, 31);
		LocalPosition[2] = TypeConversion.bytesToDouble(packet.data, 39);

		LocalVelocity = new float[3];
		LocalVelocity[0] = TypeConversion.bytesToFloat(packet.data, 47);
		LocalVelocity[1] = TypeConversion.bytesToFloat(packet.data, 51);
		LocalVelocity[2] = TypeConversion.bytesToFloat(packet.data, 55);

		LocalOrientation = new float[3];
		LocalOrientation[0] = TypeConversion.bytesToFloat(packet.data, 59);
		LocalOrientation[1] = TypeConversion.bytesToFloat(packet.data, 63);
		LocalOrientation[2] = TypeConversion.bytesToFloat(packet.data, 67);
		
		LocalPositionStandardDeviation = new float[3];
		LocalPositionStandardDeviation[0] = TypeConversion.bytesToFloat(packet.data, 71);
		LocalPositionStandardDeviation[1] = TypeConversion.bytesToFloat(packet.data, 75);
		LocalPositionStandardDeviation[2] = TypeConversion.bytesToFloat(packet.data, 79);
		
		LocalOrientationStandardDeviation = new float[3];
		LocalOrientationStandardDeviation[0] = TypeConversion.bytesToFloat(packet.data, 83);
		LocalOrientationStandardDeviation[1] = TypeConversion.bytesToFloat(packet.data, 87);
		LocalOrientationStandardDeviation[2] = TypeConversion.bytesToFloat(packet.data, 91);
		
		Localdepth = TypeConversion.bytesToFloat(packet.data, 95);
		RemoteAgeMicroseconds = TypeConversion.bytesToUInt32(packet.data, 99);
		RemoteRange = TypeConversion.bytesToFloat(packet.data, 103);
		RemoteAzimuth = TypeConversion.bytesToFloat(packet.data, 107);
		RemoteElevation = TypeConversion.bytesToFloat(packet.data, 111);

		RemoteRawPosition = new float[3];
		RemoteRawPosition[0] = TypeConversion.bytesToFloat(packet.data, 115);
		RemoteRawPosition[1] = TypeConversion.bytesToFloat(packet.data, 119);
		RemoteRawPosition[2] = TypeConversion.bytesToFloat(packet.data, 123);

		RemoteCorrectedPosition = new float[3];
		RemoteCorrectedPosition[0] = TypeConversion.bytesToFloat(packet.data, 127);
		RemoteCorrectedPosition[1] = TypeConversion.bytesToFloat(packet.data, 131);
		RemoteCorrectedPosition[2] = TypeConversion.bytesToFloat(packet.data, 135);

		RemoteNED = new float[3];
		RemoteNED[0] = TypeConversion.bytesToFloat(packet.data, 139);
		RemoteNED[1] = TypeConversion.bytesToFloat(packet.data, 143);
		RemoteNED[2] = TypeConversion.bytesToFloat(packet.data, 147);

		RemoteGeodeticPosition = new double[3];
		RemoteGeodeticPosition[0] = TypeConversion.bytesToDouble(packet.data, 151);
		RemoteGeodeticPosition[1] = TypeConversion.bytesToDouble(packet.data, 159);
		RemoteGeodeticPosition[2] = TypeConversion.bytesToDouble(packet.data, 167);
		
		RemoteRangeStandardDeviation = TypeConversion.bytesToFloat(packet.data, 175);
		RemoteAzimuthStandardDeviation = TypeConversion.bytesToFloat(packet.data, 179);
		RemoteElevationStandardDeviation = TypeConversion.bytesToFloat(packet.data, 183);
		
		RemotePositionStandardDeviation = new float[3];
		RemotePositionStandardDeviation[0] = TypeConversion.bytesToFloat(packet.data, 187);
		RemotePositionStandardDeviation[1] = TypeConversion.bytesToFloat(packet.data, 191);
		RemotePositionStandardDeviation[2] = TypeConversion.bytesToFloat(packet.data, 195);

		RemoteDepth = TypeConversion.bytesToFloat(packet.data, 199);
		
		SignalLevel = (int)(packet.data[203] & 0xFF);
		SignalToNoise = (int)packet.data[204];
		SignalCorrelation = (int)(packet.data[205] & 0xFF);
		SignalCorrelationInterference = (int)(packet.data[206] & 0xFF);		
	}
	
	public String getCSV_Header(){
			
		return "Human Timestamp,Unix Time,Microseconds,"
			// Tracking Status
			+ "Remote Address,Data Connection Active,Depth Correction Applied,"
			// Local Status
			+ status.getCSV_Header() + ","
			// Data Valid Flags
			+ "Local Time Valid,"
			+ "Local Position Valid,"
			+ "Local Velocity Valid,"
			+ "Local Orientation Valid,"
			+ "Local Position Standard Deviation Valid,"
			+ "Local Orientation Standard Deviation Valid,"
			+ "Local Depth Valid,"
			+ "Remote Age Valid,"
			+ "Remote Range Valid,"
			+ "Remote Azimuth Valid,"
			+ "Remote Elevation Valid,"
			+ "Remote Raw XYZ Valid,"
			+ "Remote Roll Pitch Corrected XYZ Valid,"
			+ "Remote NED Valid,"
			+ "Remote Geodetic Position Valid,"
			+ "Remote Range Standard Deviation Valid,"
			+ "Remote Azimuth Standard Deviation Valid,"
			+ "Remote Elevation Standard Deviation Valid,"
			+ "Remote Position Standard Deviation Valid,"
			+ "Remote Depth Valid,"
			+ "Signal Level Valid,"
			+ "Signal Noise Ratio Valid,"
			+ "Signal Correlation Ratio Valid,"
			+ "Signal Correlation Interference Valid,"
			// Local State
			+ "Local Latitude,"
			+ "Local Longitude,"
			+ "Local Height,"
			+ "Local Velocity North,"
			+ "Local Velocity East,"
			+ "Local Velocity Down,"
			+ "Local Roll,"
			+ "Local Pitch,"
			+ "Local Heading,"
			+ "Local Latitude Error,"
			+ "Local Longitude Error,"
			+ "Local Height Error,"
			+ "Local Roll Error,"
			+ "Local Pitch Error,"
			+ "Local Heading Error,"
			+ "Local Depth,"
			// Remote Calculated
			+ "Remote Age Microseconds,"
			+ "Remote Range,"
			+ "Remote Azimuth,"
			+ "Remote Elevation,"
			+ "Remote Position Raw X,"
			+ "Remote Position Raw Y,"
			+ "Remote Position Raw Z,"
			+ "Remote Position Corrected X,"
			+ "Remote Position Corrected Y,"
			+ "Remote Position Corrected Z,"
			+ "Remote North,"
			+ "Remote East,"
			+ "Remote Down,"
			+ "Remote Latitude,"
			+ "Remote Longitude,"
			+ "Remote Height,"
			+ "Remote Range Standard Deviation,"
			+ "Remote Azimuth Standard Deviation,"
			+ "Remote Elevation Standard Deviation,"
			+ "Remote Latitude Standard Deviation,"
			+ "Remote Longitude Standard Deviation,"
			+ "Remote Height standard deviation,"
			+ "Remote Depth,"
			// Signal Analysis
			+ "Signal Level,Signal To Noise Ratio,Signal Correlation Ratio,Signal Correlation Interference";
	}
	
	public String getCSV_Data(){
		return 
			LocalTime.toString() + "," + LocalUnixTimeStamp + "," + LocalMicroseconds + ","
			// Tracking Status
			+ RemoteAddress + "," 
			+ (DataConnectionActive ? 1 : 0)  + "," 
			+ (DepthCorrectionApplied ? 1 : 0)  + ","
			// Local Status
			+ status.getCSV_Data() + ","
			// Data Valid Flags
			+ (LocalTimeValid ? 1 : 0) + "," 
			+ (LocalPositionValid ? 1 : 0) + "," 
			+ (LocalVelocityValid ? 1 : 0) + ","
			+ (LocalOrientationValid ? 1 : 0) + "," 
			+ (LocalPositionStandardDeviationValid ? 1 : 0) + "," 
			+ (LocalOrientationStandardDeviationValid ? 1 : 0) + "," 
			+ (LocalDepthValid ? 1 : 0) + "," 
			+ (RemoteAgeValid ? 1 : 0) + "," 
			+ (RemoteRangeValid ? 1 : 0) + "," 
			+ (RemoteAzimuthValid ? 1 : 0) + "," 
			+ (RemoteElevationValid ? 1 : 0) + "," 
			+ (RemoteRawXYZValid ? 1 : 0) + ","
			+ (RemoteRollPitchCorrectedXYZValid ? 1 : 0) + ","
			+ (RemoteNEDValid ? 1 : 0) + ","
			+ (RemoteGeodeticPositionValid ? 1 : 0) + "," 
			+ (RemoteRangeStandardDeviationValid ? 1 : 0) + "," 
			+ (RemoteAzimuthStandardDeviationValid ? 1 : 0) + "," 
			+ (RemoteElevationStandardDeviationValid ? 1 : 0) + "," 
			+ (RemotePositionStandardDeviationValid ? 1 : 0) + "," 
			+ (RemoteDepthValid ? 1 : 0) + "," 
			+ (SignalLevelValid ? 1 : 0) + "," 
			+ (SignalNoiseRatioValid ? 1 : 0) + "," 
			+ (SignalCorrelationRatioValid ? 1 : 0) + "," 
			+ (SignalCorrelationInterferenceValid ? 1 : 0) + ","
			// Local State
			+ LocalPosition[0] * 180 / Math.PI + ","
			+ LocalPosition[1] * 180 / Math.PI + ","
			+ LocalPosition[2] + ","
			+ LocalVelocity[0] + ","
			+ LocalVelocity[1] + ","
			+ LocalVelocity[2] + ","
			+ LocalOrientation[0] * 180 / Math.PI + ","
			+ LocalOrientation[1] * 180 / Math.PI + ","
			+ LocalOrientation[2] * 180 / Math.PI + ","
			+ LocalPositionStandardDeviation[0] + ","
			+ LocalPositionStandardDeviation[1] + ","
			+ LocalPositionStandardDeviation[2] + ","
			+ LocalOrientationStandardDeviation[0] * 180 / Math.PI + ","
			+ LocalOrientationStandardDeviation[1] * 180 / Math.PI + ","
			+ LocalOrientationStandardDeviation[2] * 180 / Math.PI + ","
			+ Localdepth + ","
			// Remote Calculated
			+ RemoteAgeMicroseconds + ","
			+ RemoteRange + ","
			+ RemoteAzimuth * 180 / Math.PI + ","
			+ RemoteElevation * 180 / Math.PI + ","
			+ RemoteRawPosition[0] + ","
			+ RemoteRawPosition[1] + ","
			+ RemoteRawPosition[2] + ","
			+ RemoteCorrectedPosition[0] + ","
			+ RemoteCorrectedPosition[1] + ","
			+ RemoteCorrectedPosition[2] + ","
			+ RemoteNED[0] + ","
			+ RemoteNED[1] + ","
			+ RemoteNED[2] + ","
			+ RemoteGeodeticPosition[0] * 180 / Math.PI + ","
			+ RemoteGeodeticPosition[1] * 180 / Math.PI + ","
			+ RemoteGeodeticPosition[2] + ","
			+ RemoteRangeStandardDeviation + ","
			+ RemoteAzimuthStandardDeviation * 180 / Math.PI + ","
			+ RemoteElevationStandardDeviation * 180 / Math.PI + ","
			+ RemotePositionStandardDeviation[0] + ","
			+ RemotePositionStandardDeviation[1] + ","
			+ RemotePositionStandardDeviation[2] + ","
			+ RemoteDepth + ","
			// Signal Analysis
			+ SignalLevel + ","
			+ SignalToNoise + ","
			+ SignalCorrelation + ","
			+ SignalCorrelationInterference;
	}
}
