package com.atomscat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import ratpack.func.Action;
import ratpack.handling.Chain;
import ratpack.service.Service;
import ratpack.spring.config.EnableRatpack;

/**
 * Created by Howell on 28/2/17.
 * e-mail:th15817161961@gmail.com
 */
@SpringBootApplication
@EnableRatpack
public class Server {

    @Bean
    public Action<Chain> home() {
        return chain -> chain
                .get(ctx -> ctx
                        .render("Hello " + service().message())
                );
    }

    @Bean
    public Service service() {
        return () -> "World!";
    }

    interface Service {
        String message();
    }

    public static void main(String[] args) {

        SpringApplication.run(Server.class, args);
    }
}
