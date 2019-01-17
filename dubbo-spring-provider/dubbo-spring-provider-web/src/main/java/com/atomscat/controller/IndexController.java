package com.atomscat.controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

@RestController
public class IndexController {

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public void index(HttpServletResponse response) throws IOException {
        response.sendRedirect("/actuator");
    }

    @RequestMapping(value = "/help",method = RequestMethod.GET)
    public String help(HttpServletResponse response) throws IOException {
        JSONParser parser = new JSONParser();
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource("swagger-ui/swagger.json");
        try {
            Object obj = parser.parse(new FileReader(url.getFile()));
            JSONObject jsonObject = (JSONObject) obj;
            return jsonObject.toJSONString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }


}
