package com.training.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
//@RequestMapping("/")
public class HomePageController {


    @Resource
    HttpSessionRequestCache httpSessionRequestCache;


//    @GetMapping
//    public String home(){
//        return "login";
//    }



    @RequestMapping(value = "/login",method = { RequestMethod.GET, RequestMethod.POST })
    public String doLogin(@RequestHeader(value = "referer", required = false) final String referer,
                          @RequestParam(value = "error", defaultValue = "false") final boolean loginError, final Model model,
                          final HttpServletRequest request, final HttpServletResponse response, final HttpSession session)
    {
        if (!loginError)
        {
            storeReferer(referer, request, response);
            return "redirect:";
        }

        return "login";
    }

    protected void storeReferer(final String referer, final HttpServletRequest request, final HttpServletResponse response)
    {
        if (StringUtils.isNotBlank(referer) && !StringUtils.endsWith(referer, "/login")
                && StringUtils.contains(referer, request.getServerName()))
        {
            httpSessionRequestCache.saveRequest(request, response);
        }
    }
}
