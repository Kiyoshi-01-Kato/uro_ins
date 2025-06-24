/****************************************************************/
/*                                                              */
/*          Advanced Navigation Packet Protocol Library         */
/*          .NET C# Language Spatial SDK, Version 2.4           */
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

using System;
using System.Threading;
using System.Windows.Forms;

namespace ANPPExample
{
    public partial class Form1 : Form
    {
        Boolean tcpConnected = false;
        ANPacketDecoder anPacketDecoder;

        private static ManualResetEvent connectDone = new ManualResetEvent(false);
        private static ManualResetEvent receiveDone = new ManualResetEvent(false);

        public Form1()
        {
            InitializeComponent();
            anPacketDecoder = new ANPacketDecoder();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            comboPort.SelectedIndex = 0;

        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            if (tcpConnected)
            {
                // Receive the response from the remote device.
                Receive(client);
            }
        }

        private void buttonConnect_Click(object sender, EventArgs e)
        {
            if (tcpConnected)
            {
                try
                {
                    tcpConnected = false;
                    // Release the socket.
                    client.Shutdown(System.Net.Sockets.SocketShutdown.Both);
                    client.Close();
                    buttonConnect.Text = "Connect";
                    richTextBox1.AppendText("Disconnected\n");
                }
                catch
                {
                    richTextBox1.AppendText("TCP Disconnect Failed\n");
                }
            }
            else
            {
                try
                {
                    buttonConnect.Text = "Connecting ...";
                    buttonConnect.Enabled = false;

                    //System.Net.IPHostEntry ipHostInfo = System.Net.Dns.GetHostEntry(textHostname.Text);
                    System.Net.IPAddress[] addresses = System.Net.Dns.GetHostAddresses(textHostname.Text);
                    //System.Net.IPAddress ipAddress = ipHostInfo.AddressList[0];
                    System.Net.IPAddress ipAddress = addresses[0];
                    System.Net.IPEndPoint remoteEP = new System.Net.IPEndPoint(ipAddress, Convert.ToInt32(comboPort.Text));
                    client = new System.Net.Sockets.Socket(System.Net.Sockets.AddressFamily.InterNetwork, System.Net.Sockets.SocketType.Stream, System.Net.Sockets.ProtocolType.Tcp);

                    client.BeginConnect(remoteEP, new AsyncCallback(ConnectCallback), client);
                    connectDone.WaitOne();

                    richTextBox1.AppendText("Connected on: " + client.RemoteEndPoint.ToString() + "\n");

                    buttonConnect.Text = "Disconnect";
                    buttonConnect.Enabled = true;
                    tcpConnected = true;

                }
                catch
                {
                    richTextBox1.AppendText("Connect Failed\n");
                }
            }
        }

        private static void ConnectCallback(IAsyncResult ar)
        {
            try
            {
                // Retrieve the socket from the state object.
                System.Net.Sockets.Socket client2 = (System.Net.Sockets.Socket)ar.AsyncState;

                // Complete the connection.
                client2.EndConnect(ar);

                // Signal that the connection has been made.
                connectDone.Set();
            }
            catch (Exception e)
            { }
        }

        public class StateObject
        {
            // Dummy object
        }

        private void Receive(System.Net.Sockets.Socket client)
        {
            try
            {
                // Create the state object.
                StateObject state = new StateObject();

                // Begin receiving the data from the remote device.
                client.BeginReceive(anPacketDecoder.buffer, anPacketDecoder.bufferLength, anPacketDecoder.buffer.Length - anPacketDecoder.bufferLength, 0, new AsyncCallback(ReceiveCallback), state);
            }
            catch (Exception e)
            { }
        }

        private void ReceiveCallback(IAsyncResult ar)
        {
            try
            {
                // Retrieve the state object
                StateObject state = (StateObject)ar.AsyncState;

                // Read data from the remote device.
                int bytesRead = client.EndReceive(ar);
                anPacketDecoder.bufferLength += bytesRead;
                if (bytesRead > 0)
                {

                    ANPacket packet = null;
                    while ((packet = anPacketDecoder.packetDecode()) != null)
                    {
                        switch (packet.id)
                        {
                            case ANPacket.PACKET_ID_SYSTEM_STATE:
                                if (packet.length == 116)
                                {
                                    ANPacket20 anPacket20 = new ANPacket20(packet);
                                    this.Invoke((MethodInvoker)delegate
                                    {
                                        textBoxLatitude.Text = (anPacket20.position[0] * 180 / Math.PI).ToString();
                                        textBoxLongitude.Text = (anPacket20.position[1] * 180 / Math.PI).ToString();
                                        textBoxHeight.Text = anPacket20.position[2].ToString();
                                        textBoxRoll.Text = (anPacket20.orientation[0] * 180 / Math.PI).ToString();
                                        textBoxPitch.Text = (anPacket20.orientation[1] * 180 / Math.PI).ToString();
                                        textBoxYaw.Text = (anPacket20.orientation[2] * 180 / Math.PI).ToString();
                                    });
                                }
                                break;
                            case ANPacket.PACKET_ID_RAW_SENSORS_PACKET:
                                if (packet.length == 52)
                                {
                                    ANPacket28 anPacket28 = new ANPacket28(packet);
                                    this.Invoke((MethodInvoker)delegate
                                    {
                                        richTextBox1.AppendText("Received Raw Sensors Packet\n");
                                    });
                                }
                                break;
                        }
                    }


                    // Get the rest of the data.
                    client.BeginReceive(anPacketDecoder.buffer, anPacketDecoder.bufferLength, anPacketDecoder.buffer.Length - anPacketDecoder.bufferLength, 0, new AsyncCallback(ReceiveCallback), state);
                }
                else
                {
                    // Signal that all bytes have been received.
                    receiveDone.Set();
                }
            }
            catch (Exception e)
            { }
        }

    }
}
