package com.wteam.ug.serviceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.wteam.ug.entity.Bill;
import com.wteam.ug.entity.Certificate;
import com.wteam.ug.entity.Subject;
import com.wteam.ug.entity.User;
import com.wteam.ug.repository.BillRepository;
import com.wteam.ug.repository.CertificateRepository;
import com.wteam.ug.repository.SubjectRepository;
import com.wteam.ug.repository.UserRepository;
import com.wteam.ug.service.CertificateService;
@Service
public class CertificateServiceImpl implements CertificateService{

	@Autowired
	CertificateRepository certificateRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	SubjectRepository subjectRepository;
	@Autowired
	BillRepository billRepository;
	
	@Override
	public boolean add(Certificate certificate) throws ParseException {
		boolean b;
		try {
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	        
	        Date date = formatter.parse(formatter.format(new Date()));
			certificate.setCreatDate(date);
			
			Long userId = certificate.getUserId();
			User user = userRepository.findById(userId).get();
			String username = user.getUsername();
			certificate.setUsername(username);
			
			Subject subject = subjectRepository.findById(certificate.getSubjectId()).get();
			certificate.setGeneralCategory(subject.getGeneralCategory());
			certificate.setSubjectName(subject.getName());
			
            Bill bill=billRepository.findByUsername(username);
            
			certificate.setBill(bill);
			
            certificateRepository.save(certificate);
			b=true;
		}catch (RuntimeException  e) {
			e.printStackTrace();
			b=false;
		}
		return b;
	}
	
	@Override
	public boolean addInBill(long id) {
		boolean b;
		try {
			Certificate certificate = certificateRepository.findById(id).get();
			Bill bill = billRepository.findByUsername(certificate.getUsername());
			if(bill == null) {
				Bill newBill = new Bill();
				newBill.setUsername(certificate.getUsername());
				double overr = certificate.getDebitAmount() - certificate.getCreditAmount();
				newBill.setOverr(overr);
				List<Certificate> certificates = new ArrayList<Certificate>();
				certificates.add(certificate);
				newBill.setCertificateSet(certificates);
				billRepository.save(newBill);
			}else {
				double overr = bill.getOverr() + certificate.getDebitAmount() - certificate.getCreditAmount();
				bill.setOverr(overr);
				List<Certificate> certificates = bill.getCertificateSet();
				certificates.add(certificate);
				bill.setCertificateSet(certificates);
				billRepository.save(bill);
			}
			b=true;
		}catch (RuntimeException e) {
			e.printStackTrace();
			b=false;
		}
		return b;
	}
	
	@Override
	public boolean addOverr(long id) {
		boolean b;
		try {
			Certificate certificate = certificateRepository.findById(id).get();
			Bill bill = billRepository.findByUsername(certificate.getUsername());
			certificate.setOverr(bill.getOverr());
			certificateRepository.save(certificate);
			b=true;
		}catch (RuntimeException e) {
			e.printStackTrace();
			b=false;
		}
		return b;
	}

	@Override
	public boolean update(Certificate certificate) {
		boolean b;
		try {
			certificateRepository.save(certificate);
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
			certificateRepository.deleteById(id);
			b=true;
		}catch (RuntimeException e) {
			e.printStackTrace();
			b=false;
		}
		return b;
	}

	@Override
	public Certificate findById(long id) {
		Certificate certificate = certificateRepository.findById(id).get();
		return certificate;
	}

	@Override
	public Page<Certificate> findByDate(Date date, Integer page, Integer size) {
		Pageable pageable = new PageRequest(page-1,size);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        String formattedDate = formatter.format(date);
		Page<Certificate> certificates = certificateRepository.findAll(new Specification<Certificate>() {
            @Override
            public Predicate toPredicate(Root<Certificate> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                    predicate.getExpressions().add(
                            criteriaBuilder.like(
                                    root.get("creatDate").as(String.class), "%" + formattedDate.trim() + "%"));
                    return predicate;
            }
        }, pageable);
		return certificates;
	}

