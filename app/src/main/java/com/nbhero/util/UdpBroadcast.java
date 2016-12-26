package com.nbhero.util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;


public abstract class UdpBroadcast {

	private static final String TAG = "UdpBroadcast";
	private static final int BUFFER_SIZE = 100;
	
	private int port = Constants.UDP_PORT;
	private DatagramSocket socket;
	private DatagramPacket packetToSend;
	private InetAddress inetAddress;
	private ReceiveData receiveData;

	public void setPort(int port) {
		this.port = port;
	}
	
	public UdpBroadcast() {
		super();
		
		try {
			inetAddress = InetAddress.getByName("255.255.255.255");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open udp socket
	 */
	public void open() {
		
		try {
			socket = new DatagramSocket(port);
			socket.setBroadcast(true);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Close udp socket
	 */
	public void close() {
		stopReceive();
		if (socket != null) {
			socket.close();
		}
	}
	
	/**
	 * broadcast message
	 * @param text
	 * 			the message to broadcast
	 */
	public void send(String text) {
		if (socket == null || text == null) {
			return;
		}
		
		text = text.trim();
		packetToSend = new DatagramPacket(
				text.getBytes(), text.getBytes().length, inetAddress, port);
			
		try {
			socket.setSoTimeout(200);
			stopReceive();

			new Thread() {
				@Override
				public void run() {

					//remove the data in read chanel
					DatagramPacket packet = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
					while (true) {
						try {
							socket.receive(packet);
						} catch (Exception e) {
							break;
						}
					}

					//send data
					try {
						socket.setSoTimeout(3000);
						socket.send(packetToSend);
					} catch (IOException e) {
						e.printStackTrace();
					}

					//receive response
					receiveData = new ReceiveData();
					receiveData.start();
				}
			}.start();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Stop to receive
	 */
	public void stopReceive() {
		
		if (receiveData!=null && !receiveData.isStoped()) {
			receiveData.stop();
		}
	}
	
	public abstract void onReceived(List<DatagramPacket> packets);
	
	private class ReceiveData implements Runnable {
		
		private boolean stop;
		private Thread thread;
		private List<DatagramPacket> packets;

		private ReceiveData() {
			thread = new Thread(this);
			packets = new ArrayList<DatagramPacket>();
		}
		
		@Override
		public void run() {
			
			long time = System.currentTimeMillis();
			while (System.currentTimeMillis() - time < 15000 && !stop) {

				Log.d("1", "try to receive");
				try {
					DatagramPacket packetToReceive = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
					socket.receive(packetToReceive);
					Log.e("zlz",new String(packetToReceive.getData(),0,packetToReceive.getLength() )+ System.currentTimeMillis());
					packets.add(packetToReceive);
				} catch (SocketTimeoutException e) {
					Log.w(TAG, "Receive packet timeout!");
					break;
				}catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
			if (!stop) {
				stop = true;
				onReceived(packets);
			}
		}
		
		void start() {
			thread.start();
		}
		
		void stop() {
			stop = true;
		}
		
		boolean isStoped() {
			return stop;
		}
	}
}
