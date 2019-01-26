package com.wteam.ug.repository;

import com.wteam.ug.entity.Action;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionRepository extends JpaRepository<Action,Long>, JpaSpecificationExecutor<Action> {

}
