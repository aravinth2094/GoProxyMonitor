package com.proxy.monitor.dto;

import java.util.Date;
import java.util.Objects;

public class Message {

	private String domain;
	private String remoteAddress;
	private Date timestamp;
	private boolean blocked;

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getRemoteAddress() {
		return remoteAddress;
	}

	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public int hashCode() {
		return Objects.hash(domain, remoteAddress, timestamp);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Message)) {
			return false;
		}
		Message other = (Message) obj;
		return Objects.equals(domain, other.domain) && Objects.equals(remoteAddress, other.remoteAddress)
				&& Objects.equals(timestamp, other.timestamp);
	}

	@Override
	public String toString() {
		return "Message [domain=" + domain + ", remoteAddress=" + remoteAddress + ", timestamp=" + timestamp + "]";
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

}
