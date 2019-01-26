package com.wteam.ug.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.wteam.ug.entity.Bill;

@Repository
public interface BillRepository extends JpaRepository<Bill,Long>, JpaSpecificationExecutor<Bill>{

	Bill findByUsername(String username);

}
