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

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Locale;

/*  Fixed Position Packet */
public class ANPacket187
{
	public int enableFixedPosition = 0;
	public Boolean useInternalRollPitch = false;
	public double fixedLatitude = 0;
	public double fixedLongitude = 0;
	public double fixedHeight = 0;
	public float fixedRoll = 0;
	public float fixedPitch = 0;
	public float fixedHeading = 0;
	
	
	public ANPacket187()
	{
		
	}
	
	public ANPacket187 (ANPacket packet)
	{
		enableFixedPosition = (int)(packet.data[0] & 0xFF);
		if ((packet.data[1] & 0x01) != 0) useInternalRollPitch = true;
		fixedLatitude = TypeConversion.bytesToDouble(packet.data,2);
		fixedLongitude = TypeConversion.bytesToDouble(packet.data,10);
		fixedHeight = TypeConversion.bytesToDouble(packet.data,18);
		fixedRoll = TypeConversion.bytesToFloat(packet.data,26);
		fixedPitch = TypeConversion.bytesToFloat(packet.data,30);
		fixedHeading = TypeConversion.bytesToFloat(packet.data,34);	
	}
		
	public String getTextOutput(){
		
		DecimalFormat dp = new DecimalFormat();
		dp.setMaximumFractionDigits(12);
		DecimalFormat sp = new DecimalFormat();
		sp.setMaximumFractionDigits(4);
		
		
		return 
			  "--------------------------------- FIXED POSITION ------------------------------" + "\r\n"
			+ "Enable Fixed Position Mode = " + (enableFixedPosition == 0 ? "Internal INS" : "Fixed Position Enabled") + "\r\n"
			+ "Use Internal Roll and Pitch = " + (useInternalRollPitch ? "true" : "false")  + "\r\n"
			+ "Fixed Latitude = " + dp.format(fixedLatitude* 180.0 / Math.PI)  + " degrees\r\n"
			+ "Fixed Longitude = " +  dp.format(fixedLongitude* 180.0 / Math.PI) + " degrees\r\n"
			+ "Fixed Height = " + sp.format(fixedHeight) + " meters\r\n"
			+ "Fixed Roll = " + sp.format(fixedRoll* 180.0 / Math.PI) + " Degrees\r\n"	
			+ "Fixed Pitch = " + sp.format(fixedPitch* 180.0 / Math.PI) + " Degrees\r\n"	
			+ "Fixed Heading = " + sp.format(fixedHeading* 180.0 / Math.PI) + " Degrees\r\n"			
			+ "--------------------------------- FIXED POSITION ------------------------------" + "\r\n\r\n";
	}
}

