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

/* Remote State Packet */
public class ANPacket25
{
	public int deviceAddress;
	public ANPacket23 statusValid;
	public ANPacket23 status;
	
	public Boolean timeStampValid;
	public Boolean microsecondsValid;
	public Boolean[] positionValid;
	public Boolean[] velocityValid;
	public Boolean[] bodyAccelerationValid;
	public Boolean gForceValid;
	public Boolean[] orientationValid;
	public Boolean[] angularVelocityValid;
	public Boolean[] positionStandardDeviationValid;
	public Boolean[] orientationStandardDeviationValid;	

	public long unixTimeStamp;
	public long microseconds;
	public Date time;

	public double[] position;
	public float[] velocity;
	public float[] acceleration;
	public float gForce;
	public float[] orientation;
	public float[] angularVelocity;
	public float[] positionStandardDeviation;
	public float[] orientationStandardDeviation;

	public ANPacket25()
	{	
		deviceAddress = 0;
		statusValid = new ANPacket23();		
		status = new ANPacket23();
		
		timeStampValid = false;
		microsecondsValid = false;
		positionValid = new Boolean[3];
		positionValid[0] = false;
		positionValid[1] = false;
		positionValid[2] = false;
		velocityValid = new Boolean[3];
		velocityValid[0] = false;
		velocityValid[1] = false;
		velocityValid[2] = false;
		bodyAccelerationValid = new Boolean[3];
		bodyAccelerationValid[0] = false;
		bodyAccelerationValid[1] = false;
		bodyAccelerationValid[2] = false;
		gForceValid = false;
		orientationValid = new Boolean[3];
		orientationValid[0] = false;
		orientationValid[1] = false;
		orientationValid[2] = false;
		angularVelocityValid = new Boolean[3];
		angularVelocityValid[0] = false;
		angularVelocityValid[1] = false;
		angularVelocityValid[2] = false;
		positionStandardDeviationValid = new Boolean[3];
		positionStandardDeviationValid[0] = false;
		positionStandardDeviationValid[1] = false;
		positionStandardDeviationValid[2] = false;
		orientationStandardDeviationValid = new Boolean[3];
		orientationStandardDeviationValid[0] = false;
		orientationStandardDeviationValid[1] = false;
		orientationStandardDeviationValid[2] = false;
		
		unixTimeStamp = 0;
		microseconds = 0;
		time = new Date(0);
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
		positionStandardDeviation[0] = (float) 1000000.0;
		positionStandardDeviation[1] = (float) 1000000.0;
		positionStandardDeviation[2] = (float) 1000000.0;
		orientationStandardDeviation = new float[3];
		orientationStandardDeviation[0] = (float) 1000000.0;
		orientationStandardDeviation[1] = (float) 1000000.0;
		orientationStandardDeviation[2] = (float) 1000000.0;
	}

