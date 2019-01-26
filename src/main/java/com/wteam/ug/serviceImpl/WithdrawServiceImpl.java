package com.wteam.ug.serviceImpl;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.wteam.ug.entity.Certificate;
import com.wteam.ug.entity.Withdraw;
import com.wteam.ug.repository.CompanyRepository;
import com.wteam.ug.repository.WithdrawRepository;
import com.wteam.ug.service.CompanyService;
import com.wteam.ug.service.WithdrawService;

@Service
public class WithdrawServiceImpl implements WithdrawService{
	@Autowired
	WithdrawRepository withdrawRepository;
	@Autowired
	CompanyRepository companyRepository;

	@Override
	public boolean add(Withdraw withdraw) {
		boolean b;
		try {
			withdraw.setCreatDate(new Date());
			withdrawRepository.save(withdraw);
			b=true;
		}catch (RuntimeException e) {
			e.printStackTrace();
			b=false;
		}
		return b;
	}

	@Override
	public boolean update(Withdraw withdraw) {
		boolean b;
		try {
			withdrawRepository.save(withdraw);
			b=true;
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
			
			withdrawRepository.deleteById(id);
			b=true;
		}catch (RuntimeException e) {
			e.printStackTrace();
			b=false;
		}
		return b;
	}

	@Override
	public Withdraw findById(long id) {
		Withdraw withdraw = withdrawRepository.findById(id).get();
		return withdraw;
	}

	@Override
	public Page<Withdraw> findByPage(Integer page, Integer size) {
		Pageable pageable = new PageRequest(page-1, size);
        Page<Withdraw> withdraw = withdrawRepository.findAll(pageable);
        return withdraw;
	}

	@Override
	public Page<Withdraw> findById(long id, Integer page, Integer size) {
		Pageable pageable = new PageRequest(page-1, size);
		String username = companyRepository.findById(id).get().getUsername();
		Page<Withdraw> withdraw = withdrawRepository.findAll(new Specification<Withdraw>() {
            @Override
            public Predicate toPredicate(Root<Withdraw> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                    predicate.getExpressions().add(
                            criteriaBuilder.like(
                                    root.get("username").as(String.class), "%" + username.trim() + "%"));
                    return predicate;
            }
        }, pageable);
        return withdraw;
	}
	
}
