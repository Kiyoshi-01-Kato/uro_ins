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

public class ANPacket
{
	public static final int PACKET_ID_ACKNOWLEDGE = 0;
	public static final int PACKET_ID_REQUEST = 1;
	public static final int PACKET_ID_DEVICE_INFORMATION = 3;
	public static final int PACKET_ID_RESTORE_FACTORY_SETTINGS = 4;
	public static final int PACKET_ID_RESET = 5;
	public static final int PACKET_ID_NETWORK_SETTINGS = 11;

	public static final int PACKET_ID_SYSTEM_STATE = 20;
	public static final int PACKET_ID_UNIX_TIME = 21;
	public static final int PACKET_ID_STATUS = 23;
	public static final int PACKET_ID_REMOTE_UNIT_TRACK = 24;
	public static final int PACKET_ID_REMOTE_STATE = 25;
	
	public static final int PACKET_ID_RAW_SENSORS_PACKET = 28;
	public static final int PACKET_ID_REMOTE_RAW_SENSORS_PACKET = 29;

	public int id;
	public int length;
	public byte[] data;

	public ANPacket(int length, int id)
	{
		this.length = length;
		this.id = id;
		this.data = new byte[length];
	}
}
