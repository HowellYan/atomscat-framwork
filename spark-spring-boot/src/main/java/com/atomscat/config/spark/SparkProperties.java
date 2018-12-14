package com.atomscat.config.spark;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "atoms.spark")
@Data
public class SparkProperties {
    private String appName;
    private String master;
    private String checkpointPath;
    private int interval = 2;
    private int streamCount = 2;
}
