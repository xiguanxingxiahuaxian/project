package com.shenl.project.controller;

import com.shenl.project.bean.Activity;
import com.shenl.project.respository.ActivityRepository;
import com.shenl.project.respository.MenuRepository;
import com.shenl.project.utils.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class ActivityController {

    @Autowired
    ActivityRepository activityRepository;

    @GetMapping(value = "/getActivity")
    public Map<String, Object> getActivity(){
        return Global.getMap(activityRepository.findAll());
    }

    @PostMapping(value = "/AddActivity")
    public Map<String, Object> AddActivity(@RequestBody Activity activity){
        return Global.getMap(activityRepository.save(activity));
    }

    @GetMapping(value = "/DelActivity")
    public Map<String, Object> DelActivity(@RequestParam("id") Integer id){
        activityRepository.delete(id);
        return Global.getMap(null,"删除成功");
    }

}
