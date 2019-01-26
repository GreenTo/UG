package com.wteam.ug.serviceImpl;

import com.wteam.ug.entity.Action;
import com.wteam.ug.entity.Permission;
import com.wteam.ug.repository.ActionRepository;
import com.wteam.ug.repository.PermissionRepository;
import com.wteam.ug.service.ActionService;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ActionServiceImpl implements ActionService {

    @Autowired
    ActionRepository actionRepository;
    @Autowired
    PermissionRepository permissionRepository;

    @Override
    public boolean add(Action action) {
        boolean b;
        try {
            action.setCreateDate(new Date());
            Set<Permission> permissionSet = new HashSet<>();
            if (action.getPermissions() != null){
                for (long id : action.getPermissions()){
                    Permission permission = permissionRepository.findById(id).get();
                    permissionSet.add(permission);
                }
                action.setPermissionSet(permissionSet);
            }
            actionRepository.save(action);
            b = true;
        }catch (RuntimeException e){
            e.printStackTrace();
            b = false;
        }
        return b;
    }

    @Override
    public boolean update(Action action) {
        boolean b;
        try {
            Set<Permission> permissionSet = new HashSet<>();
            if (action.getPermissions() != null){
                for (long id : action.getPermissions()){
                    Permission permission = permissionRepository.findById(id).get();
                    permissionSet.add(permission);
                }
                action.setPermissionSet(permissionSet);
            }
            actionRepository.save(action);
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
            actionRepository.deleteById(id);
            b = true;
        }catch (RuntimeException e){
            e.printStackTrace();
            b = false;
        }
        return b;
    }

    @Override
    public Action findById(long id) {
        Action action = actionRepository.findById(id).get();
        return action;
    }

    @Override
    public List<Action> findAll() {
        List<Action> actionList = actionRepository.findAll();
        return actionList;
    }

    @Override
    public Page<Action> findByPage(Integer page, Integer size) {
        Pageable pageable = new PageRequest(page-1,size);
        Page<Action> actionPage = actionRepository.findAll(pageable);
        return actionPage;
    }

    @Override
    public Page<Action> search(String param, Integer page, Integer size) {
        Pageable pageable = new PageRequest(page-1,size);
        Page<Action> actionPage = actionRepository.findAll(new Specification<Action>() {
            @Override
            public Predicate toPredicate(Root<Action> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                predicate.getExpressions().add(
                        criteriaBuilder.like(
                                root.get("name").as(String.class), "%" + param.trim() + "%"
                        )
                );
                return predicate;
            }
        }, pageable);
        return actionPage;
    }
}
