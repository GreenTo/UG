package com.wteam.ug;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wteam.ug.entity.Order;
import com.wteam.ug.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class testOrder {

    @Autowired
    OrderService orderService;

    @Test
    public void testFindByStatusId() throws JsonProcessingException {
        Page<Order> orderPage = orderService.findByStatus(1, 1, 10);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(orderPage);
        System.out.println(json);
    }
}
