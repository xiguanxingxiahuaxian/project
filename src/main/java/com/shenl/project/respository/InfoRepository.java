package com.shenl.project.respository;

import com.shenl.project.bean.Goods;
import com.shenl.project.bean.Info;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface InfoRepository extends JpaRepository<Info,Integer> {
    @Modifying
    @Query(value = "delete from info where id=?1",nativeQuery = true)
    void delete(Integer id);


    @Query(value = "select * from Info where create_user_name=?1",nativeQuery = true)
    List<Info> findByAssessByName(String  name);
}
