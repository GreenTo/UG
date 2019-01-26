package com.wteam.ug.serviceImpl;

import com.wteam.ug.entity.OrderRecord;
import com.wteam.ug.repository.OrderRecordRepository;
import com.wteam.ug.service.OrderRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderRecordServiceImpl implements OrderRecordService {

    @Autowired
    OrderRecordRepository orderRecordRepository;

    @Override
    public List<OrderRecord> findCustomerId(long id) {
        List<OrderRecord> orderRecordList = orderRecordRepository.findByOrder_Id(id);
        return orderRecordList;
    }
}
