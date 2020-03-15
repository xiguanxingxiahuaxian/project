package com.shenl.project.controller;

import com.shenl.project.bean.Activity;
import com.shenl.project.bean.MessageCore;
import com.shenl.project.bean.SelectCore;
import com.shenl.project.respository.ActivityRepository;
import com.shenl.project.respository.InfoRepository;
import com.shenl.project.respository.MenuRepository;
import com.shenl.project.respository.SelectCoreRepository;
import com.shenl.project.utils.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class ActivityController {


    /**
     * “我的里面，活动中心包括该活动，以及资讯信息”
     */


    @Autowired
    ActivityRepository activityRepository;
    @Autowired
    InfoRepository infoRepository;
    @Autowired
    SelectCoreRepository selectCore;

    @GetMapping(value = "/getActivity")
    public Map<String, Object> getActivity(){
        MessageCore messageCore =new MessageCore();
        messageCore.setActivities(selectCore.findAll());
        messageCore.setInfos(infoRepository.findAll());
        return Global.getMap(messageCore);
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
