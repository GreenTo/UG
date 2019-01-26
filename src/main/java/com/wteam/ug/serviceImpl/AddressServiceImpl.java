package com.wteam.ug.serviceImpl;

import com.wteam.ug.entity.Address;
import com.wteam.ug.entity.Customer;
import com.wteam.ug.repository.AddressRepository;
import com.wteam.ug.repository.CustomerRepository;
import com.wteam.ug.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressRepository addressRepository;
    @Autowired
    CustomerRepository customerRepository;

    @Override
    public boolean add(Address address) {
        boolean b;
        try {
            Customer customer = customerRepository.findById(address.getCustomerId()).get();
            address.setCustomer(customer);
            addressRepository.save(address);
            b = true;
        }catch (RuntimeException e){
            e.printStackTrace();
            b = false;
        }
        return b;
    }

    @Override
    public boolean update(Address address) {
        boolean b;
        try {
            Customer customer = customerRepository.findById(address.getCustomerId()).get();
            address.setCustomer(customer);
            addressRepository.save(address);
            b = true;
        }catch (RuntimeException e){
            e.printStackTrace();
            b = false;
        }
        return b;
    }

    @Override
    public boolean delete(long id) {
        boolean b;
        try {
            addressRepository.deleteById(id);
            b = true;
        }catch (RuntimeException e){
            e.printStackTrace();
            b = false;
        }
        return b;
    }

    @Override
    public Address findById(long id) {
        Address address = addressRepository.findById(id).get();
        return address;
    }

    @Override
    public List<Address> findAll() {
        List<Address> addressList = addressRepository.findAll();
        return addressList;
    }

    @Override
    public List<Address> findByCustomer(long customerId) {
        List<Address> addressList = addressRepository.findByCustomer_Id(customerId);
        return addressList;
    }
}
