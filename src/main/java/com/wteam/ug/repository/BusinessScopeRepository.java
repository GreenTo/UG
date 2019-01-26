package com.wteam.ug.repository;

import com.wteam.ug.entity.BusinessScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusinessScopeRepository extends JpaRepository<BusinessScope,Long>, JpaSpecificationExecutor<BusinessScope> {

    @Query(value = "select * from tb_business_scope where parent_id is null",nativeQuery = true)
    List<BusinessScope> findParent();
}
