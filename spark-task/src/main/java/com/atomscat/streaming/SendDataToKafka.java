package com.atomscat.streaming;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel;
import org.apache.spark.mllib.recommendation.Rating;

import java.util.*;

public class SendDataToKafka {

    public static void sendData(MatrixFactorizationModel bestModel, String user, String goodsCategory) {
        Rating[] ratingsList = bestModel.recommendProducts(Integer.valueOf(user), 3);
        String brokers = "192.168.31.166:9092";
        String topics = "spark-als";
        Set<String> topicsSet = new HashSet(Arrays.asList(topics.split(",")));
        Map<String, Object> kafkaParams = new HashMap();
        kafkaParams.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaParams.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaParams.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaParams.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaParams.put("bootstrap.servers", brokers);
        kafkaParams.put("group.id", topics);

        KafkaProducer kafkaProducer = new KafkaProducer<String, String>(kafkaParams);
        ProducerRecord record = new ProducerRecord<String, String>(topics, user);
        kafkaProducer.send(record);
        for (Rating rating : ratingsList) {
            record = new ProducerRecord<String, String>(topics, goodsCategory + "," + String.valueOf(rating.user()) + "," + String.valueOf(rating.product()));
            kafkaProducer.send(record);
        }
    }
}
