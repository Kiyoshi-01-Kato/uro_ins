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
package com.advancednavigation.anppExample;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import com.advancednavigation.anPackets.*;

public class NetworkConnection implements Runnable
{
	private ServerSocket tcpServerSocket;
	private Socket tcpSocket;
	private OutputStream tcpOutputStream;

	private DatagramSocket udpSocket;
	private InetAddress clientAddress;
	private int port;
	private Boolean udpConnection = false;
	private Boolean server = false;
	private Boolean connected = false;
	private Thread receiveThread;
	private ANPacketDecoder packetDecoder;
	private NetworkEvents networkEvents;

	public NetworkConnection(InetAddress clientAddress, int port, Boolean udpConnection, NetworkEvents networkEvents) throws IOException
	{
		packetDecoder = new ANPacketDecoder();
		this.port = port;
		this.clientAddress = clientAddress;
		if (clientAddress == null)
		{
			server = true;
		}
		else
		{
			server = false;
		}
		this.udpConnection = udpConnection;
		this.networkEvents = networkEvents;
		if (!udpConnection)
		{
			if (server)
			{
				tcpServerSocket = new ServerSocket(port);
				networkEvents.updateStatusMessage("Waiting for connection");
			}
			else
			{
				networkEvents.updateStatusMessage("Connecting");
			}
		}
		else
		{
			udpSocket = new DatagramSocket(port);
			if (!server)
			{
				udpSocket.connect(clientAddress, port);
				networkEvents.updateStatusMessage("Connected");
			}
			else
			{
				networkEvents.updateStatusMessage("Waiting for connection");
			}
		}
		receiveThread = new Thread(this);
		receiveThread.start();
	}

	public interface NetworkEvents
	{
		public void updateStatusMessage(String statusMessage);

		public void connectionClosed();

		public void processPacket(ANPacket packet);
	}

	public void stop()
	{
		receiveThread.interrupt();
		if (udpConnection)
		{
			udpSocket.close();
		}
		else
		{
			try
			{
				if (server && !connected)
				{
					tcpServerSocket.close();
				}
				else if (connected)
				{
					tcpSocket.close();
				}
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void transmitData(byte[] data)
	{
		if (connected)
		{
			if (!udpConnection)
			{
				try
				{
					tcpOutputStream.write(data);
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
					connected = false;
					networkEvents.updateStatusMessage("Socket disconnected");
				}
			}
			else
			{
				DatagramPacket transmitPacket = new DatagramPacket(data, data.length, clientAddress, port);
				try
				{
					udpSocket.send(transmitPacket);
				}
				catch (IOException e)
				{
					System.out.println(e);
				}
			}
		}
	}

	@Override
	public void run()
	{
		BufferedInputStream tcpInputStream = null;
		while (!Thread.currentThread().isInterrupted())
		{
			if (!udpConnection)
			{
				if (!connected)
				{
					try
					{
						if (server)
						{
							tcpSocket = tcpServerSocket.accept();
							tcpServerSocket.close();
							clientAddress = tcpSocket.getInetAddress();
							networkEvents.updateStatusMessage("Connected to " + clientAddress);
						}
						else
						{
							tcpSocket = new Socket();
							tcpSocket.connect(new InetSocketAddress(clientAddress, port), 3000);
							networkEvents.updateStatusMessage("Connected");
						}
						tcpSocket.setSoTimeout(15000);
						tcpInputStream = new BufferedInputStream(tcpSocket.getInputStream());
						tcpOutputStream = tcpSocket.getOutputStream();
						connected = true;
					}
					catch (SocketException exception)
					{
						networkEvents.updateStatusMessage("Error connecting");
						networkEvents.connectionClosed();
						break;
					}
					catch (IOException exception)
					{
						networkEvents.updateStatusMessage("Error connecting");
						networkEvents.connectionClosed();
						break;
					}
				}
				else
				{
					try
					{
						int bytesReceived = tcpInputStream.read(packetDecoder.buffer, packetDecoder.bufferLength, packetDecoder.bufferSpace());
						if (bytesReceived > 0)
						{
							packetDecoder.bufferLength += bytesReceived;
							ANPacket packet = null;
							while ((packet = packetDecoder.packetDecode()) != null)
							{
								networkEvents.processPacket(packet);
							}
						}
						else
						{
							networkEvents.updateStatusMessage("Socket disconnected");
							tcpSocket.close();
							connected = false;
						}
					}
					catch (SocketException e1)
					{
						networkEvents.updateStatusMessage("Socket disconnected");
						networkEvents.connectionClosed();
						break;
					}
					catch (SocketTimeoutException e2)
					{
						networkEvents.updateStatusMessage("Connection timeout");
						networkEvents.connectionClosed();
						break;
					}
					catch (IOException e)
					{
						networkEvents.updateStatusMessage("Socket error");
						networkEvents.connectionClosed();
						break;
					}
				}
			}
			else
			{
				DatagramPacket receivePacket = new DatagramPacket(packetDecoder.buffer, packetDecoder.bufferLength, packetDecoder.bufferSpace());
				try
				{
					udpSocket.receive(receivePacket);
					if (!connected)
					{
						clientAddress = receivePacket.getAddress();
						connected = true;
						System.out.println(clientAddress);
					}
					packetDecoder.bufferLength += receivePacket.getLength();
					ANPacket packet = null;
					while ((packet = packetDecoder.packetDecode()) != null)
					{
						networkEvents.processPacket(packet);
					}
				}
				catch (SocketException e1)
				{
					System.out.println(e1);
				}
				catch (IOException exception2)
				{
					System.out.println(exception2);
				}
			}
		}
	}
}
