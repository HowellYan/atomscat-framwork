package com.atomscat.streaming;

import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import java.io.Serializable;

public class MyJavaStreamingContext extends JavaStreamingContext implements Serializable {
    private static final long serialVersionUID = 4222182685492143838L;

    public MyJavaStreamingContext(SparkConf sparkContext, Duration batchDuration) {
        super(sparkContext, batchDuration);
    }
}
