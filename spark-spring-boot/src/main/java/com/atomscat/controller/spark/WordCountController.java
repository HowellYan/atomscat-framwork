package com.atomscat.controller.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/")
public class WordCountController {
    private static final Pattern SPACE = Pattern.compile(" ");

    @Autowired
    private JavaStreamingContext javaStreamingContext;

    @RequestMapping(value = "word_count", method = RequestMethod.GET)
    @ResponseBody
    public String wordCount() {
        String path = "/etc/profile";
        JavaSparkContext sc = javaStreamingContext.sparkContext();
        JavaRDD<String> lines = sc.textFile(path);
        JavaRDD<String> words = lines.flatMap((s) -> {
            return Arrays.asList(SPACE.split(s)).iterator();
        });
        JavaPairRDD<String, Integer> ones = words.mapToPair((s) -> {
            return new Tuple2(s, 1);
        });
        JavaPairRDD<String, Integer> counts = ones.reduceByKey((i1, i2) -> {
            return i1 + i2;
        });
        List<Tuple2<String, Integer>> output = counts.collect();
        Iterator var7 = output.iterator();

        while(var7.hasNext()) {
            Tuple2<?, ?> tuple = (Tuple2)var7.next();
            System.out.println(tuple._1() + ": " + tuple._2());
        }
        return "ok";
    }

}
