package com.proxy.monitor.repository;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.influxdb.InfluxDBTemplate;
import org.springframework.stereotype.Repository;

import com.proxy.monitor.dto.Message;

@Repository
public class InfluxDBRepositoryImpl implements InfluxDBRepository {

	@Autowired
	private InfluxDBTemplate<Point> influxDBTemplate;

	@PostConstruct
	private void init() {
		influxDBTemplate.createDatabase();
	}

	@Override
	public void save(Message message) {
		final Point p = Point.measurement("traffic").time(message.getTimestamp().getTime(), TimeUnit.MILLISECONDS)
				.addField("domain", message.getDomain()).addField("remoteAddress", message.getRemoteAddress())
				.addField("blocked", message.isBlocked()).build();
		influxDBTemplate.write(p);
	}

}
