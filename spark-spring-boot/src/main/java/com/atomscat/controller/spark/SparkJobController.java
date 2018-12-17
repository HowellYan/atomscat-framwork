package com.atomscat.controller.spark;

import org.apache.log4j.Logger;
import org.apache.spark.launcher.SparkLauncher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/job")
public class SparkJobController {
    private final static Logger log = Logger.getLogger(SparkJobController.class);

    @RequestMapping(value = "/job/executeJob", method = RequestMethod.GET)
    @ResponseBody
    String executeSparkJob(@RequestParam("appResource") String appResource, @RequestParam("mainClass") String mainClass) {
        String javaHome = "/tools/java";
        String sparkHome = "/tools/spark";
        StringBuffer msg = new StringBuffer(appResource + ":" + mainClass);



        return msg.toString();
    }
}
