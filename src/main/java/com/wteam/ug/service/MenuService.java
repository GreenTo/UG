package com.wteam.ug.service;

import com.wteam.ug.entity.Menu;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MenuService {
	boolean add(Menu menu);

    List<Menu> findAll();
    
    List<Menu> findByName(String name);

    Menu preUpdate(Integer id);

    boolean update(Menu menu);

    boolean delete(Integer id);

    Page<Menu> findByPage(Integer page, Integer size);

    long findTotal();
    
    //模糊查询
    Page<Menu> search(String param, Integer page, Integer size);
    
}
