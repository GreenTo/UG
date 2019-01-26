package com.wteam.ug.service;

import com.wteam.ug.entity.Address;

import java.util.List;

public interface AddressService {
    boolean add(Address address);

    boolean update(Address address);

    boolean delete(long id);

    Address findById(long id);

    List<Address> findAll();

    List<Address> findByCustomer(long customerId);
}
