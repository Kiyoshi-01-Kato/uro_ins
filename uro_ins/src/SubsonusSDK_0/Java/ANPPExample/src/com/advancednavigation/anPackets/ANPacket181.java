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
public class ANPacket181
{
	public Boolean clearExisting;	
	public int portID[];
	public int packetID[];
	public int packetRate[];
	
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
	
	public int settings = 0;
		
	public ANPacket181(int settings_in)
	{
		if(settings_in > 0 && settings_in < 84) {
			settings = settings_in;
		}
		else {
			settings = 0;
		}
		
		clearExisting = false;
		portID = new int[settings];
		packetID = new int[settings];
		packetRate = new int[settings];
		
	}
	
	public ANPacket181 (ANPacket packet)
	{
		clearExisting = ((packet.data[0] & 0xFF) > 0 ? true : false);
		
		settings = (packet.length - 1)/3;
		
		portID = new int[settings];
		packetID = new int[settings];
		packetRate = new int[settings];
		
		if(settings > 0 && settings < 84) {
			
			int offset = 1;
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
			case 20:
				return "20 (System State Packet)";
			case 23:
				return "23 (Status Packet)";
			case 24:
				return "24 (Remote Track Packet)";
			case 25:
				return "25 (Remote System State Packet)";
			case 28:
				return "28 (Raw Sensors Packet)";
			case 29:
				return "29 (Remote Raw Sensors Packet)";
			case 66:
				return "66 (Modem Status Packets)";
			case 68:
				return "68 (Modem Data Packets)";
			case 21:
				return "21 (Unix Time Packet)";
			case 22:
				return "22 (Formatted Time Packet)";
			case 42:
				return "42 (Angular Velocity Packet)";
			case 43:
				return "43 (Angular Accelearation Packet)";
			case 210:
				return "210 (Debug Packets)";
			default:
				return "Error!";
		}
	}
	
	public boolean is_aperiodic(int index) 
	{
		switch(index)
		{		
			case 24:
			case 25:			
			case 29:
			case 210:				
				return true;
			default:
				return false;
		}
	}
	
	public String getTextOutput(){
		
		String ret = "";
		
		if(settings > 0) {
			ret += "--------------------------------- PACKET PERIODS ------------------------------" + "\r\n";
		
			for(int i=0;i<settings;i++) {
				ret += "Port ID = " +  portID[i] + ",Packet Name = " + getPacketName(packetID[i]) + ((is_aperiodic(packetID[i])) ? ", Packet Rate  = N/A" : ", Packet Rate  = " + RateString(packetRate[i])) + "\r\n";
			}
			
			ret += "--------------------------------- PACKET PERIODS ------------------------------" + "\r\n\r\n";
		}
		
		return ret;
	}
}

