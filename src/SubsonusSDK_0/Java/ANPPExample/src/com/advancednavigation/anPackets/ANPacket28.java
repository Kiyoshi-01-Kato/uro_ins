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

public class ANPacket28
{
	public float[] accelerometers;
	public float[] gyroscopes;
	public float[] magnetometers;
	public float internalTemperature;
	public float pressureDepth;
	public float waterTemperature;
	public float velocityOfSound;

	public ANPacket28()
	{
		accelerometers = new float[3];
		accelerometers[0] = 0;
		accelerometers[1] = 0;
		accelerometers[2] = 0;
		gyroscopes = new float[3];
		gyroscopes[0] = 0;
		gyroscopes[1] = 0;
		gyroscopes[2] = 0;
		magnetometers = new float[3];
		magnetometers[0] = 0;
		magnetometers[1] = 0;
		magnetometers[2] = 0;
		internalTemperature = 0;
		pressureDepth = 0;
		waterTemperature = 0;
		velocityOfSound = 0;
	}

	public ANPacket28(ANPacket packet)
	{
		accelerometers = new float[3];
		accelerometers[0] = TypeConversion.bytesToFloat(packet.data, 0);
		accelerometers[1] = TypeConversion.bytesToFloat(packet.data, 4);
		accelerometers[2] = TypeConversion.bytesToFloat(packet.data, 8);
		gyroscopes = new float[3];
		gyroscopes[0] = TypeConversion.bytesToFloat(packet.data, 12);
		gyroscopes[1] = TypeConversion.bytesToFloat(packet.data, 16);
		gyroscopes[2] = TypeConversion.bytesToFloat(packet.data, 20);
		magnetometers = new float[3];
		magnetometers[0] = TypeConversion.bytesToFloat(packet.data, 24);
		magnetometers[1] = TypeConversion.bytesToFloat(packet.data, 28);
		magnetometers[2] = TypeConversion.bytesToFloat(packet.data, 32);
		internalTemperature = TypeConversion.bytesToFloat(packet.data, 36);
		pressureDepth = TypeConversion.bytesToFloat(packet.data, 40);
		waterTemperature = TypeConversion.bytesToFloat(packet.data, 44);
		velocityOfSound = TypeConversion.bytesToFloat(packet.data, 48);
	}
	
	public String getCSV_Header(){
		return 
			"Accelerometer X,Accelerometer Y,Accelerometer Z,"
			+ "Gyroscope X,Gyroscope Y,Gyroscope Z,"
			+ "Magnetometer X,Magnetometer Y,Magnetometer Z,"
			+ "Intrernal Temperature,Pressure Depth,Water Temperature,Velocity Of Sound";		
	}
	
	public String getCSV_Data(){
		return 
			  accelerometers[0] + ","
			+ accelerometers[1] + ","
			+ accelerometers[2] + ","
			+ gyroscopes[0] + ","
			+ gyroscopes[1] + ","
			+ gyroscopes[2] + ","
			+ magnetometers[0] + ","
			+ magnetometers[1] + ","
			+ magnetometers[2] + ","
			+ internalTemperature + ","
			+ pressureDepth + ","
			+ waterTemperature + ","
			+ velocityOfSound;
	}
	
}
