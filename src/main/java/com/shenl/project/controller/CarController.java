package com.shenl.project.controller;

import com.shenl.project.bean.Car;
import com.shenl.project.respository.CarRepository;
import com.shenl.project.respository.GoodsRepository;
import com.shenl.project.utils.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CarController {

    @Autowired
    CarRepository carRepository;

    @Autowired
    GoodsRepository goodsRepository;

    @GetMapping(value = "/getCar")
    public Map<String, Object> getCar(@RequestParam("userId") Integer userId){
        return Global.getMap(goodsRepository.getCar(userId));
    }


    @GetMapping(value = "/AddCar")
    public Map<String, Object> AddCar(@RequestParam("userId") String userId,
                                      @RequestParam("goodsId") Integer goodsId) {
        Car car = new Car();
        car.setUserId(userId);
        car.setGoodsId(goodsId);
        return Global.getMap(carRepository.save(car));
    }


    @GetMapping(value = "/DelCar")
    public Map<String, Object> DelCar(@RequestParam("userId") String userId,
                                      @RequestParam("goodsId") String goodsId) {
        carRepository.deleteByUserIdAndGoodsId(userId, goodsId);
        return Global.getMap(null, "删除成功");
    }
}
