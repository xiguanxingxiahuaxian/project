package com.shenl.project.controller;

import com.shenl.project.bean.Goods;
import com.shenl.project.respository.GoodsRepository;
import com.shenl.project.utils.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class GoodsController {

    @Autowired
    GoodsRepository goodsRepository;

    @GetMapping(value = "/getGoodsList")
    public Map<String, Object> getGoodsList() {
        return Global.getMap(goodsRepository.findAll());
    }

    @GetMapping(value = "/getMySelfGoodsList")
    public Map<String, Object> getMySelfGoodsList(@RequestParam("createUserId") String createUserId) {
        return Global.getMap(goodsRepository.findByCreateUserId(createUserId));
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


    @PostMapping(value = "/setGoodsInfo")
    public Map<String, Object> setGoodsInfo(@RequestParam("id") Integer id,
                                            @RequestParam("goodsName") String goodsName,
                                            @RequestParam("goodsDesc") String goodsDesc,
                                            @RequestParam("goodsPrice") String goodsPrice,
                                            @RequestParam("vipPrice") String vipPrice,
                                            @RequestParam("goodsImage") String goodsImage) {
        Goods goods = goodsRepository.findOne(id);
        goods.setGoodsName(goodsName);
        goods.setGoodsDesc(goodsDesc);
        goods.setGoodsPrice(goodsPrice);
        goods.setVipPrice(vipPrice);
        goods.setGoodsImage(goodsImage);
        return Global.getMap(goodsRepository.save(goods));
    }

    @GetMapping(value = "/DelGoods")
    public Map<String, Object> DelGoods(@RequestParam("id") Integer id,
                                        @RequestParam("createUserId") String createUserId) {
        goodsRepository.delete(id,createUserId);
        return Global.getMap(null,"删除成功");
    }
}
