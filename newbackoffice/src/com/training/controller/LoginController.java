package com.training.controller;


import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/test")
public class LoginController {

    @GetMapping(value = "/getStr")
    public String test(){
        return "hello";
    }


    @GetMapping(value = "/getJsp")
    public String test1(){
        return "test";
    }
}
