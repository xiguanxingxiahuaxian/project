package com.shenl.project.respository;

import com.shenl.project.bean.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import javax.transaction.Transactional;

@Transactional
public interface ActivityRepository extends JpaRepository<Activity,Integer> {
    @Modifying
    @Query(value = "delete from activity where id=?1",nativeQuery = true)
    void delete(Integer id);
}
