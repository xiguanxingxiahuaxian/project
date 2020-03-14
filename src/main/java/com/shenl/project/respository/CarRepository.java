package com.shenl.project.respository;

import com.shenl.project.bean.Car;
import com.shenl.project.bean.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface CarRepository extends JpaRepository<Car,Integer> {

    void deleteByUserIdAndGoodsId(String userId, String goodsId);

}
