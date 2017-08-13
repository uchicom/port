// (c) 2017 uchicom
package com.uchicom.port;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.uchicom.port.dto.TransferDto;
import com.uchicom.remon.Constants;
import com.uchicom.remon.runnable.Througher;

/**
 * @author uchicom: Shigeki Uchiyama
 *
 */
public class Porter {

	private boolean alive = true;
	public void execute(List<TransferDto> transferList) {
		Porter porter = new Porter();
		ExecutorService executorService = Executors.newFixedThreadPool(transferList.size());
		for (TransferDto transfer : transferList) {
			executorService.execute(()-> {
				porter.execute(transfer);
			});
		}
	}
	public void execute(TransferDto dto) {
		System.out.println("start");
		while(alive) {
			try (ServerSocket fromServer = new ServerSocket();){
				fromServer.bind(dto.getFrom());
				while (fromServer.isBound()) {
					Socket fromSocket = null;
					Socket toSocket = null;
					try {
						fromSocket = fromServer.accept();
						toSocket = new Socket();
						toSocket.connect(dto.getTo());
						start(fromSocket, toSocket);
					} catch (Exception e) {
						e.printStackTrace();
						if (toSocket != null) {
							toSocket.close();
						}
						if (fromSocket != null) {
							fromSocket.close();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("end");
	}

	/**
	 * 起動処理.
	 * @param receiveSocket
	 * @param sendSocket
	 */
	public void start(Socket receiveSocket, Socket sendSocket) {

		System.out.println("start start");
		Thread remoteThrougher = new Thread(new Througher(receiveSocket, sendSocket));
		remoteThrougher.setDaemon(true);
		remoteThrougher.start();
		if (Constants.DEBUG) System.out.println("send起動");

		Thread localThrougher = new Thread(new Througher(sendSocket, receiveSocket));
		localThrougher.setDaemon(true);
		localThrougher.start();
		if (Constants.DEBUG) System.out.println("receive起動");
		System.out.println("start end");
	}
	public boolean isAlive() {
		return alive;
	}
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
}
