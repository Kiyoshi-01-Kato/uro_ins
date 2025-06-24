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

public class ANPacket29
{

	public int deviceAddress;

	public Boolean pressureDepthValid;
	public Boolean waterTemperatureValid;
	public Boolean velocityOfSoundValid;
	public Boolean internalTemperatureValid;
	
	
	public float pressureDepth;
	public float waterTemperature;
	public float velocityOfSound;
	public float internalTemperature;
	
	public ANPacket29()
	{
		deviceAddress = 0;

		pressureDepthValid = false;
		waterTemperatureValid = false;
		velocityOfSoundValid = false;
		internalTemperatureValid = false;		
		
		pressureDepth = 0;
		waterTemperature = 0;
		velocityOfSound = 0;
		internalTemperature = 0;
	}

	public ANPacket29(ANPacket packet)
	{
		deviceAddress = TypeConversion.bytesToUInt16(packet.data, 0);
		
		pressureDepthValid = false;
		waterTemperatureValid = false;
		velocityOfSoundValid = false;
		internalTemperatureValid = false;
		
		int offset = 2; // Data Valid Flags Offset		
		if ((packet.data[offset] & 0x01) != 0) pressureDepthValid = true;
		if ((packet.data[offset] & 0x02) != 0) waterTemperatureValid = true;
		if ((packet.data[offset] & 0x04) != 0) velocityOfSoundValid = true;
		if ((packet.data[offset] & 0x08) != 0) internalTemperatureValid = true;
		
		pressureDepth = TypeConversion.bytesToFloat(packet.data, 4);
		waterTemperature = TypeConversion.bytesToFloat(packet.data, 8);
		velocityOfSound = TypeConversion.bytesToFloat(packet.data, 12);
		internalTemperature = TypeConversion.bytesToFloat(packet.data, 16);		
		
	}
		
	public String getCSV_Header(){
		
		return "Device Address,"
			// Data Valid Flags
			+ "Pressure Depth Valid,Water Temperature Valid,Velocity Of Sound Valid,Internal Temperature Valid,"
			// Data
			+ "Pressure Depth,Water Temperature,Velocity Of Sound,Internal Temperature";
	}
	
	public String getCSV_Data(){
		return 
			deviceAddress + ","
			// Data Valid Flags
			+ (pressureDepthValid ? 1 : 0) + ","
			+ (waterTemperatureValid ? 1 : 0) + ","
			+ (velocityOfSoundValid ? 1 : 0) + ","
			+ (internalTemperatureValid ? 1 : 0) + ","
			// Data
			+ pressureDepth + ","
			+ waterTemperature + ","
			+ velocityOfSound + ","
			+ internalTemperature;
	}
	
}
