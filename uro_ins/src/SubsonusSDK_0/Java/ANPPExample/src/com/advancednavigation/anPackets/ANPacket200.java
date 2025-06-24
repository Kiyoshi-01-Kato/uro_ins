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

/*  Modem Configuration Packet */
public class ANPacket200
{		
	public static final int LATITUDE = 1;
	public static final int LONGITUDE = 2;
	public static final int HEIGHT = 3;
	public static final int ROLL = 4;
	public static final int PITCH = 5;
	public static final int HEADING = 6;
	public static final int DEPTH = 7;
	public static final int VOS = 8;
	public static final int SYSTEMSTATUS = 9;
	public static final int FILTERSTATUS = 10;
	public static final int MODEMDATA = 11;
	public static final int TIME = 12;
	
	public static final int UNKNOWN = -1;
	public static final int OFF = 0;
	public static final int LOW = 1;
	public static final int MEDIUM = 2;
	public static final int HIGH = 3;
	
	public int LatitudePriority = -1;
	public int LongitudePriority = -1;
	public int HeightPriority = -1;
	public int RollPriority = -1;
	public int PitchPriority = -1;
	public int HeadingPriority = -1;
	public int DepthPriority = -1;
	public int VOSPriority = -1;
	public int SystemStatusPriority = -1;
	public int FilterStatusPriority = -1;
	public int ModemDataPriority = -1;
	public int TimePriority = -1;
					
	public ANPacket200()
	{
			
	}
	
	public ANPacket200 (ANPacket packet)
	{		
		int settings = (packet.length - 1)/2;
		int offset = 1;
		
		for(int i=0;i<settings;i++) {
			int index = (int)(packet.data[offset] & 0xFF); offset += 1;
			int value = (int)(packet.data[offset] & 0xFF); offset += 1;
			updatePriority(index, value);
		}			
				
	}
	
	public void updatePriority(int index, int value) {
		switch(index)
		{
			case LATITUDE:
				LatitudePriority = value;
				break;
			case LONGITUDE:
				LongitudePriority = value;
				break;
			case HEIGHT:
				HeightPriority = value;
				break;
			case ROLL:
				RollPriority = value;
				break;
			case PITCH:
				PitchPriority = value;
				break;
			case HEADING:
				HeadingPriority = value;
				break;
			case DEPTH:
				DepthPriority = value;
				break;
			case VOS:
				VOSPriority = value;
				break;
			case SYSTEMSTATUS:
				SystemStatusPriority = value;
				break;
			case FILTERSTATUS:
				FilterStatusPriority = value;
				break;
			case MODEMDATA:
				ModemDataPriority = value;
				break;
			case TIME:
				TimePriority = value;
				break;
			default:
				break;
				
		}
	}
	
	public String getPriorityString(int index) {
		switch(index)
		{
			case OFF:
				return "Off";
			case LOW:
				return "Low";
			case MEDIUM:
				return "Medium";
			case HIGH:
				return "High";			
			default:
				return "Unknown";
		}
	}
	
	
	public String getTextOutput(){
				
		String ret = "";		
		ret += "--------------------------------- ACOUSTIC DATA PRIORITY CONFIGURATION -------------------------" + "\r\n";	
		ret += "Latitude Priority = " + getPriorityString(LatitudePriority) + "\r\n";
		ret += "Longitude Priority = " + getPriorityString(LongitudePriority) + "\r\n";
		ret += "Height Priority = " + getPriorityString(HeightPriority) + "\r\n";
		ret += "Roll Priority = " + getPriorityString(RollPriority) + "\r\n";
		ret += "Pitch Priority = " + getPriorityString(PitchPriority) + "\r\n";
		ret += "Heading Priority = " + getPriorityString(HeadingPriority) + "\r\n";
		ret += "Depth Priority = " + getPriorityString(DepthPriority) + "\r\n";
		ret += "Velocity Of Sound Priority = " + getPriorityString(VOSPriority) + "\r\n";
		ret += "System Status Priority = " + getPriorityString(SystemStatusPriority) + "\r\n";
		ret += "Filter Status Priority = " + getPriorityString(FilterStatusPriority) + "\r\n";
		ret += "Modem Data Priority = " + getPriorityString(ModemDataPriority) + "\r\n";
		ret += "Time Priority = " + getPriorityString(TimePriority) + "\r\n";
		
		ret += "--------------------------------- ACOUSTIC DATA PRIORITY CONFIGURATION -------------------------" + "\r\n\r\n";
		
		
		return ret;
	}
}

