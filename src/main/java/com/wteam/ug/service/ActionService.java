package com.wteam.ug.service;

import com.wteam.ug.entity.Action;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ActionService {
    boolean add(Action action);

    boolean update(Action action);

    boolean delete(long id);

    Action findById(long id);

    List<Action> findAll();

    Page<Action> findByPage(Integer page, Integer size);

    Page<Action> search(String param, Integer page, Integer size);
}
