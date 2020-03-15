package com.shenl.project.controller;

import com.shenl.project.bean.Goods;
import com.shenl.project.respository.GoodsRepository;
import com.shenl.project.utils.CosUtils;
import com.shenl.project.utils.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class GoodsController {

    @Autowired
    GoodsRepository goodsRepository;
    private Map<String, Object> map;

    /**
     * 分类中功能设计
     * 1 查询分类的接口，获取列表
     * 2 然后根据列表的type类型获取goods的分类商品type传值0-7
     *
     * @param classfiy
     * @return
     */
    @GetMapping(value = "/getGoodsListByclassfiy")
    public Map<String, Object> getGoodsListByclassfiy(int classfiy) {
        List<Goods> list = goodsRepository.findByGoodsId(classfiy);
        if (list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                String url = list.get(i).getGoods_url();
                url = CosUtils.GeneratePresignedUrl(url);
                list.get(i).setGoods_url(url);
            }
        }
        return Global.getMap(list);
    }


    @GetMapping(value = "/getGoodsList")
    public Map<String, Object> getGoodsList() {
        List<Goods> list = goodsRepository.findAll();
        if (list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                String url = list.get(i).getGoods_url();
                url = CosUtils.GeneratePresignedUrl(url);
                list.get(i).setGoods_url(url);
            }
        }
        return Global.getMap(list);
    }

    @GetMapping(value = "/getMySelfGoodsList")
    public Map<String, Object> getMySelfGoodsList(@RequestParam("createUserId") String createUserId) {
        List<Goods> list = goodsRepository.findByCreateUserId(createUserId);
        if (list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                String url = list.get(i).getGoods_url();
                url = CosUtils.GeneratePresignedUrl(url);
                list.get(i).setGoods_url(url);
            }
        }
        return Global.getMap(list);
    }

    /*@PostMapping(value = "/AddGoods")
    public Map<String, Object> AddGoods(@RequestParam("createUserId") String createUserId,
                                        @RequestParam("createUserName") String createUserName,
                                        @RequestParam("goodsName") String goodsName,
                                        @RequestParam("goodsDesc") String goodsDesc,
                                        @RequestParam("goodsPrice") String goodsPrice,
                                        @RequestParam("vipPrice") String vipPrice,
                                        @RequestParam("goodsImage") String goodsImage) {
        Goods goods = new Goods();
        goods.setCreateUserId(createUserId);
        goods.setCreateUserName(createUserName);
        goods.setGoodsName(goodsName);
        goods.setGoodsDesc(goodsDesc);
        goods.setGoodsPrice(goodsPrice);
        goods.setVipPrice(vipPrice);
        goods.setGoodsImage(goodsImage);
        return Global.getMap(goodsRepository.save(goods));
    }*/
    @PostMapping(value = "/AddGoods")
    public Map<String, Object> AddGoods(@RequestBody Goods goods) {
        return Global.getMap(goodsRepository.save(goods));
    }


    /**
     *    private String goods_url;
     *     private int goods_num;
     *     private String create_time;
     *     private int goods_classify;
     * @param id
     * @param goodsName
     * @param goodsDesc
     * @param goodsPrice
     * @param vipPrice
     * @param vipPrice
     * @param vipPrice
     * @param vipPrice
     * @param vipPrice
     * @param  goods_classify  0-7 在数据库可以找到数据
     *                          用户下拉选择该所属种类
     * @return
     */
    @PostMapping(value = "/setGoodsInfo")
    public Map<String, Object> setGoodsInfo(@RequestParam("id") Integer id,
                                            @RequestParam("goodsName") String goodsName,
                                            @RequestParam("goodsDesc") String goodsDesc,
                                            @RequestParam("goodsPrice") String goodsPrice,
                                            @RequestParam("vipPrice") String vipPrice,
                                            @RequestParam("create_time") String create_time,
                                            @RequestParam("goods_num") int goods_num,
                                            @RequestParam("create_user_name") String create_user_name,
                                            @RequestParam("goods_classify") int goods_classify,
                                            @RequestParam("file") MultipartFile file) {
        Goods goods = goodsRepository.findOne(id);
        goods.setGoodsName(goodsName);
        goods.setGoodsDesc(goodsDesc);
        goods.setGoodsPrice(goodsPrice);
        goods.setVipPrice(vipPrice);
        goods.setCreate_time(create_time);
        goods.setGoods_classify(goods_classify);
        goods.setGoods_num(goods_num);
        goods.setCreateUserName(create_user_name);


        String key = null;
        key = "/pay/room/" +goods.getCreateUserName()+"/"+file.getOriginalFilename();
        long size=file.getSize();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sf.format(new Date());

        InputStream stream;
        try {
            stream = file.getInputStream();
            String result = CosUtils.upLoadCos(stream, key, size);
            if (result.equals("success")) {
                goods.setGoods_url(key);
                goods.setCreate_time(time);
                map = Global.getMap(goodsRepository.save(goods));
            }else{
                map = new HashMap<>();
                map.put("code",Global.FAIL);
                map.put("msg",Global.FAILMSG);
                map.put("data","暂无数据");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return map;
    }

    @GetMapping(value = "/DelGoods")
    public Map<String, Object> DelGoods(@RequestParam("id") Integer id,
                                        @RequestParam("createUserId") String createUserId) {
        goodsRepository.delete(id,createUserId);
        return Global.getMap(null,"删除成功");
    }
}
