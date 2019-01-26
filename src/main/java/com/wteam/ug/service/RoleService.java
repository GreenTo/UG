package com.wteam.ug.service;

import com.wteam.ug.entity.Role;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RoleService {
    boolean add(Role role);

    boolean update(Role role);

    boolean delete(long id);

    Role findById(long id);

    Page<Role> findByPage(Integer page, Integer size);

    List<Role> findAll();

    Page<Role> search(String param, Integer page, Integer size);
}
