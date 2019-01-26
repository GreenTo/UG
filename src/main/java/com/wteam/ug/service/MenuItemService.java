package com.wteam.ug.service;

import com.wteam.ug.entity.MenuItem;

import java.util.List;

public interface MenuItemService {
	boolean add(MenuItem menuItem);
	
	MenuItem findById(Integer id);

    MenuItem preUpdate(Integer id);

    boolean update(MenuItem menuItem);

    boolean delete(Integer id);

    long findTotal();
    
    List<MenuItem> findAllMenuItem(Integer id);
}
