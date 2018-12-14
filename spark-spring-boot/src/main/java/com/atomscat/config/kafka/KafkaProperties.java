package com.atomscat.config.kafka;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="atoms.kafka")
@Data
public class KafkaProperties {
	private String topics;
	private String bootstrap;
	private String keyDeserializer="org.apache.kafka.common.serialization.StringDeserializer";
	private String valueDeserializer="org.apache.kafka.common.serialization.StringDeserializer";
	private String groupId;
	private String offsetReset="latest";
	private boolean autoCommit=false;
}