	public ANPacket25(ANPacket packet)
	{
		deviceAddress = TypeConversion.bytesToUInt16(packet.data, 0);
		
		statusValid = new ANPacket23(packet.data, 2);
		status = new ANPacket23(packet.data, 10);

		timeStampValid = false;
		microsecondsValid = false;
		positionValid = new Boolean[3];
		positionValid[0] = false;
		positionValid[1] = false;
		positionValid[2] = false;
		velocityValid = new Boolean[3];
		velocityValid[0] = false;
		velocityValid[1] = false;
		velocityValid[2] = false;
		bodyAccelerationValid = new Boolean[3];
		bodyAccelerationValid[0] = false;
		bodyAccelerationValid[1] = false;
		bodyAccelerationValid[2] = false;
		gForceValid = false;
		orientationValid = new Boolean[3];
		orientationValid[0] = false;
		orientationValid[1] = false;
		orientationValid[2] = false;
		angularVelocityValid = new Boolean[3];
		angularVelocityValid[0] = false;
		angularVelocityValid[1] = false;
		angularVelocityValid[2] = false;
		positionStandardDeviationValid = new Boolean[3];
		positionStandardDeviationValid[0] = false;
		positionStandardDeviationValid[1] = false;
		positionStandardDeviationValid[2] = false;
		orientationStandardDeviationValid = new Boolean[3];
		orientationStandardDeviationValid[0] = false;
		orientationStandardDeviationValid[1] = false;
		orientationStandardDeviationValid[2] = false;
		
		int offset = 18; // Data Valid Flags Offset		
		if ((packet.data[offset] & 0x01) != 0) timeStampValid = true;
		if ((packet.data[offset] & 0x02) != 0) microsecondsValid = true;
		if ((packet.data[offset] & 0x04) != 0) positionValid[0] = true;
		if ((packet.data[offset] & 0x08) != 0) positionValid[1] = true;
		if ((packet.data[offset] & 0x10) != 0) positionValid[2] = true;
		if ((packet.data[offset] & 0x20) != 0) velocityValid[0] = true;
		if ((packet.data[offset] & 0x40) != 0) velocityValid[1] = true;
		if ((packet.data[offset] & 0x80) != 0) velocityValid[2] = true;

		if ((packet.data[offset + 1] & 0x01) != 0) bodyAccelerationValid[0] = true;
		if ((packet.data[offset + 1] & 0x02) != 0) bodyAccelerationValid[1] = true;
		if ((packet.data[offset + 1] & 0x04) != 0) bodyAccelerationValid[2] = true;
		if ((packet.data[offset + 1] & 0x08) != 0) gForceValid = true;
		if ((packet.data[offset + 1] & 0x10) != 0) orientationValid[0] = true;
		if ((packet.data[offset + 1] & 0x20) != 0) orientationValid[1] = true;
		if ((packet.data[offset + 1] & 0x40) != 0) orientationValid[2] = true;
		if ((packet.data[offset + 1] & 0x80) != 0) angularVelocityValid[0] = true;

		if ((packet.data[offset + 2] & 0x01) != 0) angularVelocityValid[1] = true;
		if ((packet.data[offset + 2] & 0x02) != 0) angularVelocityValid[2] = true;
		if ((packet.data[offset + 2] & 0x04) != 0) positionStandardDeviationValid[0] = true;
		if ((packet.data[offset + 2] & 0x08) != 0) positionStandardDeviationValid[1] = true;
		if ((packet.data[offset + 2] & 0x10) != 0) positionStandardDeviationValid[2] = true;
		if ((packet.data[offset + 2] & 0x20) != 0) orientationStandardDeviationValid[0] = true;
		if ((packet.data[offset + 2] & 0x40) != 0) orientationStandardDeviationValid[1] = true;
		if ((packet.data[offset + 2] & 0x80) != 0) orientationStandardDeviationValid[2] = true;

		unixTimeStamp = TypeConversion.bytesToUInt32(packet.data, 22);
		microseconds = TypeConversion.bytesToUInt32(packet.data, 26);
		time = new Date(unixTimeStamp * 1000 + microseconds / 1000);

		position = new double[3];
		position[0] = TypeConversion.bytesToDouble(packet.data, 30);
		position[1] = TypeConversion.bytesToDouble(packet.data, 38);
		position[2] = TypeConversion.bytesToDouble(packet.data, 46);

		velocity = new float[3];
		velocity[0] = TypeConversion.bytesToFloat(packet.data, 54);
		velocity[1] = TypeConversion.bytesToFloat(packet.data, 58);
		velocity[2] = TypeConversion.bytesToFloat(packet.data, 62);

		acceleration = new float[3];
		acceleration[0] = TypeConversion.bytesToFloat(packet.data, 66);
		acceleration[1] = TypeConversion.bytesToFloat(packet.data, 70);
		acceleration[2] = TypeConversion.bytesToFloat(packet.data, 74);
		gForce = TypeConversion.bytesToFloat(packet.data, 78);

		orientation = new float[3];
		orientation[0] = TypeConversion.bytesToFloat(packet.data, 82);
		orientation[1] = TypeConversion.bytesToFloat(packet.data, 86);
		orientation[2] = TypeConversion.bytesToFloat(packet.data, 90);
		if (orientation[2] < 0) orientation[2] += 2 * Math.PI;

		angularVelocity = new float[3];
		angularVelocity[0] = TypeConversion.bytesToFloat(packet.data, 94);
		angularVelocity[1] = TypeConversion.bytesToFloat(packet.data, 98);
		angularVelocity[2] = TypeConversion.bytesToFloat(packet.data, 102);

		positionStandardDeviation = new float[3];
		positionStandardDeviation[0] = TypeConversion.bytesToFloat(packet.data, 106);
		positionStandardDeviation[1] = TypeConversion.bytesToFloat(packet.data, 110);
		positionStandardDeviation[2] = TypeConversion.bytesToFloat(packet.data, 114);
		
		orientationStandardDeviation = new float[3];
		orientationStandardDeviation[0] = TypeConversion.bytesToFloat(packet.data, 118);
		orientationStandardDeviation[1] = TypeConversion.bytesToFloat(packet.data, 122);
		orientationStandardDeviation[2] = TypeConversion.bytesToFloat(packet.data, 126);
	}
		
