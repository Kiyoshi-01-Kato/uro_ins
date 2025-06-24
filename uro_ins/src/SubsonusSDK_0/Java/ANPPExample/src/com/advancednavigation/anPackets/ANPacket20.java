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

public class ANPacket20
{
	public ANPacket23 status;

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
	
	public ANPacket20()
	{
		status = new ANPacket23();
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

	public ANPacket20(ANPacket packet)
	{
		status = new ANPacket23(packet.data, 0);

		unixTimeStamp = TypeConversion.bytesToUInt32(packet.data, 8);
		microseconds = TypeConversion.bytesToUInt32(packet.data, 12);
		time = new Date(unixTimeStamp * 1000 + microseconds / 1000);

		position = new double[3];
		position[0] = TypeConversion.bytesToDouble(packet.data, 16);
		position[1] = TypeConversion.bytesToDouble(packet.data, 24);
		position[2] = TypeConversion.bytesToDouble(packet.data, 32);

		velocity = new float[3];
		velocity[0] = TypeConversion.bytesToFloat(packet.data, 40);
		velocity[1] = TypeConversion.bytesToFloat(packet.data, 44);
		velocity[2] = TypeConversion.bytesToFloat(packet.data, 48);

		acceleration = new float[3];
		acceleration[0] = TypeConversion.bytesToFloat(packet.data, 52);
		acceleration[1] = TypeConversion.bytesToFloat(packet.data, 56);
		acceleration[2] = TypeConversion.bytesToFloat(packet.data, 60);
		gForce = TypeConversion.bytesToFloat(packet.data, 64);

		orientation = new float[3];
		orientation[0] = TypeConversion.bytesToFloat(packet.data, 68);
		orientation[1] = TypeConversion.bytesToFloat(packet.data, 72);
		orientation[2] = TypeConversion.bytesToFloat(packet.data, 76);
		if (orientation[2] < 0) orientation[2] += 2 * Math.PI;

		angularVelocity = new float[3];
		angularVelocity[0] = TypeConversion.bytesToFloat(packet.data, 80);
		angularVelocity[1] = TypeConversion.bytesToFloat(packet.data, 84);
		angularVelocity[2] = TypeConversion.bytesToFloat(packet.data, 88);

		positionStandardDeviation = new float[3];
		positionStandardDeviation[0] = TypeConversion.bytesToFloat(packet.data, 92);
		positionStandardDeviation[1] = TypeConversion.bytesToFloat(packet.data, 96);
		positionStandardDeviation[2] = TypeConversion.bytesToFloat(packet.data, 100);
		
		orientationStandardDeviation = new float[3];
		orientationStandardDeviation[0] = TypeConversion.bytesToFloat(packet.data, 104);
		orientationStandardDeviation[1] = TypeConversion.bytesToFloat(packet.data, 108);
		orientationStandardDeviation[2] = TypeConversion.bytesToFloat(packet.data, 112);
		
	}	
	
	public String getCSV_Header(){
		
		return "Human Timestamp,Unix Time,Microseconds,"
			// System Status
			+ status.getCSV_Header() + ","
			
			+ "Latitude,Longitude,Height,Velocity North,Velocity East,Velocity Down,Acceleration X,Acceleration Y,Acceleration Z,"
			+ "G Force,Roll,Pitch,Heading,Angular Velocity X,Angular Velocity Y,Angular Velocity Z,Latitude Error,Longitude Error,Height Error,"
			+ "Roll Error, Pitch Error, Heading Error";
	}
	
	public String getCSV_Data(){
		return 
			  time.toString() + "," + unixTimeStamp + "," + microseconds + ","			
			+ status.getCSV_Data() + ","			
			+ position[0] * 180 / Math.PI + "," + position[1] * 180 / Math.PI + "," + position[2] + ","
			+ velocity[0] + "," + velocity[1] + "," + velocity[2] + ","
			+ acceleration[0] + "," + acceleration[1] + "," + acceleration[2] + "," + gForce + ","
			+ orientation[0] * 180 / Math.PI + "," + orientation[1] * 180 / Math.PI + "," + orientation[2] * 180 / Math.PI + ","
			+ angularVelocity[0] * 180 / Math.PI + "," + angularVelocity[1] * 180 / Math.PI + "," + angularVelocity[2] * 180 / Math.PI + ","
			+ positionStandardDeviation[0] + "," + positionStandardDeviation[1] + "," + positionStandardDeviation[2] + ","
			+ orientationStandardDeviation[0] * 180 / Math.PI + "," + orientationStandardDeviation[1] * 180 / Math.PI + "," + orientationStandardDeviation[2] * 180 / Math.PI;				
	}
	
	public String getCSV_TimeHeader(){
		return "Human Timestamp,Unix Time,Microseconds,";
	}
	
	public String getCSV_TimeData(){
		return time.toString() + "," + unixTimeStamp + "," + microseconds + ",";
	}
}
