package com.training.controller;


import com.training.data.UIData;
import com.training.service.UIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/loadui")
public class LoadUiController {

    @Autowired
    private UIService uiService;

    @PostMapping(value = "/tree")
    @ResponseBody
    public List<UIData> loadTree(){

        return uiService.loadMenuTree();
    }
}
