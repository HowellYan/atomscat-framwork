package com.atomscat.config.spark;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootApplication
@ComponentScan("com.atomscat")
@EnableAutoConfiguration(exclude = { org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration.class })
@Import(SparkServerMarkerConfiguration.class)
public @interface EnableSparkServer {
}
