package com.proxy.monitor.repository;

import com.proxy.monitor.dto.Message;

public interface InfluxDBRepository {

	void save(Message message);

}
