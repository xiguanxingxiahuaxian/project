package com.shenl.project.respository;

import com.shenl.project.bean.Info;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

@Transactional
public interface InfoRepository extends JpaRepository<Info,Integer> {
    @Modifying
    @Query(value = "delete from info where id=?1",nativeQuery = true)
    void delete(Integer id);
}
