package com.shenl.project.utils;

import java.util.HashMap;
import java.util.Map;

public class Global {

    public static final String SUCCESS = "0000";
    public static final String SUCCESSMSG = "请求成功";
    public static final String FAIL = "1111";
    public static final String FAILMSG = "请求失败";


    //获取成功的返回数据
    public static Map<String,Object> getMap(Object obj){
        Map<String, Object> map = new HashMap<>();
        if (obj != null){
            map.put("code",Global.SUCCESS);
            map.put("msg",Global.SUCCESSMSG);
            map.put("data",obj);
        }else{
            map.put("code",Global.FAIL);
            map.put("msg",Global.FAILMSG);
            map.put("data","暂无数据");
        }
        return map;
    }

    //获取成功的返回数据
    public static Map<String,Object> getMap(Object obj,String msg){
        Map<String, Object> map = new HashMap<>();
        if (obj != null){
            map.put("code",Global.SUCCESS);
            map.put("msg",msg);
            map.put("data",obj);
        }else{
            map.put("code",Global.FAIL);
            map.put("msg",msg);
            map.put("data","暂无数据");
        }
        return map;
    }
}
