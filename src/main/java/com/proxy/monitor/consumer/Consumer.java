package com.proxy.monitor.consumer;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.proxy.monitor.dto.Message;
import com.proxy.monitor.repository.InfluxDBRepository;

@Service
public class Consumer {

	private final Logger logger = LoggerFactory.getLogger(Consumer.class);

	@Autowired
	private InfluxDBRepository repository;

	@KafkaListener(topics = "proxyMonitor", groupId = "proxyMonitorGroup")
	public void consume(Message message) throws IOException {
		repository.save(message);
		if (message.isBlocked()) {
			logger.error("Blocked: {}", message);
		} else {
			logger.info("Consumed: {}", message);
		}
	}
}