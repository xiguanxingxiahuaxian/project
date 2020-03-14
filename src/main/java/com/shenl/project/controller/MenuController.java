package com.shenl.project.controller;

import com.shenl.project.respository.MenuRepository;
import com.shenl.project.utils.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MenuController {

    @Autowired
    MenuRepository menuRepository;

    @GetMapping(value = "/getMenu")
    public Map<String, Object> getMenu(){
        return Global.getMap(menuRepository.findAll());
    }

}
