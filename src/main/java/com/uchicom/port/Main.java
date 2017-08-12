// (c) 2017 uchicom
package com.uchicom.port;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import com.uchicom.port.dto.TransferDto;

/**
 * @author uchicom: Shigeki Uchiyama
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<TransferDto> transferList = createTransferDtoList(args);
		if (!transferList.isEmpty()) {
			Porter porter = new Porter();
			for (TransferDto dto : transferList) {
				porter.execute(dto);
			}
		}

	}

	/**
	 * 変換リストを作成する
	 * @param args
	 * @return
	 */
	public static List<TransferDto> createTransferDtoList(String[] args) {
		List<TransferDto> transferDtoList = new ArrayList<>();
		for (String arg: args) {
			InetSocketAddress from = null;
			InetSocketAddress to = null;
			String[] splits = arg.split(";");
			if (splits.length == 1) {
				String[] address = splits[0].split(":");
				if (address.length == 2) {
					int port = Integer.parseInt(address[1]);
					from = new InetSocketAddress("localhost", port);
					to = new InetSocketAddress(address[0], port);
				}
			} else if (splits.length == 2) {
				String[] address = splits[1].split(":");
				int port = 0;
				if (address.length == 2) {
					port = Integer.parseInt(address[1]);
					to = new InetSocketAddress(address[0], port);
				}
				address = splits[0].split(":");
				if (address.length == 2) {
					String host = "localhost";
					if (address[0].length() > 0) {
						host = address[0];
					}
					if (address[1].length() > 0) {
						port = Integer.parseInt(address[1]);
					}
					from = new InetSocketAddress(host, port);
				}
			}
			transferDtoList.add(new TransferDto(from, to));
		}
		return transferDtoList;
	}
}
