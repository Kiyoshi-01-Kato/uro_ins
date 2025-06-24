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

/*  Packet Periods Packet */
public class ANPacket195
{
	public int NMEAFixBehavior = 0;
	public Boolean clearExisting = false;	
	public int portID[];
	public int packetID[];
	public int packetRate[];
	

	public static final int NORMAL = 0;
	public static final int ALWAYS_3D_FIX = 1;
	
	public static final int DISABLED = 0;
	public static final int HZ01 = 1;
	public static final int HZ02 = 2;
	public static final int HZ05 = 3;
	public static final int HZ1 = 4;
	public static final int HZ2 = 5;
	public static final int HZ5 = 6;
	public static final int HZ10 = 7;
	public static final int HZ25 = 8;
	public static final int HZ50 = 9;
	
	public static final int NO_MESSAGE = 0;
	public static final int GPZDA = 1;
	public static final int GPGGA = 2;
	public static final int GPVTG = 3;
	public static final int GPRMC = 4;
	public static final int GPHDT = 5;
	public static final int GPGGL = 6;
	public static final int PASHR = 7;
	public static final int TSS1 = 8;
	public static final int SIMRAD = 9;
	public static final int GPGGA_REMOTE = 10;
	public static final int GPHTD_REMOTE = 11;	
	public static final int GPDBS = 12;
	public static final int GPDBS_REMOTE = 13;
	public static final int PASHR_REMOTE = 14;
	
	public int settings = 0;
		
	public ANPacket195(int settings_in)
	{
		if(settings_in > 0 && settings_in < 84) {
			settings = settings_in;
		}
		else {
			settings = 0;
		}		
		portID = new int[settings];
		packetID = new int[settings];
		packetRate = new int[settings];
		
	}
	
	public ANPacket195 (ANPacket packet)
	{
		NMEAFixBehavior = (int)(packet.data[0] & 0xFF);
		clearExisting = ((packet.data[1] & 0xFF) > 0 ? true : false);
		
		settings = (packet.length - 2)/3;
		
		if(settings > 0 && settings < 84) {
			
			portID = new int[settings];
			packetID = new int[settings];
			packetRate = new int[settings];
			
			int offset = 2;
			for(int i=0;i<settings;i++) {
				portID[i] = (int)(packet.data[offset] & 0xFF); offset += 1;
				packetID[i] = (int)(packet.data[offset] & 0xFF); offset += 1;
				packetRate[i] = (int)(packet.data[offset] & 0xFF); offset += 1;
			}			
		}
		
	}
	
	public String RateString(int index)
	{
		switch(index)
		{
			case HZ01:
				return "0.1 Hz";
			case HZ02:
				return "0.2 Hz";
			case HZ05:
				return "0.5 Hz";
			case HZ1:
				return "1.0 Hz";
			case HZ2:
				return "2.0 Hz";
			case HZ5:
				return "3.0 Hz";
			case HZ10:
				return "10.0 Hz";
			case HZ25:
				return "25.0 Hz";
			case HZ50:
				return "50.0 Hz";
			default:
				return "Disabled";
		}
	}	
	
	public String getPacketName(int index)
	{
		switch(index)
		{
			case NO_MESSAGE:
				return "No Message";
			case GPZDA:
				return "GPZDA";
			case GPGGA:
				return "GPGGA";
			case GPVTG:
				return "GPVTG";
			case GPRMC:
				return "GPRMC";
			case GPHDT:
				return "GPHDT";
			case GPGGL:
				return "GPGGL";
			case PASHR:
				return "PASHR";
			case SIMRAD:
				return "Simrad";
			case GPGGA_REMOTE:
				return "GPGGA (Remote)";
			case GPHTD_REMOTE:
				return "GPHDT (Remote)";
			case GPDBS:
				return "GPDBS";
			case GPDBS_REMOTE:
				return "GPDBS (Remote)";
			case PASHR_REMOTE:
				return "PASHR (Remote)";
			default:
				return "Unknown";
		}
	}
	
	private boolean is_aperiodic(int index) {
		
		switch(index)
		{
			case NO_MESSAGE:
			case GPGGA_REMOTE:
			case GPHTD_REMOTE:	
			case GPDBS_REMOTE:
			case PASHR_REMOTE:
				return true;						
			default:
				return false;
		}
		
	}
	
	public String getTextOutput(){
		
		String ret = "";
		
		if(settings > 0) {
			ret += "--------------------------------- NMEA OUTPUT CONFIGURATION -------------------" + "\r\n";
		
			ret += "NMEA Fix Behavior = " + (NMEAFixBehavior == 0 ? "Normal" : "Always 3D Fix") + "\r\n";
			
			for(int i=0;i<settings;i++) {
				ret += "Port ID = " +  portID[i] + ",Packet Name = " + getPacketName(packetID[i]) + ((is_aperiodic(packetID[i])) ? ", Packet Rate  = N/A" : ", Packet Rate  = " + RateString(packetRate[i])) + "\r\n";
			}
			
			ret += "--------------------------------- NMEA OUTPUT CONFIGURATION -------------------" + "\r\n\r\n";
		}
		
		return ret;
	}
}

