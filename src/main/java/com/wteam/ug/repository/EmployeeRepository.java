package com.wteam.ug.repository;

import com.wteam.ug.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long>, JpaSpecificationExecutor<Employee> {

    List<Employee> findByCompany_Id(long id);

    Employee findByUsernameAndPassword(String username,String password);
}
