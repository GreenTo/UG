package com.wteam.ug.serviceImpl;

import com.wteam.ug.entity.Permission;
import com.wteam.ug.entity.Role;
import com.wteam.ug.repository.PermissionRepository;
import com.wteam.ug.repository.RoleRepository;
import com.wteam.ug.service.RoleService;
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
import java.util.Date;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PermissionRepository permissionRepository;

    @Override
    public boolean add(Role role) {
        boolean b;
        try {
            role.setCreateDate(new Date());
            List<Permission> permissionList = new ArrayList<>();
            if (role.getPermissions() != null){
                for (long id : role.getPermissions()){
                    Permission permission = permissionRepository.findById(id).get();
                    permissionList.add(permission);
                }
                role.setPermissionList(permissionList);
            }
            roleRepository.save(role);
            b = true;
        }catch (RuntimeException e){
            e.printStackTrace();
            b = false;
        }
        return b;
    }

    @Override
    public boolean update(Role role) {
        boolean b;
        try {
            List<Permission> permissionList = new ArrayList<>();
            if (role.getPermissions() != null){
                for (long id : role.getPermissions()){
                    Permission permission = permissionRepository.findById(id).get();
                    permissionList.add(permission);
                }
                role.setPermissionList(permissionList);
            }
            roleRepository.save(role);
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
            roleRepository.deleteById(id);
            b = true;
        }catch (RuntimeException e){
            e.printStackTrace();
            b = false;
        }
        return b;
    }

    @Override
    public Role findById(long id) {
        Role role = roleRepository.findById(id).get();
        return role;
    }

    @Override
    public Page<Role> findByPage(Integer page, Integer size) {
        Pageable pageable = new PageRequest(page-1,size);
        Page<Role> rolePage = roleRepository.findAll(pageable);
        return rolePage;
    }

    @Override
    public List<Role> findAll() {
        List<Role> roleList = roleRepository.findAll();
        return roleList;
    }

    @Override
    public Page<Role> search(String param, Integer page, Integer size) {
        Pageable pageable = new PageRequest(page-1,size);
        Page<Role> rolePage = roleRepository.findAll(new Specification<Role>() {
            @Override
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
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
        return rolePage;
    }
}
