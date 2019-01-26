package com.wteam.ug.serviceImpl;

import com.wteam.ug.entity.Role;
import com.wteam.ug.entity.User;
import com.wteam.ug.repository.RoleRepository;
import com.wteam.ug.repository.UserRepository;
import com.wteam.ug.service.UserService;
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
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Override
    public boolean register(User user) {
        boolean b;
        try {
            user.setCreateDate(new Date());
            Set<Role> roleList = new HashSet<>();
            if (user.getRoles() != null){
                for (Long id : user.getRoles()){
                    Role role = roleRepository.findById(id).get();
                    roleList.add(role);
                }
                user.setRoleSet(roleList);
            }
            userRepository.save(user);
            b = true;
        }catch (RuntimeException e){
            e.printStackTrace();
            b = false;
        }
        return b;
    }

    @Override
    public User login(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username,password);
        return user;
    }

    @Override
    public boolean update(User user) {
        boolean b;
        try {
            User old = userRepository.findById(user.getId()).get();
            Set<Role> roleList = new HashSet<>();
            if (user.getRoles() != null){
                for (Long id : user.getRoles()){
                    Role role = roleRepository.findById(id).get();
                    roleList.add(role);
                }
                user.setRoleSet(roleList);
            }
            user.setPassword(old.getPassword());
            userRepository.save(user);
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
            userRepository.deleteById(id);
            b = true;
        }catch (RuntimeException e){
            e.printStackTrace();
            b = false;
        }
        return b;
    }

    @Override
    public boolean editPassword(long id, String oldPassword, String newPassword) {
//        boolean b;
        User user = userRepository.findById(id).get();
        if (user != null){
            if (user.getPassword().equals(oldPassword.trim())){
                user.setPassword(newPassword.trim());
                userRepository.save(user);
//                    b = true;
                return true;
            }
        }
        return false;
    }

    @Override
    public List<User> findAll() {
        List<User> userList = userRepository.findAll();
        return userList;
    }

    @Override
    public Page<User> findByPage(Integer page, Integer size) {
        Pageable pageable = new PageRequest(page-1,size);
        Page<User> userPage = userRepository.findAll(pageable);
        return userPage;
    }

    @Override
    public Page<User> search(String param, Integer page, Integer size) {

        Pageable pageable = new PageRequest(page-1,size);
        Page<User> userPage = userRepository.findAll(new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                    predicate.getExpressions().add(
                            criteriaBuilder.like(
                                    root.get("username").as(String.class), "%" + param.trim() + "%"));
                    return predicate;
            }
        }, pageable);
        return userPage;
    }

    @Override
    public User findById(long id) {
        User user = userRepository.findById(id).get();
        return user;
    }
}