	@Override
	public Page<Certificate> searchByCertificateId(String param, Integer page, Integer size) {
		Pageable pageable = new PageRequest(page-1,size);
        Page<Certificate> certificates = certificateRepository.findAll(new Specification<Certificate>() {
            @Override
            public Predicate toPredicate(Root<Certificate> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                    predicate.getExpressions().add(
                            criteriaBuilder.like(
                                    root.get("certificateId").as(String.class), "%" + param.trim() + "%"));
                    return predicate;
            }
        }, pageable);
		return certificates;
	}

	@Override
	public Page<Certificate> searchBySummary(String param, Integer page, Integer size) {
		Pageable pageable = new PageRequest(page-1,size);
        Page<Certificate> certificates = certificateRepository.findAll(new Specification<Certificate>() {
            @Override
            public Predicate toPredicate(Root<Certificate> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                    predicate.getExpressions().add(
                            criteriaBuilder.like(
                                    root.get("summary").as(String.class), "%" + param.trim() + "%"));
                    return predicate;
            }
        }, pageable);
		return certificates;
	}

	@Override
	public Page<Certificate> searchByUserName(String param, Integer page, Integer size) {
		Pageable pageable = new PageRequest(page-1,size);
        Page<Certificate> certificates = certificateRepository.findAll(new Specification<Certificate>() {
            @Override
            public Predicate toPredicate(Root<Certificate> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                    predicate.getExpressions().add(
                            criteriaBuilder.like(
                                    root.get("username").as(String.class), "%" + param.trim() + "%"));
                    return predicate;
            }
        }, pageable);
		return certificates;
	}

	@Override
	public Page<Certificate> searchAll(String param, Integer page, Integer size) {
		Pageable pageable = new PageRequest(page-1,size);
        Page<Certificate> certificates = certificateRepository.findAll(new Specification<Certificate>() {
            @Override
            public Predicate toPredicate(Root<Certificate> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
            	 Predicate p1 = criteriaBuilder.like(root.get("certificateId").as(String.class), "%" + param.trim() + "%");
                 Predicate p2 = criteriaBuilder.like(root.get("username").as(String.class), "%" + param.trim() + "%");
                 Predicate p3 = criteriaBuilder.like(root.get("summary").as(String.class), "%" + param.trim() + "%");
                 criteriaQuery.where(criteriaBuilder.or(p1, p2, p3));
                 return criteriaQuery.getRestriction();
            }
        }, pageable);
        return certificates;
	}

	@Override
	public List<Certificate> searchBySubjectNameAndUsername(String subjectName, String username) {
		
        List<Certificate> certificates = certificateRepository.findAll(new Specification<Certificate>() {
            @Override
            public Predicate toPredicate(Root<Certificate> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
            	 Predicate p1 = criteriaBuilder.like(root.get("subjectName").as(String.class), "%" + subjectName.trim() + "%");
                 Predicate p2 = criteriaBuilder.equal(root.get("username").as(String.class),   username.trim() );
                 criteriaQuery.where(criteriaBuilder.and(p1, p2));
                 return criteriaQuery.getRestriction();
            }
        });
        return certificates;
	}

	@Override
	public List<Certificate> findByDateAndUsername(Date date, String username)  {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfmat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String formattedDate = formatter.format(date); 
		List<Certificate> certificates = certificateRepository.findAll(new Specification<Certificate>() {
	            @Override
	            public Predicate toPredicate(Root<Certificate> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
	            	 Predicate p1 = null;
					
						try {
							p1 = criteriaBuilder.between(root.get("creatDate"),  sdfmat.parse(sdfmat.format(date.getTime())),
							            													sdfmat.parse(sdfmat.format(date.getTime()) + 86400000));
							Predicate p2 = criteriaBuilder.equal(root.get("username").as(String.class), username.trim() );
							criteriaQuery.where(criteriaBuilder.and(p1, p2));
						} catch (ParseException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						}
					
	                 return criteriaQuery.getRestriction();
	            }
	        });
	        return certificates;
	}


}
