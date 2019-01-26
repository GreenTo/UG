package com.wteam.ug.service;

import com.wteam.ug.entity.Bill;
import com.wteam.ug.entity.Certificate;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BillService {
	boolean add(Bill bill);

    boolean update(Bill bill);

    boolean delete(long id);

    Bill findById(long id);

    List<List<String>> findByCurrentAndFixed(String username);
    
    //模糊搜索
    Page<Bill> searchByUsername(String param, Integer page, Integer size);
    
    boolean downLoad(List<Certificate> certificates, String path);
}
