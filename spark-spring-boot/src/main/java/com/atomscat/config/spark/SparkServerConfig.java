package com.atomscat.config.spark;

import com.atomscat.entity.JavaStreamingContextList;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableConfigurationProperties({SparkProperties.class})
public class SparkServerConfig {
    @Autowired
    private SparkProperties sparkProperties;

    @Bean
    public SparkConf sparkConf() {
        SparkConf sparkConf = new SparkConf().setAppName(sparkProperties.getAppName()).setMaster(sparkProperties.getMaster());
        sparkConf.set("spark.driver.allowMultipleContexts", "true");
        return sparkConf;
    }

    @Bean
    public JavaStreamingContextList javaStreamingContext() {
        JavaStreamingContextList javaStreamingContextList = new JavaStreamingContextList();
        List<JavaStreamingContext> javaStreamingContexts = new ArrayList<>();
        JavaStreamingContext javaStreamingContext = new JavaStreamingContext(sparkConf(), Durations.seconds(sparkProperties.getInterval()));
        // add check point directory
        //javaStreamingContext.checkpoint(sparkProperties.getCheckpointPath());
        javaStreamingContexts.add(javaStreamingContext);
        javaStreamingContextList.setJavaStreamingContextList(javaStreamingContexts);
        return javaStreamingContextList;
    }
}
