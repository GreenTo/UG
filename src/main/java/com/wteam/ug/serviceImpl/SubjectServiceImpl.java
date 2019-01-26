package com.wteam.ug.serviceImpl;

import com.wteam.ug.entity.Subject;
import com.wteam.ug.repository.SubjectRepository;
import com.wteam.ug.service.SubjectService;
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

@Service
public class SubjectServiceImpl implements SubjectService{

	@Autowired
	SubjectRepository subjectRepository;
	
	@Override
	public boolean add(Subject subject) {
		boolean b;
		try {
			Integer directionId = subject.getDirectionId();
			Integer generalCategoryId = subject.getGeneralCategoryId();			
			if(directionId == 1) {
				subject.setDirection("借");
			}else if(directionId == -1) {
				subject.setDirection("贷");
			}else {
				b=false;
				return b;
			}
			if(generalCategoryId == 1) {
				subject.setGeneralCategory("流动资产");
			}else if(generalCategoryId == -1) {
				subject.setGeneralCategory("固定资产");
			}else {
				b=false;
				return b;
			}
            subjectRepository.save(subject);
            b=true;
        }catch (RuntimeException e){
            e.printStackTrace();
            b=false;
        }
        return b;
	}

	@Override
	public Subject preUpdate(Integer id) {
		Subject subject=subjectRepository.findById(id).get();
    	return subject;
	}

	@Override
	public boolean update(Subject subject) {
		boolean b;
        try {
        	Integer directionId = subject.getDirectionId();
        	Integer generalCategoryId = subject.getGeneralCategoryId();
        	if(directionId == 1) {
				subject.setDirection("借");
			}else if(directionId == -1) {
				subject.setDirection("贷");
			}else {
				b=false;
				return b;
			}
			if(generalCategoryId == 1) {
				subject.setGeneralCategory("流动资产");
			}else if(generalCategoryId == -1) {
				subject.setDirection("固定资产");
			}else {
				b=false;
				return b;
			}
            subjectRepository.save(subject);
            b=true;
        }catch (RuntimeException e){
            e.printStackTrace();
            b=false;
        }
        return b;
	}

	@Override
	public boolean delete(Integer id) {
		boolean b;
    	try {
    		subjectRepository.deleteById(id);
    		b=true;
    	}catch(RuntimeException e) {
    		e.printStackTrace();
    		b=false;
    	}
    	return b;
	}

	@Override
	public Page<Subject> findByPage(Integer page, Integer size) {
		Pageable pageable = new PageRequest(page-1, size);
        Page<Subject> subjectPage = subjectRepository.findAll(pageable);
        return subjectPage;
	}

	@Override
    public Page<Subject> searchByName(String param, Integer page, Integer size) {
        Pageable pageable = new PageRequest(page-1,size);
        Page<Subject> subjectPage = subjectRepository.findAll(new Specification<Subject>() {
            @Override
            public Predicate toPredicate(Root<Subject> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                predicate.getExpressions().add(
                        criteriaBuilder.like(
//                                root从数据表中获得名为"name"的属性,它是string类型的,
                                root.get("name").as(String.class), "%" + param.trim() + "%"
                        )
                );
                return predicate;
            }
        }, pageable);
        return subjectPage;
    }

	@Override
    public Page<Subject> searchByCoding(String param, Integer page, Integer size) {
        Pageable pageable = new PageRequest(page-1,size);
        Page<Subject> subjectPage = subjectRepository.findAll(new Specification<Subject>() {
            @Override
            public Predicate toPredicate(Root<Subject> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                predicate.getExpressions().add(
                        criteriaBuilder.like(
//                                root从数据表中获得名为"coding"的属性,它是string类型的,
                                root.get("coding").as(String.class), "%" + param.trim() + "%"
                        )
                );
                return predicate;
            }
        }, pageable);
        return subjectPage;
    }
	
	@Override
    public Page<Subject> searchAll(String param, Integer page, Integer size) {
        Pageable pageable = new PageRequest(page-1,size);
        Page<Subject> subjectPage = subjectRepository.findAll(new Specification<Subject>() {
            @Override
            public Predicate toPredicate(Root<Subject> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
            	 Predicate p1 = criteriaBuilder.like(root.get("coding").as(String.class), "%" + param.trim() + "%");
                 Predicate p2 = criteriaBuilder.like(root.get("name").as(String.class), "%" + param.trim() + "%");
                 criteriaQuery.where(criteriaBuilder.or(p1, p2));
                 return criteriaQuery.getRestriction();
            }
        }, pageable);
        return subjectPage;
    }
}
