package com.wteam.ug.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.wteam.ug.entity.Certificate;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate,Long>, JpaSpecificationExecutor<Certificate>{

	List<Certificate> findByUsername(String username);

}
