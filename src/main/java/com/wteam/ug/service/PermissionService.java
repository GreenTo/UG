package com.wteam.ug.service;

import com.wteam.ug.entity.Permission;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PermissionService {
    boolean add(Permission permission);

    boolean update(Permission permission);

    boolean delete(long id);

    Permission findById(long id);

    List<Permission> findAll();

    Page<Permission> findByPage(Integer page, Integer size);

    Page<Permission> search(String param, Integer page, Integer size);

    Page<Permission> findByName(String name, Integer page, Integer size);
}
