package com.wteam.ug.service;

import com.wteam.ug.entity.BusinessScope;

import java.util.List;

public interface BusinessScopeService {
    boolean add(BusinessScope businessScope);

    boolean update(BusinessScope businessScope);

    boolean delete(long id);

    BusinessScope findById(long id);

    List<BusinessScope> findAll();
}
