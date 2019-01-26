package com.wteam.ug.repository;

import com.wteam.ug.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    Customer findByUsernameAndPassword(String username,String password);
}
