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
public class ANPacket198
{
	public long unitSelect = 0;
	public int bufferSize = 0;
	public float bufferTimeout = 0;
	public int dataChunkSize = 0;
	public float dataChunkTimeout = 0;
	
	public Boolean DisableTCPAiding = false;
	public Boolean DisableDataChunkRulesForANPPData = false;	
			
	public ANPacket198(int settings_in)
	{
			
	}
	
	public ANPacket198 (ANPacket packet)
	{
		unitSelect = TypeConversion.bytesToUInt32(packet.data,8);
		bufferSize = TypeConversion.bytesToUInt16(packet.data,12);
		bufferTimeout = TypeConversion.bytesToFloat(packet.data,14);
		dataChunkSize = TypeConversion.bytesToUInt16(packet.data,18);
		dataChunkTimeout = TypeConversion.bytesToFloat(packet.data,20);
		
		if ((packet.data[26] & 0x01) != 0) DisableTCPAiding = true;
		if ((packet.data[26] & 0x02) != 0) DisableDataChunkRulesForANPPData = true;
				
	}
	
	
	public String getTextOutput(){
				
		String ret = "";		
		ret += "--------------------------------- MODEM CONFIGURATION -------------------------" + "\r\n";	
		ret += "Device Selection = " + (unitSelect == 0 ? "This Device" : "Remote Device") + "\r\n";
		ret += "Buffer Size = " + bufferSize + " bytes\r\n";
		ret += "Buffer Timeout = " + bufferTimeout + " seconds\r\n";
		ret += "Data Chunk Size = " + dataChunkSize + " bytes\r\n";
		ret += "Data Chunk Timeout = " + dataChunkTimeout + " seconds\r\n";
		ret += "Disable TCP Aiding = " + (DisableTCPAiding ? "true" : "false") + "\r\n";
		ret += "Disable Data Chunk Rules for ANPP Data = " + (DisableDataChunkRulesForANPPData ? "true" : "false") + "\r\n";	
		ret += "--------------------------------- MODEM CONFIGURATION -------------------------" + "\r\n\r\n";
		
		
		return ret;
	}
}

