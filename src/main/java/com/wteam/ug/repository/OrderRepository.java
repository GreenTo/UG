package com.wteam.ug.repository;

import com.wteam.ug.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long>, JpaSpecificationExecutor<Order> {

    List<Order> findByCustomer_Id(long id);

    List<Order> findByCustomer_IdAndOrderStatus_Id(long customerId,long statusId);
}
