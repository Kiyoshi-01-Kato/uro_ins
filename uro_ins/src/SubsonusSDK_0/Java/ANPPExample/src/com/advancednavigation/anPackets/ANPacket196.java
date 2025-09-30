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

/*  Device Configuration Packet */
public class ANPacket196
{
	public int deviceType = 0;
	public int deviceKey = 0;	
	public int deviceAddress = 0;	
	public String displayName = "";
	public int activeFlag = 0;

	public Boolean has_port = false;
	public int port = 0;
	public Boolean has_hostname = false;
	public String hostname = "";
	
	public Boolean has_tracked = false;
	public int tracked = 0;
	
	public Boolean has_externalINS = false;
	public int externalINS = 0;
	
	public Boolean has_enabled = false;
	public int enabled = 0;
	
	public Boolean has_operationMode = false;
	public long operationMode = 0;
	
	public Boolean has_serialNumber = false;
	public long[] serialNumber;	
	
	public Boolean has_offset = false;
	public float offsetX = 0.0f;
	public float offsetY = 0.0f;
	public float offsetZ = 0.0f;
	
	public Boolean has_position = false;
	public double latitude = 0.0f;
	public double longitude = 0.0f;
	public double height = 0.0f;

	public float transmitFrequency = 0.0f;
	public Boolean has_transmitFrequency = false;
	
	public float transmitDuration = 0.0f;
	public Boolean has_transmitDuration = false;

	public float acousticEchoTime = 0.0f;
	public Boolean has_acousticEchoTime = false;
	
	public float interrogationFrequency = 0.0f;
	public Boolean has_interrogationFrequency = false;
	
	public float interrogationDuration = 0.0f;
	public Boolean has_interrogationDuration = false;
	
	public float interrogationPeriod = 0.0f;
	public Boolean has_interrogationPeriod = false;

	public int interrogationCode = 0;
	public Boolean has_interrogationCode = false;

	public int replyCode = 0;
	public Boolean has_replyCode = false;
	
	public float replyDelay = 0.0f;
	public Boolean has_replyDelay = false;
	
	public Boolean oneWayDelay = false;
	public Boolean has_oneWayDelay = false;
	
	public int wakeEnable = 0;
	public Boolean has_wakeEnable = false;
	
	public static final int HOSTNAME_SIZE = 32;
	
	public static final int NO_DEVICE = 0;
	public static final int ADVANCED_NAVIGATION_SUBSONUS = 1;
	public static final int ADVANCED_NAVIGATION_TAG = 2;
	public static final int ADVANCED_NAVIGATION_GNSS_COMPASS = 3;
	public static final int ADVANCED_NAVIGATION_INS = 4;
	public static final int FIXED_TONE_PINGER_GENERIC = 5;
	public static final int FIXED_TONE_TRANSPONDER_GENERIC = 6;
	public static final int FIXED_TONE_TRANSPONDER_BENTHOWAVE = 7;
	public static final int WAYPOINT = 8;
	public static final int FIXED_TONE_PINGER_EMERGENCY = 9;
	public static final int HEMISPHERE_GNSS_COMPASS = 10;
	public static final int WB2_TRANSPONDER = 11;
	public static final int GENERIC_NMEA_GNSS_COMPASS = 12;
	public static final int MICRON_TRANSPONDER = 13;
	public static final int ADVANCED_NAVIGATION_SPATIAL = 14;
	public static final int ADVANCED_NAVIGATION_FOG = 15;
	public static final int ADVANCED_NAVIGATION_DUAL = 16;
	public static final int ADVANCED_NAVIGATION_FOG_DUAL = 17;
	
	public static final int NEW_DEVICE = 0;
	public static final int THIS_DEVICE = 1;
	public static final int DEVICE_ADDRESS = 2;
			
	public ANPacket196(int settings_in)
	{
		serialNumber = new long[3];				
	}
	
