package com.wteam.ug.serviceImpl;

import com.wteam.ug.entity.Customer;
import com.wteam.ug.repository.CustomerRepository;
import com.wteam.ug.service.CustomerService;
import com.wteam.ug.util.SMSSendUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public boolean register(Customer customer) {
        boolean b = false;
        try {
        	customer.setCreateDate(new Date());
        	customerRepository.save(customer);
        	b=true;
        }catch (RuntimeException e){
        	e.printStackTrace();
        }
    return b;
    }

	@Override
	public String verficate(String phone) {
		String vcode = SMSSendUtil.createRandomVcode();
		String[] str = new String[]{vcode,"3"};
		boolean b = SMSSendUtil.sendSms(phone, true, str);
		if (b)
			return vcode;
		else
			return null;
	}

	@Override
	public Customer preUpdate(long id) {
		Customer customer=customerRepository.findById(id).get();
    	return customer;
	}

	@Override
	public boolean update(Customer customer) {
		boolean b = false;
		try {
			Customer newCustomer = customerRepository.findById(customer.getId()).get();
			newCustomer.setUsername(customer.getUsername());
			newCustomer.setPhone(customer.getPhone());
			customerRepository.save(newCustomer);
			b=true;
		}catch (RuntimeException e) {
			e.printStackTrace();
		}
		return b;
	}
    
    @Override
    public boolean changePassword(long id,String oldPassword,String newPassword) {
    	boolean b = false;
    	try {
    		Customer customer = customerRepository.findById(id).get();
    		String password = customer.getPassword();
    		if(oldPassword.equals(password)) {
    			customer.setPassword(newPassword);
    			customerRepository.save(customer);
    			b=true;
    		}else
    			b=false;
    	}catch (RuntimeException e) {
    		e.printStackTrace();
			b=false;
		}
    	return b;
    }
    
    @Override
    public boolean delete(long id) {
    	boolean b;
    	try {
    		customerRepository.deleteById(id);
    		b=true;
    	}catch (RuntimeException e) {
			e.printStackTrace();
			b=false;
		}
    	return b;
    }
    
    @Override
    public Customer login(String username, String password) {
		Customer customer = customerRepository.findByUsernameAndPassword(username, password);
		return customer;
    }
    
    @Override
    public Page<Customer> findByPage(Integer page, Integer size){
    	Pageable pageable = new PageRequest(page-1, size);
        Page<Customer> customerPage = customerRepository.findAll(pageable);
        return customerPage;
    }
}
