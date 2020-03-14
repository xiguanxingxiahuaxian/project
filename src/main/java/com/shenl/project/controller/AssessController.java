package com.shenl.project.controller;

import com.shenl.project.bean.Assess;
import com.shenl.project.respository.AssessRepository;
import com.shenl.project.utils.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
public class AssessController {

    @Autowired
    AssessRepository assessRepository;

    @GetMapping(value = "/getAssess")
    public Map<String, Object> getAssess(@RequestParam("goodsId") Integer goodsId) {
        return Global.getMap(assessRepository.findByGoodsId(goodsId));
    }

    @PostMapping(value = "/AddAssess")
    public Map<String, Object> AddAssess(@RequestBody Assess assess) {
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = df.format(day);
        assess.setTime(s);
        return Global.getMap(assessRepository.save(assess));
    }

    @GetMapping(value = "/DelAssess")
    public Map<String, Object> DelAssess(@RequestParam("id") Integer id) {
        assessRepository.delete(id);
        return Global.getMap(null, "删除成功");
    }

}