	public ANPacket196 (ANPacket packet)
	{
		deviceType = TypeConversion.bytesToUInt16(packet.data,0);
		deviceKey = TypeConversion.bytesToUInt16(packet.data,2);
		deviceAddress = TypeConversion.bytesToUInt16(packet.data,4);
		
		{
			byte[] t = new byte[16];
			System.arraycopy (packet.data, 6, t, 0,16);
			displayName =  new String(t).trim();
		}
		
		activeFlag = (int)(packet.data[22] & 0xFF);	

		byte[] hn = new byte[HOSTNAME_SIZE];
		switch(deviceType)
		{
			case NO_DEVICE:
				break;
			case ADVANCED_NAVIGATION_SUBSONUS:
				port = TypeConversion.bytesToUInt16(packet.data,23); has_port = true;
				
				System.arraycopy (packet.data, 25, hn, 0,HOSTNAME_SIZE);
				hostname =  new String(hn).trim(); has_hostname = true;
				
				tracked = (int)(packet.data[57] & 0xFF); has_tracked = true;
				
				operationMode = TypeConversion.bytesToInt32(packet.data,58); has_operationMode = true;
				
				serialNumber = new long[3];
				serialNumber[0] = TypeConversion.bytesToUInt32(packet.data, 62);
				serialNumber[1] = TypeConversion.bytesToUInt32(packet.data, 66);
				serialNumber[2] = TypeConversion.bytesToUInt32(packet.data, 70); has_serialNumber = true;
				interrogationPeriod = TypeConversion.bytesToFloat(packet.data, 74); has_interrogationPeriod = true;		
				break;
			case ADVANCED_NAVIGATION_TAG:
				tracked = (int)(packet.data[23] & 0xFF); has_tracked = true;
				serialNumber = new long[3];
				serialNumber[0] = TypeConversion.bytesToUInt32(packet.data, 24);
				serialNumber[1] = TypeConversion.bytesToUInt32(packet.data, 28);
				serialNumber[2] = TypeConversion.bytesToUInt32(packet.data, 32); has_serialNumber = true;
				interrogationPeriod = TypeConversion.bytesToFloat(packet.data, 36); has_interrogationPeriod = true;
				break;
			case ADVANCED_NAVIGATION_GNSS_COMPASS: // Aiding Source only
				port = TypeConversion.bytesToUInt16(packet.data,23); has_port = true;
				
				System.arraycopy (packet.data, 25, hn, 0,HOSTNAME_SIZE);
				hostname =  new String(hn).trim(); has_hostname = true;
				
				serialNumber = new long[3];
				serialNumber[0] = TypeConversion.bytesToUInt32(packet.data, 57);
				serialNumber[1] = TypeConversion.bytesToUInt32(packet.data, 61);
				serialNumber[2] = TypeConversion.bytesToUInt32(packet.data, 65); has_serialNumber = true;
				
				enabled = (int)(packet.data[69] & 0xFF); has_enabled = true;
				offsetX = TypeConversion.bytesToFloat(packet.data, 70);
				offsetY = TypeConversion.bytesToFloat(packet.data, 74);
				offsetZ = TypeConversion.bytesToFloat(packet.data, 78); has_offset = true;
								
				break;
			case ADVANCED_NAVIGATION_SPATIAL: // External INS only
			case ADVANCED_NAVIGATION_FOG:
				
				port = TypeConversion.bytesToUInt16(packet.data,23); has_port = true;
				
				System.arraycopy (packet.data, 25, hn, 0,HOSTNAME_SIZE);
				hostname =  new String(hn).trim(); has_hostname = true;	
				
				serialNumber = new long[3];
				serialNumber[0] = TypeConversion.bytesToUInt32(packet.data, 57);
				serialNumber[1] = TypeConversion.bytesToUInt32(packet.data, 61);
				serialNumber[2] = TypeConversion.bytesToUInt32(packet.data, 65); has_serialNumber = true;
				
				externalINS = (int)(packet.data[69] & 0xFF); has_externalINS = true;
				offsetX = TypeConversion.bytesToFloat(packet.data, 70);
				offsetY = TypeConversion.bytesToFloat(packet.data, 74);
				offsetZ = TypeConversion.bytesToFloat(packet.data, 78); has_offset = true;
				break;
			case ADVANCED_NAVIGATION_INS: // Aiding Source and External INS
			case ADVANCED_NAVIGATION_DUAL:
			case ADVANCED_NAVIGATION_FOG_DUAL:				
				port = TypeConversion.bytesToUInt16(packet.data,23); has_port = true;
				
				System.arraycopy (packet.data, 25, hn, 0,HOSTNAME_SIZE);
				hostname =  new String(hn).trim(); has_hostname = true;	
				
				serialNumber = new long[3];
				serialNumber[0] = TypeConversion.bytesToUInt32(packet.data, 57);
				serialNumber[1] = TypeConversion.bytesToUInt32(packet.data, 61);
				serialNumber[2] = TypeConversion.bytesToUInt32(packet.data, 65); has_serialNumber = true;
				externalINS = (int)(packet.data[69] & 0xFF); has_externalINS = true;
				enabled = (int)(packet.data[70] & 0xFF); has_enabled = true;
				offsetX = TypeConversion.bytesToFloat(packet.data, 71);
				offsetY = TypeConversion.bytesToFloat(packet.data, 75);
				offsetZ = TypeConversion.bytesToFloat(packet.data, 79); has_offset = true;				
				break;
			case WAYPOINT:
				latitude = TypeConversion.bytesToDouble(packet.data, 23);
				longitude = TypeConversion.bytesToDouble(packet.data, 31);
				height = TypeConversion.bytesToDouble(packet.data, 39);
				has_position = true;				
				break;
			case FIXED_TONE_PINGER_EMERGENCY:
				tracked = (int)(packet.data[23] & 0xFF); has_tracked = true;
				break;
			case FIXED_TONE_PINGER_GENERIC:
				tracked = (int)(packet.data[23] & 0xFF); has_tracked = true;
				transmitFrequency = TypeConversion.bytesToFloat(packet.data, 24); has_transmitFrequency = true;
				transmitDuration = TypeConversion.bytesToFloat(packet.data, 28); has_transmitDuration = true;
				if(packet.length > 32) {
					acousticEchoTime = TypeConversion.bytesToFloat(packet.data, 32); has_acousticEchoTime = true;
				}
				break;
			case FIXED_TONE_TRANSPONDER_GENERIC:
				tracked = (int)(packet.data[23] & 0xFF); has_tracked = true;
				
				interrogationPeriod = TypeConversion.bytesToFloat(packet.data, 24); has_interrogationPeriod = true;
				interrogationDuration = TypeConversion.bytesToFloat(packet.data, 28); has_interrogationDuration = true;
				interrogationFrequency = TypeConversion.bytesToFloat(packet.data, 32); has_interrogationFrequency = true;
				
				transmitFrequency = TypeConversion.bytesToFloat(packet.data, 36); has_transmitFrequency = true;
				transmitDuration = TypeConversion.bytesToFloat(packet.data, 40); has_transmitDuration = true;
				replyDelay = TypeConversion.bytesToFloat(packet.data, 44); has_replyDelay = true;
				
				break;
			case FIXED_TONE_TRANSPONDER_BENTHOWAVE:
				tracked = (int)(packet.data[23] & 0xFF); has_tracked = true;
				
				interrogationPeriod = TypeConversion.bytesToFloat(packet.data, 24); has_interrogationPeriod = true;
				interrogationDuration = TypeConversion.bytesToFloat(packet.data, 28); has_interrogationDuration = true;
				interrogationFrequency = TypeConversion.bytesToFloat(packet.data, 32); has_interrogationFrequency = true;
				
				transmitFrequency = TypeConversion.bytesToFloat(packet.data, 36); has_transmitFrequency = true;
				transmitDuration = TypeConversion.bytesToFloat(packet.data, 40); has_transmitDuration = true;
				replyDelay = TypeConversion.bytesToFloat(packet.data, 44); has_replyDelay = true;
				oneWayDelay = ((packet.data[48] & 0xFF) > 0); has_oneWayDelay = true;
				
				break;
			case WB2_TRANSPONDER:
				tracked = (int)(packet.data[23] & 0xFF); has_tracked = true;
				wakeEnable = (int)(packet.data[24] & 0xFF); has_wakeEnable = true;
				replyCode = TypeConversion.bytesToUInt16(packet.data, 25); has_replyCode = true;
				interrogationCode = TypeConversion.bytesToUInt16(packet.data, 27); has_interrogationCode = true;
				interrogationPeriod = TypeConversion.bytesToFloat(packet.data, 29); has_interrogationPeriod = true;				
				replyDelay = TypeConversion.bytesToFloat(packet.data, 33); has_replyDelay = true;
				break;
			case MICRON_TRANSPONDER:
				tracked = (int)(packet.data[23] & 0xFF); has_tracked = true;
				interrogationCode = TypeConversion.bytesToUInt16(packet.data, 24); has_interrogationCode = true;
				interrogationPeriod = TypeConversion.bytesToFloat(packet.data, 26); has_interrogationPeriod = true;				
				break;	
			case GENERIC_NMEA_GNSS_COMPASS:
			case HEMISPHERE_GNSS_COMPASS:	
				port = TypeConversion.bytesToUInt16(packet.data,23); has_port = true;
				
				System.arraycopy (packet.data, 25, hn, 0,HOSTNAME_SIZE);
				hostname =  new String(hn).trim(); has_hostname = true;	
								
				enabled = (int)(packet.data[57] & 0xFF); has_enabled = true;
				offsetX = TypeConversion.bytesToFloat(packet.data, 58);
				offsetY = TypeConversion.bytesToFloat(packet.data, 62);
				offsetZ = TypeConversion.bytesToFloat(packet.data, 66); has_offset = true;
				break;
			
			
			default:
				break;
		}
				
	}
	
