package com.wteam.ug.repository;

import com.wteam.ug.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer>,JpaSpecificationExecutor<Menu>{

	List<Menu> findByName(String name);

	Page<Menu> findAllByName(Pageable pageable);

}
