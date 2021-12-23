package com.training.controller;


import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/processJson")
public class ProcessJsonController {


    @RequestMapping(value = "")
    public String doGet(HttpServletRequest request, HttpServletResponse response, final ModelAndView modelAndView){



        return null;

    }




}
