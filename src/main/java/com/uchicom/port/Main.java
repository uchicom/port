// (c) 2017 uchicom
package com.uchicom.port;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
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
		if (args.length == 0) {
			//Windowを表示する
		} else if (args.length > 0) {
			//ファイル読み込みか、コマンドライン引数で指定して起動する
			List<TransferDto> transferList = null;
			if ("-f".equals(args[0])) {
				if (args.length == 2) {
					transferList = createTransferDtoList(new File(args[1]));
				}
			} else {
				transferList = createTransferDtoList(args);
			}
			if (transferList != null && !transferList.isEmpty()) {
				Porter porter = new Porter();
				porter.execute(transferList);
			}

		}

	}

	public static List<TransferDto> createTransferDtoList(File file) {
		try {
			String data = new String(Files.readAllBytes(file.toPath()));
			return createTransferDtoList(data.split("\r\n|[\n\\s]"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 変換リストを作成する
	 * @param args
	 * @return
	 */
	public static List<TransferDto> createTransferDtoList(String[] args) {
		List<TransferDto> transferDtoList = new ArrayList<>();
		for (String arg: args) {
			System.out.println(arg);
			InetSocketAddress from = null;
			InetSocketAddress to = null;
			String[] splits = arg.split(";");
			if (splits.length == 1) {
				String[] address = splits[0].split(":", -1);
				if (address.length == 2) {
					int port = Integer.parseInt(address[1]);
					from = new InetSocketAddress("localhost", port);
					to = new InetSocketAddress(address[0], port);
				}
			} else if (splits.length == 2) {
				String[] address = splits[1].split(":", -1);
				int port = 0;
				if (address.length == 2) {
					port = Integer.parseInt(address[1]);
					to = new InetSocketAddress(address[0], port);
				}
				address = splits[0].split(":", -1);
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
			System.out.println(from + ":" + to);
			transferDtoList.add(new TransferDto(from, to));
		}
		return transferDtoList;
	}
}