	public String getDeviceType(int index)
	{
		switch(index)
		{
			case NO_DEVICE:
				return "No Device";
			case ADVANCED_NAVIGATION_SUBSONUS:
				return "Advanced Navigation Subsonus";		
			case ADVANCED_NAVIGATION_TAG:
				return "Advanced Navigation Tag";	
			case ADVANCED_NAVIGATION_GNSS_COMPASS:
				return "Advanced Navigation GNSS Compass";	
			case ADVANCED_NAVIGATION_INS:
				return "Advanced Navigation INS";	
			case FIXED_TONE_PINGER_GENERIC:
				return "Fixed Tone Pinger Generic";	
			case FIXED_TONE_TRANSPONDER_GENERIC:
				return "Fixed Tone Transponder Generic";	
			case FIXED_TONE_TRANSPONDER_BENTHOWAVE:
				return "Fixed Tone Transponder Benthowave";	
			case WAYPOINT:
				return "Waypoint";	
			case FIXED_TONE_PINGER_EMERGENCY:
				return "Fixed Tone Pinger Emergency";	
			case HEMISPHERE_GNSS_COMPASS:
				return "Hemisphere GNSS Compass";	
			case WB2_TRANSPONDER:
				return "WB2 Transponder";	
			case GENERIC_NMEA_GNSS_COMPASS:
				return "Generic NMEA GNSS Compass";	
			case MICRON_TRANSPONDER:
				return "Micron Transponder";	
			case ADVANCED_NAVIGATION_SPATIAL:
				return "Advanced Navigation Spatial";	
			case ADVANCED_NAVIGATION_FOG:
				return "Advanced Navigation FOG";	
			case ADVANCED_NAVIGATION_DUAL:
				return "Advanced Navigation Dual";	
			case ADVANCED_NAVIGATION_FOG_DUAL:
				return "Advanced Navigation FOG Dual";		
			default:
				return "Error!";
		}
	}	
	
