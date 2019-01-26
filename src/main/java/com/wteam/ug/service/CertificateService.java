package com.wteam.ug.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import com.wteam.ug.entity.Certificate;

public interface CertificateService {
	boolean add(Certificate certificate) throws ParseException;
	boolean addInBill(long id);
	boolean addOverr(long id);

    boolean update(Certificate certificate);

    boolean delete(long id);

    Certificate findById(long id);
    
    Page<Certificate> findByDate(Date date, Integer page, Integer size);
    
    List<Certificate> findByDateAndUsername(Date date, String username) ;
    
    //模糊搜索
    Page<Certificate> searchByCertificateId(String param, Integer page, Integer size);
    
    Page<Certificate> searchBySummary(String param, Integer page, Integer size);
    
    Page<Certificate> searchByUserName(String param, Integer page, Integer size);
    
    Page<Certificate> searchAll(String param, Integer page, Integer size);
    
    List<Certificate> searchBySubjectNameAndUsername(String subjectName, String username);
}
