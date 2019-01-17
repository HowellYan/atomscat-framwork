package com.atomscat.controller;

import com.atomscat.service.DubboHelpMapService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

@RestController
public class IndexController {

    @Autowired
    private DubboHelpMapService dubboHelpMapService;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public void index(HttpServletResponse response) throws IOException {
        response.sendRedirect("/actuator");
    }

    @RequestMapping(value = "/help",method = RequestMethod.GET)
    public String help(HttpServletResponse response) throws IOException {
        return getSwaggerJson().toJSONString();
    }

    @RequestMapping(value = "/helpMap/{key}",method = RequestMethod.GET)
    public String helpMap(@PathVariable("key") String key, HttpServletResponse response) throws IOException {
        JSONObject jsonObject = getSwaggerJson();
        return dubboHelpMapService.getHelpByKey(jsonObject, key).toJSONString();
    }

    private JSONObject getSwaggerJson(){
        JSONParser parser = new JSONParser();
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource("swagger-ui/swagger.json");
        try {
            Object obj = parser.parse(new FileReader(url.getFile()));
            JSONObject jsonObject = (JSONObject) obj;
            return jsonObject;
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("Exception:" + e.getCause().getClass() + "," + e.getCause().getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Exception:" + e.getCause().getClass() + "," + e.getCause().getMessage());
        }
        return null;
    }


}
