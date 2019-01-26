package com.wteam.ug.repository;

import com.wteam.ug.entity.BusinessScope;
import com.wteam.ug.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Long>, JpaSpecificationExecutor<Company> {

    Company findByUsernameAndPassword(String username,String password);

    List<Company> findByLngBetweenAndLatBetween(double leftLng,double rightLng,double leftLat,double rightLat);

    @Query(value = "select * from tb_company where lng between ?1 and ?2 and lat between ?3 and ?4 order by convert(name using gbk)",nativeQuery = true)
    List<Company> findByLngBetweenAndLatBetweenAndNameOrderByName(double leftLng,double rightLng,double leftLat,double rightLat);

    List<Company> findByLngBetweenAndLatBetweenAndBusinessScopeListIn(double leftLng, double rightLng, double leftLat, double rightLat, List<BusinessScope> businessScopeList);
}
