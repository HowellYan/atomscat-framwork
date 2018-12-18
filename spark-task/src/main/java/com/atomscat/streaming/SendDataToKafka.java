package com.atomscat.streaming;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel;
import org.apache.spark.mllib.recommendation.Rating;

import java.util.*;

public class SendDataToKafka {

    public static void sendData(JavaSparkContext sc, String user){
        MatrixFactorizationModel bestModel2 = MatrixFactorizationModel.load(sc.sc(), "hdfs://slaves1:9000/model/all_*");
        Rating[] ratingsList = bestModel2.recommendProducts(Integer.valueOf(user), 3);

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

        KafkaProducer kafkaProducer =  new KafkaProducer<String,String>(kafkaParams);
        for (Rating rating : ratingsList) {
            ProducerRecord record = new ProducerRecord<String, String>(topics, String.valueOf(rating.user()), String.valueOf(rating.product()));
            kafkaProducer.send(record);
        }
    }

    public static void sendData(MatrixFactorizationModel bestModel){
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
        Rating[] ratingsList = bestModel.recommendProducts(2096876, 3);
        KafkaProducer kafkaProducer =  new KafkaProducer<String,String>(kafkaParams);
        for (Rating rating : ratingsList) {
            ProducerRecord record = new ProducerRecord<String,String>(topics, String.valueOf(rating.user()), String.valueOf(rating.product()));
            kafkaProducer.send(record);
        }
    }
}
