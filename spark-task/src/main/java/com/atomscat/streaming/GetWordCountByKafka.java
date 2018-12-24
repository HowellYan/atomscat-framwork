package com.atomscat.streaming;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction2;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.Time;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import scala.Tuple2;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class GetWordCountByKafka {
    private static final Pattern SPACE = Pattern.compile(" ");
    private static final Pattern UserId = Pattern.compile("\\s?UserId: [a-zA-Z0-9._-]+\\s?");
    private static final Pattern ProductId = Pattern.compile("id\\\\\":\\\\\"[a-zA-Z0-9._-]+");
    private static final Pattern GoodsCategory = Pattern.compile("goodsCategory\\\\\":\\\\\"[a-zA-Z0-9._-]+");


    public GetWordCountByKafka() {
    }

    public static void main(String[] args) throws Exception {
        try {
            String brokers = "192.168.31.166:9092";
            String topics = "spark-log";
            SparkConf sparkConf = (new SparkConf()).setAppName("JavaDirectKafkaWordCount").setMaster("local[*]");
            JavaStreamingContext jssc = new JavaStreamingContext(sparkConf, Durations.seconds(2L));
            Set<String> topicsSet = new HashSet(Arrays.asList(topics.split(",")));
            Map<String, Object> kafkaParams = new HashMap();
            kafkaParams.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            kafkaParams.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            kafkaParams.put("bootstrap.servers", brokers);
            kafkaParams.put("group.id", topics);
            JavaInputDStream<ConsumerRecord<String, String>> messages = KafkaUtils.createDirectStream(jssc, LocationStrategies.PreferConsistent(), ConsumerStrategies.Subscribe(topicsSet, kafkaParams));

            JavaDStream<String> lines = messages.map(ConsumerRecord::value);

            SparkSession sparkSession = SparkSession.builder().sparkContext(jssc.ssc().sc()).getOrCreate();

            lines.print();
            /**
             * clear data
             */
            lines.filter(new Function<String, Boolean>() {
                @Override
                public Boolean call(String v1) throws Exception {
                    if (v1.indexOf("/api/goods/detail") > 0 && v1.indexOf("UserId: null") < 0) {
                        return true;
                    }
                    return false;
                }
            }).mapToPair(new PairFunction<String, String, Integer>() {
                @Override
                public Tuple2<String, Integer> call(String s) throws Exception {
                    String userId = getVal(s, UserId).replace("UserId: ", "");
                    String productId = getVal(s, ProductId).replace("id\\\":\\\"", "");
                    String goodsCategory = getVal(s, GoodsCategory).replace("goodsCategory\\\":\\\"", "");
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
                    String time = simpleDateFormat.format(new Date());
                    return new Tuple2<String, Integer>(goodsCategory + "," + userId + "," + productId + "," + time, 1);
                }
            }).foreachRDD(new VoidFunction2<JavaPairRDD<String, Integer>, Time>() {
                @Override
                public void call(JavaPairRDD<String, Integer> v1, Time v2) throws Exception {
                    if (v1.rdd().count() > 0) {
                        CountALSData.read(sparkSession, v1.rdd().toJavaRDD());
                        v1.rdd().saveAsTextFile("hdfs://slaves1:9000/spark/als_" + new Date().getTime());
                    }
                }
            });
            jssc.start();
            jssc.awaitTermination();
        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
        }
    }

    public static String getVal(String str, Pattern p) {
        Matcher m = p.matcher(str);
        if (m.find()) {
            return m.group().trim();
        }
        return null;
    }

}