	public String getCSV_Header(){
		
		return "Human Timestamp,"
			+ "Unix Time,"
			+ "Microseconds,"
			+ "Device Address,"
			// System Status
			+ statusValid.getCSV_ValidHeader() + ","
			+ status.getCSV_Header() + ","
			// Data Valid Flags
			+ "Unitx Time Valid,"
			+ "Microseconds Valid,"
			+ "Latitude Valid,"
			+ "Longitude Valid,"
			+ "Height Valid,"
			+ "Velocity North Valid,"
			+ "Velocity East Valid,"
			+ "Velocity Down Valid,"
			+ "Acceleration X Valid,"
			+ "Acceleration Y Valid,"
			+ "Acceleration Z Valid,"
			+ "G Force Valid,"
			+ "Roll Valid,"
			+ "Pitch Valid,"
			+ "Heading Valid,"
			+ "Angular Velocity X Valid,"
			+ "Angular Velocity Y Valid,"
			+ "Angular Velocity Z Valid,"
			+ "Latitude Error Valid,"
			+ "Longitude Error Valid,"
			+ "Height Error Valid,"
			+ "Roll Error Valid,"
			+ "Pitch Error Valid,"
			+ "Heading Error Valid,"
			// Data
			+ "Latitude,"
			+ "Longitude,"
			+ "Height,"
			+ "Velocity North,"
			+ "Velocity East,"
			+ "Velocity Down,"
			+ "Acceleration X,"
			+ "Acceleration Y,"
			+ "Acceleration Z,"
			+ "G Force,"
			+ "Roll,"
			+ "Pitch,"
			+ "Heading,"
			+ "Angular Velocity X,"
			+ "Angular Velocity Y,"
			+ "Angular Velocity Z,"
			+ "Latitude Error,"
			+ "Longitude Error,"
			+ "Height Error,"
			+ "Roll Error,"
			+ "Pitch Error,"
			+ "Heading Error";
	}
	
	public String getCSV_Data(){
		return 
			  time.toString() + "," 
			+ unixTimeStamp + "," 
			  + microseconds + "," 
			+ deviceAddress + ","
			 //System Status
		    + statusValid.getCSV_Data() + ","
			+ status.getCSV_Data() + ","
			// Data Valid Flags
			+ (timeStampValid ? 1 : 0 ) + ","
			+ (microsecondsValid ? 1 : 0 ) + ","
			+ (positionValid[0] ? 1 : 0 ) + ","
			+ (positionValid[1] ? 1 : 0 ) + ","
			+ (positionValid[2] ? 1 : 0 ) + ","
			+ (velocityValid[0] ? 1 : 0 ) + ","
			+ (velocityValid[1] ? 1 : 0 ) + ","
			+ (velocityValid[2] ? 1 : 0 ) + ","		
			+ (bodyAccelerationValid[0] ? 1 : 0 ) + ","
			+ (bodyAccelerationValid[1] ? 1 : 0 ) + ","
			+ (bodyAccelerationValid[2] ? 1 : 0 ) + ","
			+ (gForceValid ? 1 : 0 ) + ","
			+ (orientationValid[0] ? 1 : 0 ) + ","
			+ (orientationValid[1] ? 1 : 0 ) + ","
			+ (orientationValid[2] ? 1 : 0 ) + ","
			+ (angularVelocityValid[0] ? 1 : 0 ) + ","
			+ (angularVelocityValid[1] ? 1 : 0 ) + ","
			+ (angularVelocityValid[2] ? 1 : 0 ) + ","
			+ (positionStandardDeviationValid[0] ? 1 : 0 ) + ","
			+ (positionStandardDeviationValid[1] ? 1 : 0 ) + ","
			+ (positionStandardDeviationValid[2] ? 1 : 0 ) + ","
			+ (orientationStandardDeviationValid[0] ? 1 : 0 ) + ","
			+ (orientationStandardDeviationValid[1] ? 1 : 0 ) + ","
			+ (orientationStandardDeviationValid[2] ? 1 : 0 ) + ","
			// Data
			+ position[0] * 180 / Math.PI + "," + position[1] * 180 / Math.PI + "," + position[2] + ","
			+ velocity[0] + "," + velocity[1] + "," + velocity[2] + ","
			+ acceleration[0] + "," + acceleration[1] + "," + acceleration[2] + "," + gForce + ","
			+ orientation[0] * 180 / Math.PI + "," + orientation[1] * 180 / Math.PI + "," + orientation[2] * 180 / Math.PI + ","
			+ angularVelocity[0] * 180 / Math.PI + "," + angularVelocity[1] * 180 / Math.PI + "," + angularVelocity[2] * 180 / Math.PI + ","
			+ positionStandardDeviation[0] + "," + positionStandardDeviation[1] + "," + positionStandardDeviation[2] + ","
			+ orientationStandardDeviation[0] * 180 / Math.PI + "," 
			+ orientationStandardDeviation[1] * 180 / Math.PI + "," 
			+ orientationStandardDeviation[2] * 180 / Math.PI
			+ "";				
	}
}
