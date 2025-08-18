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

/*  Port Configuration Packet */
public class ANPacket197
{
	public Boolean deletePort = false;
	public Boolean dataSourceMask = false;
	
	public int portID = 0;
	public int outputType = 0;
	public int dataSource = 0;
	public int portType = 0;
	public Boolean enabled = false;	
	public int portNumber = 0;
	public String serverAddress = "";
	
	public static final int ANPP = 0;
	public static final int NMEA = 1;
	
	public static final int INVALID = 0;
	public static final int ALL_DEVICES = 1;
	public static final int THIS_DEVICE = 2;

	public static final int TCP_SERVER = 0;
	public static final int TCP_CLIENT = 1;
				
	public ANPacket197()
	{
		
	}
	
	public ANPacket197 (ANPacket packet)
	{
		if ((packet.data[0] & 0x01) != 0) deletePort = true;
		if ((packet.data[0] & 0x02) != 0) dataSourceMask = true;
		
		portID = (int)(packet.data[1] & 0xFF);		
		outputType = (int)(packet.data[2] & 0xFF);		
		dataSource = TypeConversion.bytesToUInt16(packet.data,3);
		portType = (int)(packet.data[5] & 0xFF);	
		enabled = ((packet.data[6] & 0xFF) > 0 ? true : false);
		portNumber = TypeConversion.bytesToUInt16(packet.data,7);
		
		byte[] serv_addr = new byte[32];
		System.arraycopy (packet.data, 9, serv_addr, 0,32);
		serverAddress =  new String(serv_addr).trim();
		
	}
	
	public String getOutputType(int index)
	{
		switch(index)
		{
			case ANPP:
				return "ANPP";
			case NMEA:
				return "NMEA";			
			default:
				return "Error!";
		}
	}	
	
	public String getPortType(int index)
	{
		switch(index)
		{
			case TCP_SERVER:
				return "TCP Server";
			case TCP_CLIENT:
				return "TCP Client";			
			default:
				return "Error!";
		}
	}
	
	public String getDataSource(int index)
	{
		switch(index)
		{
			case INVALID:
				return "Invalid Device";
			case ALL_DEVICES:
				return "All Devices";
			case THIS_DEVICE:
				return "This Device";			
			default:
				return "Device " + index;
		}
	}
	
	
	public String getTextOutput(){
		
		String ret = "";
		ret += "--------------------------------- PORT CONFIGURATION --------------------------" + "\r\n";
		
		ret += "Port ID = " +  (portID == 255 ? "ANPP Logging" : portID) + "\r\n";
		ret += "Output Type = " +  getOutputType(outputType) + "\r\n";		
		if(!dataSourceMask) {
			ret += "Data Source = Device " + dataSource + "\r\n";
		}
		else {
			ret += "Data Source = " + getDataSource(dataSource) + "\r\n";
		}
		ret += "Port Type = " +  getPortType(portType) + "\r\n";
		ret += "Enabled = " +  (enabled ? "true" : "false") + "\r\n";
		ret += "Port Number = " + portNumber + "\r\n";
		ret += "Server Address = " + serverAddress + "\r\n";		
		ret += "--------------------------------- PORT CONFIGURATION --------------------------" + "\r\n\r\n";
		
		
		return ret;
	}
}

