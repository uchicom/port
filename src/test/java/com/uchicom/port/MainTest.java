// (c) 2017 uchicom
package com.uchicom.port;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.Test;

import com.uchicom.port.dto.TransferDto;

/**
 * @author uchicom: Shigeki Uchiyama
 *
 */
public class MainTest {

	@Test
	public void testCreateTransferDtoList1() {
		String[] args = new String[]{"192.168.1.4:9999", "192.168.1.4:10000"};
		List<TransferDto> transferDtoList = Main.createTransferDtoList(args);
		assertEquals(2, transferDtoList.size());
		assertEquals("localhost", transferDtoList.get(0).getFrom().getHostName());
		assertEquals(9999, transferDtoList.get(0).getFrom().getPort());
		assertEquals("192.168.1.4", transferDtoList.get(0).getTo().getAddress().getHostAddress());
		assertEquals(9999, transferDtoList.get(0).getTo().getPort());
		assertEquals("localhost", transferDtoList.get(1).getFrom().getHostName());
		assertEquals(10000, transferDtoList.get(1).getFrom().getPort());
		assertEquals("192.168.1.4", transferDtoList.get(1).getTo().getAddress().getHostAddress());
		assertEquals(10000, transferDtoList.get(1).getTo().getPort());
	}
	@Test
	public void testCreateTransferDtoList2() {
		String[] args = new String[]{":4444;192.168.1.4:9999"};
		List<TransferDto> transferDtoList = Main.createTransferDtoList(args);
		assertEquals(1, transferDtoList.size());
		assertEquals("localhost", transferDtoList.get(0).getFrom().getHostName());
		assertEquals(4444, transferDtoList.get(0).getFrom().getPort());
		assertEquals("192.168.1.4", transferDtoList.get(0).getTo().getAddress().getHostAddress());
		assertEquals(9999, transferDtoList.get(0).getTo().getPort());
	}
	@Test
	public void testCreateTransferDtoList3() {
		String[] args = new String[]{"127.0.0.1:;192.168.1.4:9999"};
		List<TransferDto> transferDtoList = Main.createTransferDtoList(args);
		assertEquals(1, transferDtoList.size());
		assertEquals("127.0.0.1", transferDtoList.get(0).getFrom().getAddress().getHostAddress());
		assertEquals(9999, transferDtoList.get(0).getFrom().getPort());
		assertEquals("192.168.1.4", transferDtoList.get(0).getTo().getAddress().getHostAddress());
		assertEquals(9999, transferDtoList.get(0).getTo().getPort());
	}
	@Test
	public void testCreateTransferDtoList4() {
		List<TransferDto> transferDtoList = Main.createTransferDtoList(new File("test/test.txt"));
		assertEquals(4, transferDtoList.size());
		assertEquals("localhost", transferDtoList.get(0).getFrom().getHostName());
		assertEquals(9999, transferDtoList.get(0).getFrom().getPort());
		assertEquals("192.168.1.4", transferDtoList.get(0).getTo().getAddress().getHostAddress());
		assertEquals(9999, transferDtoList.get(0).getTo().getPort());
		assertEquals("localhost", transferDtoList.get(1).getFrom().getHostName());
		assertEquals(10000, transferDtoList.get(1).getFrom().getPort());
		assertEquals("192.168.1.4", transferDtoList.get(1).getTo().getAddress().getHostAddress());
		assertEquals(10000, transferDtoList.get(1).getTo().getPort());
		assertEquals("localhost", transferDtoList.get(2).getFrom().getHostName());
		assertEquals(4444, transferDtoList.get(2).getFrom().getPort());
		assertEquals("192.168.1.4", transferDtoList.get(2).getTo().getAddress().getHostAddress());
		assertEquals(9999, transferDtoList.get(2).getTo().getPort());
		assertEquals("127.0.0.1", transferDtoList.get(3).getFrom().getAddress().getHostAddress());
		assertEquals(9999, transferDtoList.get(3).getFrom().getPort());
		assertEquals("192.168.1.4", transferDtoList.get(3).getTo().getAddress().getHostAddress());
		assertEquals(9999, transferDtoList.get(3).getTo().getPort());
	}
}
