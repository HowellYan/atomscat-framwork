package com.atomscat;

import com.atomscat.config.spark.EnableSparkServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableSparkServer
public class Server {
    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }
}
