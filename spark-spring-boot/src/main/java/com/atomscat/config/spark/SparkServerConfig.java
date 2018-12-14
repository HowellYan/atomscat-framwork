package com.atomscat.config.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties({SparkProperties.class})
public class SparkServerConfig {
    @Autowired
    private SparkProperties sparkProperties;

    @Bean
    public SparkConf sparkConf() {
        SparkConf conf = new SparkConf().setAppName(sparkProperties.getAppName()).setMaster(sparkProperties.getMaster());
        return conf;
    }

    @Bean
    public JavaStreamingContext javaStreamingContext() {
        JavaStreamingContext jssc = new JavaStreamingContext(sparkConf(), Durations.seconds(sparkProperties.getInterval()));
        // add check point directory
        jssc.checkpoint(sparkProperties.getCheckpointPath());
        return jssc;
    }
}
