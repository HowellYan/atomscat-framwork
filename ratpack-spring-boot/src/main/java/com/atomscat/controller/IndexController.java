package com.atomscat.controller;

import com.atomscat.service.IndexService;
import com.atomscat.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import ratpack.func.Action;
import ratpack.handling.Chain;
import ratpack.http.internal.HttpHeaderConstants;

@Controller
@Slf4j
public class IndexController {

    @Autowired
    private IndexService indexService;

    @Bean
    public Action<Chain> index() {
        return chain -> chain
                .get(ctx -> ctx
                        .render("Hello " + indexService.message())
                );
    }

    @Bean
    public Action<Chain> get() {
        return  PrefixChain -> PrefixChain.prefix("api/get", GChain -> GChain
                .all(context -> {
                    context.byMethod(
                            method -> {
                                method.get(() -> {
                                    log.info(context.getRequest().getUri());
                                    JSONObject jsonObj = new JSONObject(JsonUtils.urlToJson(context.getRequest().getUri()));
                                    context.getResponse().contentType(HttpHeaderConstants.JSON).send(indexService.get(jsonObj));
                                });
                            });
                })
        );
    }

    @Bean
    public Action<Chain> post() {
        return  PrefixChain -> PrefixChain.prefix("api/post", GChain -> GChain
                .all(context -> {
                    context.byMethod(
                            method -> {
                                method.post(() -> {
                                    context.getRequest().getBody().then(request -> {
                                        log.info(request.getText());
                                        JSONObject jsonObj = new JSONObject(request.getText());
                                        context.getResponse().contentType(HttpHeaderConstants.JSON).send(indexService.get(jsonObj));
                                    });

                                });
                            });
                })
        );
    }
}
