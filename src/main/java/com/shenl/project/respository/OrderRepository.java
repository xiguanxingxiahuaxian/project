package com.shenl.project.respository;

import com.shenl.project.bean.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface OrderRepository extends JpaRepository<Orders,Integer> {
    List<Orders> findByCreateUserId(String CreateUserId);

    @Modifying
    @Query(value = "delete from orders where id=?1 and create_user_id=?2",nativeQuery = true)
    void delete(Integer id, String CreateUserId);
    Orders findByIdAndCreateUserId(Integer id, String CreateUserId);
}
