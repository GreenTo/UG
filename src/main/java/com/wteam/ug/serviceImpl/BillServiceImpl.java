package com.wteam.ug.serviceImpl;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.wteam.ug.entity.Bill;
import com.wteam.ug.entity.Certificate;
import com.wteam.ug.repository.BillRepository;
import com.wteam.ug.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service("bill")
public class BillServiceImpl implements BillService{
	@Autowired
	BillRepository billRepository;

	@Override
	public boolean add(Bill bill) {
		boolean b;
		try {
			billRepository.save(bill);
			b=true;
		}catch (RuntimeException e) {
			e.printStackTrace();
			b=false;
		}
		return b;
	}

	@Override
	public boolean update(Bill bill) {
		boolean b;
		try {
			billRepository.save(bill);
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

			billRepository.deleteById(id);
			b=true;
		}catch (RuntimeException e) {
			e.printStackTrace();
			b=false;
		}
		return b;
	}

	@Override
	public Bill findById(long id) {
		Bill bill = billRepository.findById(id).get();
		return bill;
	}

	@Override
	public Page<Bill> searchByUsername(String param, Integer page, Integer size) {
		Pageable pageable = new PageRequest(page-1,size);
		Page<Bill> bills = billRepository.findAll(new Specification<Bill>() {
			@Override
			public Predicate toPredicate(Root<Bill> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
				Predicate predicate = criteriaBuilder.conjunction();
				predicate.getExpressions().add(
						criteriaBuilder.like(
								root.get("username").as(String.class), "%" + param.trim() + "%"));
				return predicate;
			}
		}, pageable);
		return bills;
	}

	@Override
	public List<List<String>> findByCurrentAndFixed(String username) {
		Bill bill = billRepository.findByUsername(username);
		List<Certificate> certificates = bill.getCertificateSet();
		List<String> currentList =new ArrayList<String>();
		List<String> fixedList =new ArrayList<String>();
		for(Certificate certificate:certificates) {
			if(certificate.getGeneralCategory() == "流动资产" & !currentList.contains(certificate.getSubjectName())) {
				currentList.add(certificate.getSubjectName());
			}else if(!fixedList.contains(certificate.getSubjectName())){
				fixedList.add(certificate.getSubjectName());
			}
		}
		List<List<String>> list = new ArrayList<>();
		list.add(currentList);
		list.add(fixedList);
		return list;
	}

	@Override
	public boolean downLoad(List<Certificate> certificates , String path) {
		boolean b = true;
		try{
			// 通过工具类创建writer
			ExcelWriter writer = ExcelUtil.getWriter(path);
			// 合并单元格后的标题行，使用默认标题样式
			writer.merge(2+certificates.size(), "个人明细账");
			// 一次性写出内容，使用默认样式
			writer.write(certificates);
			// 关闭writer，释放内存
			writer.close();
		}catch (RuntimeException e) {
			e.printStackTrace();
			b=false;
		}
		return b;
	}

}
