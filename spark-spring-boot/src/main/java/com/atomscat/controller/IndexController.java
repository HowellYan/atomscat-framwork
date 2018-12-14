package com.atomscat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class IndexController {
    @RequestMapping(value = "index", method = RequestMethod.GET)
    @ResponseBody
    public String index() {
        return "spark spring boot start!";
    }
}
