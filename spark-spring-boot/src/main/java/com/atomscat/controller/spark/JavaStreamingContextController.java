package com.atomscat.controller.spark;

import com.atomscat.config.spark.SparkProperties;
import com.atomscat.entity.JavaStreamingContextList;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.StreamingContextState;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/streaming")
public class JavaStreamingContextController {

    @Autowired
    private JavaStreamingContextList javaStreamingContextList;

    @Autowired
    private SparkConf sparkConf;

    @Autowired
    private SparkProperties sparkProperties;

    @Autowired
    ApplicationContext context;

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    @ResponseBody
    public String start() throws InterruptedException {

        javaStreamingContextList.getJavaStreamingContextList().get(0).start();
        javaStreamingContextList.getJavaStreamingContextList().get(0).awaitTermination();
        return "ok";
    }

    @RequestMapping(value = "/stop", method = RequestMethod.GET)
    @ResponseBody
    public String stop(){
        javaStreamingContextList.getJavaStreamingContextList().get(0).stop();


        List<JavaStreamingContext> javaStreamingContexts = new ArrayList<>();
        JavaStreamingContext javaStreamingContext = new JavaStreamingContext(sparkConf, Durations.seconds(sparkProperties.getInterval()));
        // add check point directory
        //javaStreamingContext.checkpoint(sparkProperties.getCheckpointPath());
        javaStreamingContexts.add(javaStreamingContext);
        context.getBean(JavaStreamingContextList.class).setJavaStreamingContextList(javaStreamingContexts);
        return "ok";
    }

}