	public String getDeviceKey(int index)
	{
		switch(index)
		{
			case NEW_DEVICE:
				return "New Device (0)";
			case THIS_DEVICE:
				return "This Device (1)";		
			case DEVICE_ADDRESS:
				return "Device By Address (2)";
			default:
				return "" + index;
		}
	}	
	
	public String getOperationMode(long index)
	{
		switch((int)index)
		{
			case 0:
				return "Surface (0)";
			case 1:
				return "Subsea (1)";
			default:
				return "" + index;
		}
	}	
	
	
	public String serialNumberString()
	{
		return String.format(Locale.ENGLISH, "%08X%08X%08X", serialNumber[0], serialNumber[1], serialNumber[2]);
	}
	
	public String getTextOutput(){
		
		DecimalFormat dp = new DecimalFormat();
		dp.setMaximumFractionDigits(12);
		DecimalFormat sp = new DecimalFormat();
		sp.setMaximumFractionDigits(4);
		
		String ret = "";
		
		ret += "--------------------------------- DEVICE CONFIGURATION ------------------------" + "\r\n";	
		ret += "Device Type = " + getDeviceType(deviceType) + "\r\n";
		ret += "Device Key = " + getDeviceKey(deviceKey) + "\r\n";
		ret += "Device Address = " + deviceAddress + "\r\n";
		ret += "Display Name = " + displayName + "\r\n";
		ret += "Active = " + (activeFlag > 0 ? "true" : "false") + "\r\n";
		
		if(has_port) ret += "Port = " + port + "\r\n";
		if(has_hostname) ret += "Hostname/Address = " + hostname + "\r\n";
		if(has_tracked) ret += "Tracked = " + (tracked > 0 ? "true" : "false") + "\r\n";
		if(has_externalINS) ret += "External INS = " + (externalINS > 0 ? "true" : "false") + "\r\n";
		if(has_enabled) ret += "Enabled = " + (enabled > 0 ? "true" : "false") + "\r\n";
		if(has_operationMode) ret += "Operation Mode = " + getOperationMode(operationMode) + "\r\n";
		if(has_serialNumber) ret += "Serial Number = " + serialNumberString() + "\r\n";
		if(has_offset) ret += "Offset X = " + sp.format(offsetX)  + " meters\r\n";
		if(has_offset) ret += "Offset Y = " + sp.format(offsetY) + " meters\r\n";
		if(has_offset) ret += "Offset Z = " + sp.format(offsetZ) + " meters\r\n";		
		if(has_position) ret += "Latitude = " + dp.format(latitude* 180.0 / Math.PI) + " degrees\r\n";
		if(has_position) ret += "Longitude = " + dp.format(longitude* 180.0 / Math.PI) + " degrees\r\n";
		if(has_position) ret += "Height = " + sp.format(height) + " meters\r\n";
		if(has_transmitFrequency) ret += "Transmit Frequency = " + sp.format(transmitFrequency) + " Hz\r\n";
		if(has_transmitDuration) ret += "Transmit Duration = " + sp.format(transmitDuration) + " Seconds\r\n";
		if(has_acousticEchoTime) ret += "Acoustic Echo Time = " + sp.format(acousticEchoTime) + " Seconds\r\n";
		if(has_interrogationFrequency) ret += "Interrogation Frequency = " + sp.format(interrogationFrequency) + " Hz\r\n";
		if(has_interrogationDuration) ret += "Interrogation Duration = " + sp.format(interrogationDuration) + " Seconds\r\n";
		if(has_interrogationPeriod) ret += "Interrogation Period = " + (interrogationPeriod > 0 ? sp.format(interrogationPeriod) + " Seconds" : "Fastest" ) + "\r\n";
		if(has_interrogationCode) ret += "Interrogation Code = " + interrogationCode + "\r\n";
		if(has_replyCode) ret += "Reply Code = " + replyCode + "\r\n";
		if(has_replyDelay) ret += "Reply Delay = " + replyDelay + " Seconds\r\n";
		if(has_oneWayDelay) ret += "One Way Delay = " + (oneWayDelay ? "true" : "false") + "\r\n";
		if(has_wakeEnable) ret += "Wake Enable = " + (wakeEnable > 0 ? "true" : "false") + "\r\n";
				
		ret += "--------------------------------- DEVICE CONFIGURATION ------------------------" + "\r\n\r\n";
		
		
		return ret;
	}
}

