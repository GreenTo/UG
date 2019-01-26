package com.wteam.ug.repository;

import com.wteam.ug.entity.OrderRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRecordRepository extends JpaRepository<OrderRecord,Long> {

    List<OrderRecord> findByOrder_Id(long id);
}
