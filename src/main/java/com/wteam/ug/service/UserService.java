package com.wteam.ug.service;

import com.wteam.ug.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    boolean register(User user);

    User login(String username, String password);

    boolean update(User user);

    boolean delete(long id);

    List<User> findAll();

    Page<User> findByPage(Integer page, Integer size);

    Page<User> search(String param, Integer page, Integer size);

    User findById(long id);

    boolean editPassword(long id, String oldPassword, String newPassword);
}
