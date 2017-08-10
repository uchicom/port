// (c) 2017 uchicom
package com.uchicom.porter;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import com.uchicom.remon.Constants;
import com.uchicom.remon.runnable.Througher;

/**
 * @author uchicom: Shigeki Uchiyama
 *
 */
public class Porter {

	public void execute(String host, int port) {
		execute(port, host, port);
	}
	public void execute(int fromPort, String host, int port) {
		execute("localhost", port, host, port);
	}
	public void execute(String fromHost, int fromPort, String host, int port) {
		System.out.println("start");
		try (ServerSocket fromServer = new ServerSocket();){
			fromServer.bind(new InetSocketAddress(fromHost, fromPort));
			while (fromServer.isBound()) {
				Socket fromSocket = fromServer.accept();
				Socket toSocket = new Socket(host, port);
				start(fromSocket, toSocket);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("end");
	}
	public void start(Socket receiveSocket, Socket sendSocket) {

		System.out.println("start start");
		Thread remoteThrougher = new Thread(new Througher(receiveSocket, sendSocket));
//		remoteThrougher.setDaemon(true);
		remoteThrougher.start();
		if (Constants.DEBUG) System.out.println("send起動");
//
		Thread localThrougher = new Thread(new Througher(sendSocket, receiveSocket));
////		localThrougher.setDaemon(true);
		localThrougher.start();
		if (Constants.DEBUG) System.out.println("receive起動");
		System.out.println("start end");
	}
}
