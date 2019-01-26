package com.wteam.ug;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wteam.ug.entity.BusinessScope;
import com.wteam.ug.repository.BusinessScopeRepository;
import com.wteam.ug.service.BusinessScopeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BusinessScopeTest {

    @Resource(name = "businessScope1")
    BusinessScopeService businessScopeService;
    @Autowired
    BusinessScopeRepository businessScopeRepository;

    @Test
    public void testAdd(){
        BusinessScope b1 = new BusinessScope();
        b1.setName("清洗");
        BusinessScope b2 = new BusinessScope();
        b2.setName("维修");
        businessScopeService.add(b1);
        businessScopeService.add(b2);
        BusinessScope b11 = businessScopeRepository.findById((long) 1).get();
        BusinessScope b3 = new BusinessScope();
        b3.setName("电脑");
        b3.setParent(b11);
        businessScopeRepository.save(b3);
    }

    @Test
    public void testFind() throws JsonProcessingException {
        List<BusinessScope> businessScopeList = businessScopeRepository.findParent();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(businessScopeList);
        System.out.println(json);
    }

    @Test
    public void testDelete() throws JsonProcessingException {
        BusinessScope b1 = new BusinessScope();
        b1.setName("家用电器");
        b1.setParentId(2);
        businessScopeService.add(b1);
        BusinessScope scope = businessScopeRepository.findById((long) 2).get();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(scope);
        System.out.println(json);
        businessScopeService.delete(2);
    }

    @Test
    public void testUpdate(){
        BusinessScope scope = businessScopeService.findById(16);
        scope.setName("冰箱");
        businessScopeService.update(scope);

    }
}
