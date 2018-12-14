package com.atomscat.config.spark;


import com.atomscat.config.kafka.KafkaProperties;
import com.atomscat.entity.InboundEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class KafkaStreamBuilder<T extends InboundEvent> {
    protected KafkaProperties kafkaProperties;
    protected JavaStreamingContext jssc;
    protected SparkProperties sparkProperties;

    public KafkaStreamBuilder() {
    }

    @Autowired
    public KafkaStreamBuilder(JavaStreamingContext jssc, SparkProperties sparkProperties, KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
        this.jssc = jssc;
        this.sparkProperties = sparkProperties;
    }

    public KafkaStreamBuilder<T> setKafkaProperties(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
        return this;
    }

    public KafkaStreamBuilder<T> setJssc(JavaStreamingContext jssc) {
        this.jssc = jssc;
        return this;
    }

    public KafkaStreamBuilder<T> setSparkProperties(SparkProperties sparkProperties) {
        this.sparkProperties = sparkProperties;
        return this;
    }

    @SuppressWarnings("unchecked")
    public JavaDStream<T> build() {

        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("key.deserializer", kafkaProperties.getKeyDeserializer());
        kafkaParams.put("value.deserializer", kafkaProperties.getValueDeserializer());
        kafkaParams.put("bootstrap.servers", kafkaProperties.getBootstrap());
        kafkaParams.put("group.id", kafkaProperties.getGroupId());
        kafkaParams.put("auto.offset.reset", kafkaProperties.getOffsetReset());
        kafkaParams.put("enable.auto.commit", kafkaProperties.isAutoCommit());
        String topics = kafkaProperties.getTopics();
        Collection<String> topicsSet = Arrays.asList(topics.split(","));
        List<JavaDStream<ConsumerRecord<String, InboundEvent>>> streamList = new ArrayList<>(sparkProperties.getStreamCount());
        for (int i = 0; i < sparkProperties.getStreamCount(); i++) {
            streamList.add(KafkaUtils.createDirectStream(jssc, LocationStrategies.PreferConsistent(), ConsumerStrategies.<String, InboundEvent>Subscribe(topicsSet, kafkaParams))
            );
        }
        JavaDStream<ConsumerRecord<String, InboundEvent>> directKafkaStream;
        directKafkaStream = jssc.union(streamList.get(0), streamList.subList(1, streamList.size()));
        JavaDStream<T> dataStream = directKafkaStream.map(cs -> {
            return (T) cs.value();
        });
        return dataStream;

    }

}
