package com.atomscat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import ratpack.spring.config.EnableRatpack;

/**
 * Created by Howell on 28/2/17.
 * e-mail:th15817161961@gmail.com
 */
@SpringBootApplication
@EnableRatpack
@EnableCaching
public class Server {

    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }

}
