package com.shenl.project.controller;

import com.shenl.project.bean.Orders;
import com.shenl.project.respository.OrderRepository;
import com.shenl.project.utils.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class OrderController {

    @Autowired
    OrderRepository orderRepository;

    @GetMapping(value = "/getMySelfOrderList")
    public Map<String, Object> getMySelfOrderList(@RequestParam("createUserId") String createUserId) {
        return Global.getMap(orderRepository.findByCreateUserId(createUserId));
    }

    @PostMapping(value = "/AddOrder")
    public Map<String, Object> AddOrder(@RequestParam("createUserId") String createUserId,
                                        @RequestParam("createUserName") String createUserName,
                                        @RequestParam("goodsName") String goodsName,
                                        @RequestParam("goodsDesc") String goodsDesc,
                                        @RequestParam("goodsPrice") String goodsPrice,
                                        @RequestParam("vipPrice") String vipPrice,
                                        @RequestParam("goodsImage") String goodsImage,
                                        @RequestParam("number") String number) {
        Orders order = new Orders();
        order.setCreateUserId(createUserId);
        order.setCreateUserName(createUserName);
        order.setGoodsName(goodsName);
        order.setGoodsDesc(goodsDesc);
        order.setGoodsPrice(goodsPrice);
        order.setVipPrice(vipPrice);
        order.setGoodsImage(goodsImage);
        order.setNumber(number);
        return Global.getMap(orderRepository.save(order));
    }


    @GetMapping(value = "/DelOrder")
    public Map<String, Object> DelOrder(@RequestParam("id") Integer id,
                                        @RequestParam("createUserId") String createUserId) {
        orderRepository.delete(id,createUserId);
        return Global.getMap(null,"删除成功");
    }

    @GetMapping(value = "/BuyOrder")
    public Map<String, Object> BuyOrder(@RequestParam("id") Integer id,
                                        @RequestParam("createUserId") String createUserId) {
        Orders order = orderRepository.findByIdAndCreateUserId(id, createUserId);
        order.setIsPay("1");
        orderRepository.save(order);
        return Global.getMap(order);
    }
}
