package com.wteam.ug.repository;

import com.wteam.ug.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends JpaRepository<Photo,Long> {

//    List<Photo> findByOrder_Id(long id);
}
