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

    /**
     * 根据isplay的状态完成查询
     * 该功能室用户收藏，下单 下单未收获，服务端发货等
     * 信息
     * @param createUserId
     * @param ispay
     * @return
     */
    @GetMapping(value = "/getMySelfOrderListByIspay")
    public Map<String, Object> getMySelfOrderList(@RequestParam("createUserId") String createUserId,@RequestParam("ispay") String ispay) {
        return Global.getMap(orderRepository.findByWithIsplay(createUserId,ispay));
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
        order.setGoodspay("1");
        orderRepository.save(order);
        return Global.getMap(order);
    }

    /**
     * 是否付款0为付款1已经付款2未发货3已经发货4未确认收获5已经确认收获6完成11为收藏
     * @param id
     * @param createUserId
     * @return
     */
    @GetMapping(value = "/BuyOrderWithIsPlayState")
    public Map<String, Object> BuyOrderWithIsPlayState(@RequestParam("id") Integer id,
                                        @RequestParam("createUserId") String createUserId,
                                                       @RequestParam("isPlay") String isPlay) {
        Orders order = orderRepository.findByIdAndCreateUserId(id, createUserId);
        order.setGoodspay(isPlay);
        orderRepository.save(order);
        return Global.getMap(order);
    }
}
