package com.atomscat.streaming;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class CountALSData {

    public static void read(SparkSession sparkSession, String user) {
        JavaSparkContext sc = JavaSparkContext.fromSparkContext(sparkSession.sparkContext());
        JavaRDD<String> lines = sc.textFile("hdfs://slaves1:9000/spark/als_*");

        /**
         * group by goodsCategory`s prod rating
         */
        Map<String, Iterable<Tuple2<String, Integer>>> stringIterableJavaPairRDD = lines.mapToPair((s) -> {
            String[] strings = s.replaceAll("\\(", "").replaceAll("\\)", "").split(",");
            return new Tuple2<String, Integer>(strings[0] + "," + strings[1] + "," + strings[2], 1);
        }).reduceByKey((i1, i2) -> i1 + i2).mapToPair((s1) -> {
            String[] strings = s1._1().split(",");
            return new Tuple2<String, Tuple2<String, Integer>>(strings[0], new Tuple2(strings[1] + "," + strings[2], s1._2()));
        }).groupByKey().collectAsMap();

        stringIterableJavaPairRDD.forEach(new BiConsumer<String, Iterable<Tuple2<String, Integer>>>() {
            @Override
            public void accept(String s, Iterable<Tuple2<String, Integer>> tuple2s) {
                List<Tuple2<String, Integer>> tuple2Arrays = new ArrayList<>();
                tuple2s.forEach(new Consumer<Tuple2<String, Integer>>() {
                    @Override
                    public void accept(Tuple2<String, Integer> stringIntegerTuple2) {
                        tuple2Arrays.add(stringIntegerTuple2);
                    }
                });
                TrainALSModel.train(sc.parallelizePairs(tuple2Arrays), s, sc, user);
            }
        });

        /**
         * all prod rating
         */
        JavaPairRDD<String, Integer> stringIntegerJavaPairRDD = lines.mapToPair((s) -> {
            String[] strings = s.split(",");
            return new Tuple2<>(strings[1] + "," + strings[2], 1);
        });
        JavaPairRDD<String, Integer> counts = stringIntegerJavaPairRDD.reduceByKey((i1, i2) -> i1 + i2);
        TrainALSModel.train(sc, counts, user);
    }


}
