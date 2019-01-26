package com.wteam.ug.repository;

import com.wteam.ug.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {


//    @Query(nativeQuery = true,value = "select * from  ")
//    Page<User> findByUsernameLike(String param);

    User findByUsernameAndPassword(String username,String password);
}
