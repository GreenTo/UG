package com.wteam.ug.serviceImpl;

import com.wteam.ug.entity.Permission;
import com.wteam.ug.repository.PermissionRepository;
import com.wteam.ug.service.PermissionService;
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
import java.util.Date;
import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    PermissionRepository permissionRepository;

    @Override
    public boolean add(Permission permission) {
        boolean b;
        try {
            permission.setCreateDate(new Date());
            permissionRepository.save(permission);
            b = true;
        }catch (RuntimeException e){
            e.printStackTrace();
            b = false;
        }
        return b;
    }

    @Override
    public boolean update(Permission permission) {
        boolean b;
        try {
            permissionRepository.save(permission);
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
            permissionRepository.deleteById(id);
            b = true;
        }catch (RuntimeException e){
            e.printStackTrace();
            b = false;
        }
        return b;
    }

    @Override
    public Permission findById(long id) {
        Permission permission = permissionRepository.findById(id).get();
        return permission;
    }

    @Override
    public List<Permission> findAll() {
        List<Permission> permissionList = permissionRepository.findAll();
        return permissionList;
    }

    @Override
    public Page<Permission> findByPage(Integer page, Integer size) {
        Pageable pageable = new PageRequest(page - 1, size);
        Page<Permission> permissionPage = permissionRepository.findAll(pageable);
        return permissionPage;
    }

    @Override
    public Page<Permission> search(String param, Integer page, Integer size) {
        Pageable pageable = new PageRequest(page - 1, size);
        Page<Permission> permissionPage = permissionRepository.findAll(new Specification<Permission>() {
            @Override
            public Predicate toPredicate(Root<Permission> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                predicate.getExpressions().add(
                        criteriaBuilder.like(
                                root.get("name").as(String.class), "%" + param.trim() + "%"
                        )
                );
                return predicate;
            }
        }, pageable);
        return permissionPage;
    }

    @Override
    public Page<Permission> findByName(String name, Integer page, Integer size) {
        Pageable pageable = new PageRequest(page-1,size);
        Page<Permission> permissionPage = permissionRepository.findByName(name, pageable);
        return permissionPage;
    }
}
