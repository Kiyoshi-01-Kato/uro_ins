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
import java.util.Arrays;
import java.util.Locale;

/*  Installation Alignment Packet */
public class ANPacket185
{
	public int operatingOrientation = 0;
	public float roll = 0;
	public float pitch = 0;
	public float heading = 0;
	
	public double alignment_dcm[][];
	
	public ANPacket185()
	{
		alignment_dcm = new double[3][3];		
	}
	
	public ANPacket185 (ANPacket packet)
	{
		operatingOrientation = (int)(packet.data[37] & 0xFF);
		alignment_dcm = new double[3][3];
		alignment_dcm[0][0] = TypeConversion.bytesToFloat(packet.data,1);
		alignment_dcm[0][1] = TypeConversion.bytesToFloat(packet.data,5);
		alignment_dcm[0][2] = TypeConversion.bytesToFloat(packet.data,9);
		alignment_dcm[1][0] = TypeConversion.bytesToFloat(packet.data,13);
		alignment_dcm[1][1] = TypeConversion.bytesToFloat(packet.data,17);
		alignment_dcm[1][2] = TypeConversion.bytesToFloat(packet.data,21);
		alignment_dcm[2][0] = TypeConversion.bytesToFloat(packet.data,25);
		alignment_dcm[2][1] = TypeConversion.bytesToFloat(packet.data,29);
		alignment_dcm[2][2] = TypeConversion.bytesToFloat(packet.data,33);
		
		roll = (float) ((Math.atan2(alignment_dcm[1][2],alignment_dcm[2][2])) * 180 / Math.PI);
        pitch = (float) ((-(Math.asin(alignment_dcm[0][2]))) * 180 / Math.PI);
        heading = (float) ((Math.atan2(alignment_dcm[0][1],alignment_dcm[0][0])) * 180 / Math.PI);
	}
	
	public String getTextOutput(){
		
		return 
			  "--------------------------------- INSTALLATION ALIGNMENT ----------------------" + "\r\n"
			+ "Operating Orientation = " + (operatingOrientation==0 ? "Surface" : "Subsea") + "\r\n"
			+ "Roll Offset = " + roll + " degrees\r\n"
			+ "Pitch Offset = " + pitch + " degrees\r\n"
			+ "Heading Offset = " + heading + " degrees\r\n"
			+ "--------------------------------- INSTALLATION ALIGNMENT ----------------------" + "\r\n\r\n";
	}
}

