package com.shenl.project.controller;

import com.shenl.project.bean.Info;
import com.shenl.project.respository.InfoRepository;
import com.shenl.project.respository.MenuRepository;
import com.shenl.project.utils.Global;
import org.hibernate.tool.schema.extract.spi.InformationExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
public class InfoController {

    @Autowired
    InfoRepository infoRepository;

    @GetMapping(value = "/getInfo")
    public Map<String, Object> getInfo(){
        return Global.getMap(infoRepository.findAll());
    }

    /**
     *
     * 当前用户获取评论的回复
     * @param username
     * @return
     */
    @GetMapping(value = "/getInfoByCreateuser")
    public Map<String, Object> getInfo(String username){
        return Global.getMap(infoRepository.findByAssessByName(username));
    }
    @PostMapping(value = "/AddInfo")
    public Map<String, Object> AddInfo(@RequestBody Info info) {
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = df.format(day);
        info.setTime(s);
        return Global.getMap(infoRepository.save(info));
    }

    @GetMapping(value = "/DelInfo")
    public Map<String, Object> DelInfo(@RequestParam("id") Integer id){
        infoRepository.delete(id);
        return Global.getMap(null,"删除成功");
    }

}
