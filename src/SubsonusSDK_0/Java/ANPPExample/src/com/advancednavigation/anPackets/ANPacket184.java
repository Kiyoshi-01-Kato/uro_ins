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

/* Sensor ranges */
public class ANPacket184
{	
	public int accelerometersRange;
	public int magnetometersRange;
	
	public static final int ACCELEROMETER_RANGE_2G = 0;
	public static final int ACCELEROMETER_RANGE_4G = 1;
	public static final int ACCELEROMETER_RANGE_16G = 2;
	public static final int ACCELEROMETER_RANGE_MAXIMUM = 2;
		
	public static final int MAGNETOMETER_RANGE_2_GAUSS = 0;
	public static final int MAGNETOMETER_RANGE_4_GAUSS = 1;
	public static final int MAGNETOMETER_RANGE_8_GAUSS = 2;
	public static final int MAGNETOMETER_RANGE_MAXIMUM = 2;
	
	public ANPacket184()
	{

	}

	public ANPacket184(ANPacket packet)
	{
		accelerometersRange = packet.data[1]&0xFF;
		magnetometersRange = packet.data[3]&0xFF;
	}
	
	public ANPacket toANPacket() throws IndexOutOfBoundsException
	{	
		ANPacket packet = new ANPacket(4, ANPacket.PACKET_ID_SENSOR_RANGES);
		packet.data[0] = 0;
		if (accelerometersRange < 0 || accelerometersRange > ACCELEROMETER_RANGE_MAXIMUM) throw new IndexOutOfBoundsException();
		packet.data[1] = (byte) (accelerometersRange & 0xFF);
		packet.data[2] = 0;
		if (magnetometersRange < 0 || magnetometersRange > MAGNETOMETER_RANGE_MAXIMUM  ) throw new IndexOutOfBoundsException();
		packet.data[3] = (byte) (magnetometersRange & 0xFF);
		return packet;
	}
	
	public String accelerometersRangeText()
	{
		switch(accelerometersRange)
		{
			case ACCELEROMETER_RANGE_4G:
				return "4 g";
			case ACCELEROMETER_RANGE_16G:
				return "16 g";
			case ACCELEROMETER_RANGE_2G:
			default:
				return "2 g";
		}
	}
		
	public String magnetometersRangeText()
	{
		switch(magnetometersRange)
		{
			case MAGNETOMETER_RANGE_4_GAUSS:
				return "4 Gauss";
			case MAGNETOMETER_RANGE_8_GAUSS:
				return "8 Gauss";
			case MAGNETOMETER_RANGE_2_GAUSS:
			default:
				return "2 Gauss";
		}
	}
	
	public String getTextOutput(){
		
		String ret = "";		
		ret += "--------------------------------- MODEM CONFIGURATION -------------------------" + "\r\n";	
		ret += "Accelerometers range = " + accelerometersRangeText() + "\r\n";
		ret += "Magnetometers range = " + magnetometersRangeText() + "\r\n";
		ret += "--------------------------------- MODEM CONFIGURATION -------------------------" + "\r\n\r\n";
		
		
		return ret;
	}
}
