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

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.DefaultComboBoxModel;
import com.advancednavigation.anPackets.*;
import com.advancednavigation.anppExample.NetworkConnection;
import com.advancednavigation.anppExample.NetworkConnection.NetworkEvents;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Main implements NetworkEvents
{
	private JFrame frmAnppExample;
	private JButton btnConnect;

	// Network Connection
	private NetworkConnection networkConnection = null;

	protected static final int DEVICE_TIMEOUT = 1000;

	protected Boolean deviceConnection = false;

	private JTextArea textArea;
	private JPanel panel;
	private JLabel lblLatitude;
	private JLabel lblLongitude;
	private JLabel lblHeight;
	protected JTextField textFieldLatitude;
	protected JTextField textFieldLongitude;
	protected JTextField textFieldHeight;

	private JLabel lblRoll;
	private JLabel lblPitch;
	private JLabel lblYaw;
	protected JTextField textFieldRoll;
	protected JTextField textFieldPitch;
	protected JTextField textFieldYaw;
	private JComboBox<String> comboAddress;

	/*
	 * Code for sending packets
	 * 
	 * ANPacket packet = new ANPacket(1, ANPacket.PACKET_ID_REQUEST);
	 * packet.data[0] = ANPacket.PACKET_ID_BOOT_MODE;
	 * serialPort.write(ANPacketDecoder.packetEncode(packet));
	 * 
	 * The above example sends a packet of length 1 requesting the device boot
	 * mode packet The serial port must be connected for the above code to work,
	 * you can use the boolean serialConnected to check this
	 */

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					Main window = new Main();
					window.frmAnppExample.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		// this sets swing to use the native look and feel, much more
		// aesthetically pleasing
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e)
		{
		}

		// all of the swing components and layout management is best edited
		// using the windowbuilder GUI
		frmAnppExample = new JFrame();
		frmAnppExample.setTitle("ANPP Example");
		frmAnppExample.setBounds(100, 100, 819, 631);
		frmAnppExample.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 10, 0, 0, 90, 5, 0 };
		gridBagLayout.rowHeights = new int[] { 10, 0, 0, 0, 5, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		frmAnppExample.getContentPane().setLayout(gridBagLayout);

		comboAddress = new JComboBox();
		comboAddress.setEditable(true);
		comboAddress.setModel(new DefaultComboBoxModel(new String[] { "an-subsonus-1.local", "an-subsonus-2.local", "an-subsonus-3.local", "an-subsonus-4.local" }));
		GridBagConstraints gbc_comboAddress = new GridBagConstraints();
		gbc_comboAddress.insets = new Insets(0, 0, 5, 5);
		gbc_comboAddress.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboAddress.gridx = 1;
		gbc_comboAddress.gridy = 1;
		frmAnppExample.getContentPane().add(comboAddress, gbc_comboAddress);

		btnConnect = new JButton("Connect");
		btnConnect.setEnabled(true);
		btnConnect.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (networkConnection == null)
				{
					try
					{
						InetAddress address = InetAddress.getByName((String) comboAddress.getSelectedItem());
						networkConnection = new NetworkConnection(address, 16718, false, Main.this);

						btnConnect.setText("Disconnect");
						comboAddress.setEnabled(false);
						// open log file
						// set connected

					}
					catch (UnknownHostException e2)
					{
						// updateStatusMessage("Error, Cannot resolve host");
					}
					catch (IOException e2)
					{
						// updateStatusMessage("Error, Cannot open port");
					}
				}
				else
				{
					if (networkConnection != null)
					{
						networkConnection.stop();
						networkConnection = null;
					}
					btnConnect.setText("Connect");
					comboAddress.setEnabled(true);
				}
			}
		});

		GridBagConstraints gbc_btnConnect = new GridBagConstraints();
		gbc_btnConnect.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnConnect.insets = new Insets(0, 0, 5, 5);
		gbc_btnConnect.gridx = 3;
		gbc_btnConnect.gridy = 1;
		frmAnppExample.getContentPane().add(btnConnect, gbc_btnConnect);

		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Data", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 3;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 2;
		frmAnppExample.getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 10, 0, 0, 5, 0, 5, 0, 0 };
		gbl_panel.rowHeights = new int[] { 5, 0, 0, 0, 5, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		lblLatitude = new JLabel("Latitude:");
		GridBagConstraints gbc_lblLatitude = new GridBagConstraints();
		gbc_lblLatitude.anchor = GridBagConstraints.EAST;
		gbc_lblLatitude.insets = new Insets(0, 0, 5, 5);
		gbc_lblLatitude.gridx = 1;
		gbc_lblLatitude.gridy = 1;
		panel.add(lblLatitude, gbc_lblLatitude);

		textFieldLatitude = new JTextField();
		GridBagConstraints gbc_textFieldLatitude = new GridBagConstraints();
		gbc_textFieldLatitude.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldLatitude.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldLatitude.gridx = 2;
		gbc_textFieldLatitude.gridy = 1;
		panel.add(textFieldLatitude, gbc_textFieldLatitude);
		textFieldLatitude.setColumns(15);

		lblRoll = new JLabel("Roll:");
		GridBagConstraints gbc_lblRoll = new GridBagConstraints();
		gbc_lblRoll.anchor = GridBagConstraints.EAST;
		gbc_lblRoll.insets = new Insets(0, 0, 5, 5);
		gbc_lblRoll.gridx = 4;
		gbc_lblRoll.gridy = 1;
		panel.add(lblRoll, gbc_lblRoll);

		textFieldRoll = new JTextField();
		GridBagConstraints gbc_textFieldRoll = new GridBagConstraints();
		gbc_textFieldRoll.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldRoll.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldRoll.gridx = 5;
		gbc_textFieldRoll.gridy = 1;
		panel.add(textFieldRoll, gbc_textFieldRoll);
		textFieldRoll.setColumns(15);

		lblLongitude = new JLabel("Longitude:");
		GridBagConstraints gbc_lblLongitude = new GridBagConstraints();
		gbc_lblLongitude.anchor = GridBagConstraints.EAST;
		gbc_lblLongitude.insets = new Insets(0, 0, 5, 5);
		gbc_lblLongitude.gridx = 1;
		gbc_lblLongitude.gridy = 2;
		panel.add(lblLongitude, gbc_lblLongitude);

		textFieldLongitude = new JTextField();
		GridBagConstraints gbc_textFieldLongitude = new GridBagConstraints();
		gbc_textFieldLongitude.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldLongitude.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldLongitude.gridx = 2;
		gbc_textFieldLongitude.gridy = 2;
		panel.add(textFieldLongitude, gbc_textFieldLongitude);
		textFieldLongitude.setColumns(15);

		lblPitch = new JLabel("Pitch:");
		GridBagConstraints gbc_lblPitch = new GridBagConstraints();
		gbc_lblPitch.anchor = GridBagConstraints.EAST;
		gbc_lblPitch.insets = new Insets(0, 0, 5, 5);
		gbc_lblPitch.gridx = 4;
		gbc_lblPitch.gridy = 2;
		panel.add(lblPitch, gbc_lblPitch);

		textFieldPitch = new JTextField();
		GridBagConstraints gbc_textFieldPitch = new GridBagConstraints();
		gbc_textFieldPitch.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldPitch.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldPitch.gridx = 5;
		gbc_textFieldPitch.gridy = 2;
		panel.add(textFieldPitch, gbc_textFieldPitch);
		textFieldPitch.setColumns(15);

		lblHeight = new JLabel("Height:");
		GridBagConstraints gbc_lblHeight = new GridBagConstraints();
		gbc_lblHeight.anchor = GridBagConstraints.EAST;
		gbc_lblHeight.insets = new Insets(0, 0, 5, 5);
		gbc_lblHeight.gridx = 1;
		gbc_lblHeight.gridy = 3;
		panel.add(lblHeight, gbc_lblHeight);

		textFieldHeight = new JTextField();
		GridBagConstraints gbc_textFieldHeight = new GridBagConstraints();
		gbc_textFieldHeight.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldHeight.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldHeight.gridx = 2;
		gbc_textFieldHeight.gridy = 3;
		panel.add(textFieldHeight, gbc_textFieldHeight);
		textFieldHeight.setColumns(15);

		lblYaw = new JLabel("Yaw:");
		GridBagConstraints gbc_lblYaw = new GridBagConstraints();
		gbc_lblYaw.anchor = GridBagConstraints.EAST;
		gbc_lblYaw.insets = new Insets(0, 0, 5, 5);
		gbc_lblYaw.gridx = 4;
		gbc_lblYaw.gridy = 3;
		panel.add(lblYaw, gbc_lblYaw);

		textFieldYaw = new JTextField();
		GridBagConstraints gbc_textFieldYaw = new GridBagConstraints();
		gbc_textFieldYaw.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldYaw.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldYaw.gridx = 5;
		gbc_textFieldYaw.gridy = 3;
		panel.add(textFieldYaw, gbc_textFieldYaw);
		textFieldYaw.setColumns(15);

		textArea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(textArea);
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.gridwidth = 3;
		gbc_textArea.insets = new Insets(0, 0, 5, 5);
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 1;
		gbc_textArea.gridy = 3;
		frmAnppExample.getContentPane().add(scrollPane, gbc_textArea);

	}

	@Override
	public void updateStatusMessage(String statusMessage)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void connectionClosed()
	{
		// indicate that it is disconnected

		networkConnection.stop();
		networkConnection = null;
	}

	protected synchronized void transmitData(byte[] data)
	{
		if (networkConnection != null)
		{
			networkConnection.transmitData(data);
		}
	}

	@Override
	public void processPacket(ANPacket packet)
	{
		switch (packet.id)
		{
			case ANPacket.PACKET_ID_SYSTEM_STATE:
				final ANPacket20 anPacket20 = new ANPacket20(packet);

				SwingUtilities.invokeLater(new Runnable()
				{
					public void run()
					{
						textArea.append("Received System State Packet\n");
						textArea.setCaretPosition(textArea.getDocument().getLength());
						textFieldLatitude.setText(Double.toString(anPacket20.position[0] * 180 / Math.PI));
						textFieldLongitude.setText(Double.toString(anPacket20.position[1] * 180 / Math.PI));
						textFieldHeight.setText(Double.toString(anPacket20.position[2]));
						textFieldRoll.setText(Double.toString(anPacket20.orientation[0] * 180 / Math.PI));
						textFieldPitch.setText(Double.toString(anPacket20.orientation[1] * 180 / Math.PI));
						textFieldYaw.setText(Double.toString(anPacket20.orientation[2] * 180 / Math.PI));

					}
				});
				break;
			case ANPacket.PACKET_ID_REMOTE_UNIT_TRACK:

				SwingUtilities.invokeLater(new Runnable()
				{
					public void run()
					{
						textArea.append("Received Remote Track Packet\n");
						textArea.setCaretPosition(textArea.getDocument().getLength());
					}
				});
				break;
		}
	}
}
