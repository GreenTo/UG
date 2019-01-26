package com.wteam.ug.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.wteam.ug.entity.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer>,JpaSpecificationExecutor<Subject>{

}
