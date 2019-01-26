package com.wteam.ug.service;

import com.wteam.ug.entity.OrderRecord;

import java.util.List;

public interface OrderRecordService {
    List<OrderRecord> findCustomerId(long id);
}
