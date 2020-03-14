package com.shenl.project.respository;

import com.shenl.project.bean.Assess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface AssessRepository extends JpaRepository<Assess,Integer> {

    @Query(value = "select * from assess where goods_id=?1",nativeQuery = true)
    List<Assess> findByGoodsId(Integer goodsId);

    @Modifying
    @Query(value = "delete from assess where id=?1",nativeQuery = true)
    void delete(Integer id);
}
