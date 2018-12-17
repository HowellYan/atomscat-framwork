package com.atomscat.controller.spark;

import org.apache.log4j.Logger;
import org.apache.spark.deploy.SparkSubmit;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping("/job")
public class SparkJobController {
    private final static Logger log = Logger.getLogger(SparkJobController.class);

    @RequestMapping(value="/job/executeJob",method= RequestMethod.GET)
    @ResponseBody
    String executeSparkJob(@RequestParam("jarId") String jarId, @RequestParam("sparkUri") String sparkUri) {
        StringBuffer msg = new StringBuffer(jarId+":"+sparkUri);
        String[] arg0=new String[]{
                "/usr/job/"+jarId,
                "--master",sparkUri,
                "--name","web polling",
                "--executor-memory","1G"
        };
        log.info("提交作业...");
        try {
            SparkSubmit.main(arg0);
        } catch (Exception e) {
            log.info("出错了！", e);
            msg.append(e.getMessage());
        }
        return msg.toString();
    }
}
