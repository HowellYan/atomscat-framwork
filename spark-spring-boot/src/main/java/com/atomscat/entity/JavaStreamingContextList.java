package com.atomscat.entity;

import lombok.Data;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import java.io.Serializable;
import java.util.List;

@Data
public class JavaStreamingContextList implements Serializable {
    private List<JavaStreamingContext> javaStreamingContextList;
}
