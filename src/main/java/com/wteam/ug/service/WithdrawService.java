package com.wteam.ug.service;

import org.springframework.data.domain.Page;

import com.wteam.ug.entity.Bill;
import com.wteam.ug.entity.Withdraw;

public interface WithdrawService {
	boolean add(Withdraw withdraw);

    boolean update(Withdraw withdraw);

    boolean delete(long id);

    Withdraw findById(long id);
    
    Page<Withdraw> findByPage(Integer page, Integer size);
    
    Page<Withdraw> findById(long id, Integer page, Integer size);
}
