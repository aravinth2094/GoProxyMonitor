package com.proxy.monitor.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.proxy.monitor.dto.Message;

@Configuration
public class ProxyMonitorConfiguration {

	@Value("${kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Bean
	public NewTopic proxyMonitor() {
		return new NewTopic("proxyMonitor", 1, (short) 1);
	}

	@Bean
	ConsumerFactory<String, Message> consumerFactory() {
		Map<String, Object> properties = new HashMap<>();
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, "proxyMonitorGroup");
		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		return new DefaultKafkaConsumerFactory<>(properties, new StringDeserializer(),
				new JsonDeserializer<>(Message.class));
	}

	@Bean
	KafkaListenerContainerFactory<?> kafkaListenerContainerFactory(ConsumerFactory<String, Message> consumerFactory) {
		ConcurrentKafkaListenerContainerFactory<String, Message> kafkaListenerContainerFactory = new ConcurrentKafkaListenerContainerFactory<>();
		kafkaListenerContainerFactory.setConcurrency(20);
		kafkaListenerContainerFactory.setConsumerFactory(consumerFactory);
		return kafkaListenerContainerFactory;
	}

}
