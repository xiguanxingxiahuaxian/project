package com.shenl.project.respository;

import com.shenl.project.bean.Goods;
import com.shenl.project.bean.SelectCore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface SelectCoreRepository extends JpaRepository<SelectCore,Integer> {

  /*  List<Goods> findByCreateUserId(String CreateUserId);
    @Modifying
    @Query(value = "delete from goods where id=?1 and create_user_id=?2",nativeQuery = true)
    void delete(Integer id, String CreateUserId);

    @Query(value = "select * from goods where id in (select goods_id from car where user_id=?1)",nativeQuery = true)
    List<Goods> getCar(Integer userId);*/

    /**
     * 管理员用户可以对当前的首页内容
     * 进行维护
     * 提供增加 删除  查询的功能
     */
    @Override
    List<SelectCore> findAll();

    @Override
    void delete(SelectCore selectCore);

    @Override
    <S extends SelectCore> S save(S s);

    @Override
    SelectCore findOne(Integer integer);
}
