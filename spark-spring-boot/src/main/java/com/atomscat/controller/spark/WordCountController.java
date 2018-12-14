package com.atomscat.controller.spark;

import com.atomscat.entity.JavaStreamingContextList;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.StorageLevels;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
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
    private JavaStreamingContextList javaStreamingContextList;

    @RequestMapping(value = "word_count", method = RequestMethod.GET)
    @ResponseBody
    public String wordCount() {
        String path = "/etc/profile";
        JavaSparkContext sc = javaStreamingContextList.getJavaStreamingContextList().get(0).sparkContext();
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

        StringBuffer stringBuffer = new StringBuffer("");
        while(var7.hasNext()) {
            Tuple2<?, ?> tuple = (Tuple2)var7.next();
            System.out.println();
            stringBuffer.append(tuple._1() + ": " + tuple._2() + "<br/>");
        }
        return stringBuffer.toString();
    }

    @RequestMapping(value = "network_word_count", method = RequestMethod.GET)
    @ResponseBody
    public String networkWordCount() throws Exception {
        String[] args = new String[2];
        args[0] = "127.0.0.1";
        args[1] = "9999";
        JavaStreamingContext sc = javaStreamingContextList.getJavaStreamingContextList().get(0);
        JavaReceiverInputDStream<String> lines = sc.socketTextStream(args[0], Integer.parseInt(args[1]), StorageLevels.MEMORY_AND_DISK_SER);
        JavaDStream<String> words = lines.flatMap((x) -> {
            return Arrays.asList(SPACE.split(x)).iterator();
        });
        JavaPairDStream<String, Integer> wordCounts = words.mapToPair((s) -> {
            return new Tuple2(s, 1);
        }).reduceByKey((i1, i2) -> {
            return Integer.parseInt(i1.toString()) + Integer.parseInt(i2.toString());
        });
        wordCounts.print();
        System.out.println(wordCounts.toString());
        return "ok";
    }

}
