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

/*  Time Config Packet */
public class ANPacket193
{
	public int timeSource = 0;
	public Boolean servePTP = false;
	public Boolean serveNTP = false;
	public Boolean updateExternalINS = false;
	public String NTPServer1 = "";
	public String NTPServer2 = "";
	public String NTPServer3 = "";
	
	public static final int PTP = 0;	
	public static final int NTP = 1;
	public static final int EXTERNAL_INS = 2;
	public static final int ACOUSTIC = 3;
	public static final int NONE = 4;
	
	public ANPacket193()
	{
		
	}
	
	public ANPacket193 (ANPacket packet)
	{
		timeSource = (int)(packet.data[0] & 0xFF);
		if ((packet.data[1] & 0x01) != 0) servePTP = true;
		if ((packet.data[1] & 0x02) != 0) serveNTP = true;
		if ((packet.data[1] & 0x04) != 0) updateExternalINS = true;
				
		byte[] ntp1 = new byte[64];
		System.arraycopy (packet.data, 2, ntp1, 0,64);
		NTPServer1 =  new String(ntp1).trim();
		
		byte[] ntp2 = new byte[64];
		System.arraycopy (packet.data, 66, ntp2, 0,64);
		NTPServer2 =  new String(ntp2).trim();
		
		byte[] ntp3 = new byte[64];
		System.arraycopy (packet.data, 130, ntp3, 0,64);
		NTPServer3 =  new String(ntp3).trim();
		
	}
	
	public String getTimeSource(int index)
	{
		switch(index)
		{
			case PTP:
				return "PTP";
			case NTP:
				return "NTP";
			case EXTERNAL_INS:
				return "External INS";
			case ACOUSTIC:
				return "Acoustic";
			case NONE:
				return "None";			
			default:
				return "Error!";
		}
	}
		
	public String getTextOutput(){
				
		return 
			  "--------------------------------- TIME CONFIGURATION --------------------------" + "\r\n"
			+ "Time Source = " + getTimeSource(timeSource) + "\r\n"
			+ "Serve PTP = " + (servePTP ? "true" : "false")  + "\r\n"
			+ "Serve NTP = " + (serveNTP ? "true" : "false")  + "\r\n"
			+ "Update External INS = " +  (updateExternalINS ? "true" : "false")  + "\r\n"
			+ "NTP Server 1 = " + NTPServer1 + "\r\n"
			+ "NTP Server 2 = " + NTPServer2 + "\r\n"	
			+ "NTP Server 3 = " + NTPServer3 + "\r\n"			
			+ "--------------------------------- TIME CONFIGURATION --------------------------"  + "\r\n\r\n";
	}
}

