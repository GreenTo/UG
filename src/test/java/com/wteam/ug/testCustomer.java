package com.wteam.ug;

import com.wteam.ug.entity.Customer;
import com.wteam.ug.repository.CustomerRepository;
import com.wteam.ug.service.CustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class testCustomer {
	@Autowired
	CustomerService customerService;
	@Autowired
	CustomerRepository customerRepository;

	@Test
	public void testCustomer() {
//		Customer customer = new Customer();
//		customer.setUsername("customer2");
//		customer.setPassword("123456");
//		customer.setPhone("15355555555");
//		customer.setTime(new Date());
//		boolean b = customerService.add(customer);
//		if(b) {
//			System.out.println("注册成功");
//		}else 
//			System.out.println("注册失败");
		
//		Customer preUpdate = customerService.preUpdate(1);//✔
//		System.out.println("更新前:"+preUpdate.getUsername());
//		preUpdate.setUsername("库存现金");
//		preUpdate.setPhone("15355555559");
//		customerService.update(preUpdate);
//		System.out.println("更新后:"+preUpdate.getUsername());
		
//		System.out.println("删除");//✔
//		Integer i =1;
//		boolean bb=customerService.delete(i);
//		if(bb) {
//			System.out.println("删除成功");
//		}else System.out.println("删除失败");
		
//		System.out.println("查询分页");//✔
//		Page<Customer> menu03=customerService.findByPage(1, 10);
//		List<Customer> menu000=menu03.getContent();
//		for(Customer menu04:menu000) {
//			String s=menu04.getUsername();
//			System.out.println(s);
//		}
	}

	@Test
	public void  testLogin(){
		String username = "123";
		String password = "123";
		Customer customer = customerRepository.findByUsernameAndPassword(username, password);
	}
}
