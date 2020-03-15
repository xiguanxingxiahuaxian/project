package com.shenl.project.respository;

import com.shenl.project.bean.Assess;
import com.shenl.project.bean.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface GoodsRepository extends JpaRepository<Goods,Integer> {

    List<Goods> findByCreateUserId(String CreateUserId);

    @Modifying
    @Query(value = "delete from goods where id=?1 and create_user_id=?2",nativeQuery = true)
    void delete(Integer id, String CreateUserId);

    @Query(value = "select * from goods where id in (select goods_id from car where user_id=?1)",nativeQuery = true)
    List<Goods> getCar(Integer userId);

    @Query(value = "select * from goods where goods_classify=?1",nativeQuery = true)
    List<Goods> findByGoodsId(Integer goodsId);
}
