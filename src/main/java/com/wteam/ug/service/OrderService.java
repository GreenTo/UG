package com.wteam.ug.service;

import com.wteam.ug.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface OrderService {

    List<Order> findByCustomerAndStatus(long customerId, long statusId);

    boolean add(Order order, MultipartFile[] files);

    boolean cancel(long id);

    boolean comment(long employeeId, double evaluate, long orderId);

    boolean update(Order order);

    boolean assgin(long orderId, long companyId);

    boolean distribute(long orderId, long employeeId);

    List<Order> findByCustomerId(long id);

    Page<Order> findByFirstCategory(String firstCategory, Integer page, Integer size);

    Page<Order> findByStatus(Integer statusId, Integer page, Integer size);

    Page<Order> findByStatusAndFirstCategoryAndPayment(Integer id, String firstCategory, String payment, Integer page, Integer size);

    Page<Order> search(String param, Integer page, Integer size);

    String pay(long customerId, long orderId);
}
