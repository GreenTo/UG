package com.wteam.ug.service;

import com.wteam.ug.entity.Customer;
import org.springframework.data.domain.Page;

public interface CustomerService {

    boolean register(Customer customer);
    
    Customer preUpdate(long id);
    
    boolean update(Customer customer);
    
    boolean changePassword(long id, String newPassword, String repeatPassword);
    
    boolean delete(long id);
    
    Customer login(String username, String password);
    
    Page<Customer> findByPage(Integer page, Integer size);

    String verficate(String phone);
}
