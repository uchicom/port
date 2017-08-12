// (c) 2017 uchicom
package com.uchicom.port.dto;

import java.net.InetSocketAddress;

/**
 * @author uchicom: Shigeki Uchiyama
 *
 */
public class TransferDto {

	private InetSocketAddress from;
	private InetSocketAddress to;
	public TransferDto(InetSocketAddress from, InetSocketAddress to) {
		this.from = from;
		this.to = to;
	}
	public InetSocketAddress getFrom() {
		return from;
	}
	public void setFrom(InetSocketAddress from) {
		this.from = from;
	}
	public InetSocketAddress getTo() {
		return to;
	}
	public void setTo(InetSocketAddress to) {
		this.to = to;
	}
}
