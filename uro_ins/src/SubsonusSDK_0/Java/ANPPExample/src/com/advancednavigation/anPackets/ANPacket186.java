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

/*  System Options Packet */
public class ANPacket186
{
	public int operationMode = 0;
	public int vehicleProfile = 0;
	public int enablePressureDepthAiding = 0;
	public float PressureDepthOffset = 0;
	public int forceFixedVelocityOfSound = 0;
	public float fixedVelocityOfSound = 0;
	
	public static final int UNCHANGED = 0;	
	public static final int MASTER = 1;
	public static final int SLAVE = 2;
	public static final int LISTEN_ONLY = 3;
	
	public static final int UNLIMITED = 1;
	public static final int STATIONARY = 2;
	public static final int BUOY = 3;
	public static final int DIVER = 4;
	public static final int OBSERVATION_CLASS_3D = 5;
	public static final int WORK_CLASS_3D = 6;
	public static final int TOWFISH = 7;
	public static final int GLIDER = 8;
	public static final int SMALL_BOAT = 9;
	public static final int LARGE_SHIP = 10;
	
	
	public ANPacket186()
	{
		
	}
	
	public ANPacket186 (ANPacket packet)
	{
		operationMode = (int)(packet.data[0] & 0xFF);
		vehicleProfile = (int)(packet.data[1] & 0xFF);
		enablePressureDepthAiding = (int)(packet.data[2] & 0xFF);
		PressureDepthOffset = TypeConversion.bytesToFloat(packet.data,3);
		forceFixedVelocityOfSound = (int)(packet.data[7] & 0xFF);
		fixedVelocityOfSound = TypeConversion.bytesToFloat(packet.data,8);		
	}
	
	public String getOperationMode(int index)
	{
		switch(index)
		{
			case UNCHANGED:
				return "Unchanged";
			case MASTER:
				return "Master";
			case SLAVE:
				return "Slave";
			case LISTEN_ONLY:
				return "Listen Only";
			default:
				return "Error!";
		}
	}
	
	public String getVehicleProfile(int index)
	{
		switch(index)
		{
			case UNCHANGED:
				return "Unchanged";
			case UNLIMITED:
				return "Unlimited";
			case STATIONARY:
				return "Stationary";
			case BUOY:
				return "Buoy";
			case DIVER:
				return "Diver";
			case OBSERVATION_CLASS_3D:
				return "3D Observation Class";
			case WORK_CLASS_3D:
				return "3D Work Class";
			case TOWFISH:
				return "Towfish";
			case GLIDER:
				return "Glider";
			case SMALL_BOAT:
				return "Small Boat";
			case LARGE_SHIP:
				return "Large Ship";
			default:
				return "Error!";
		}
	}
	
	public String getTextOutput(){
		
		return 
			  "--------------------------------- SYSTEM OPTIONS ------------------------------" + "\r\n"
			+ "Operation Mode = " + getOperationMode(operationMode) + "\r\n"
			+ "Vehicle Profile = " + getVehicleProfile(vehicleProfile) + "\r\n"
			+ "Enable Pressure Depth Aiding = " + (enablePressureDepthAiding > 0 ? "true" : "false") + "\r\n"
			+ "Pressure Depth Zero Offset = " + PressureDepthOffset + " meters\r\n"
			+ "Force Fixed Velocity of Sound = " + (forceFixedVelocityOfSound > 0 ? "true" : "false") + "\r\n"
			+ "Fixed Velocity of Sound = " + fixedVelocityOfSound + "\r\n"			
			+ "--------------------------------- SYSTEM OPTIONS ------------------------------" + "\r\n\r\n";
	}
}

