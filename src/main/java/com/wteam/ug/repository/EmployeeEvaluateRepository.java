package com.wteam.ug.repository;

import com.wteam.ug.entity.EmployeeEvaluate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeEvaluateRepository extends JpaRepository<EmployeeEvaluate,Long> {

    List<EmployeeEvaluate> findByEmployee_Id(long id);
}
