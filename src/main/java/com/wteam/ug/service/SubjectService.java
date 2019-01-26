package com.wteam.ug.service;

import org.springframework.data.domain.Page;

import com.wteam.ug.entity.Subject;

public interface SubjectService {
	boolean add(Subject subject);
	
	Subject preUpdate(Integer id);
	
	boolean update(Subject subject);

	boolean delete(Integer id);
	
	Page<Subject> findByPage(Integer page, Integer size);
	
	Page<Subject> searchByName(String param, Integer page, Integer size);
	
	Page<Subject> searchByCoding(String param, Integer page, Integer size);
	
	Page<Subject> searchAll(String param, Integer page, Integer size);
}
