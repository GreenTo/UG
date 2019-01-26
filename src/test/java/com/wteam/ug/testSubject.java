package com.wteam.ug;

import com.wteam.ug.service.SubjectService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class testSubject {

	@Autowired
	SubjectService subjectService;
	
	@Test
	public void testSubject() {
//		Subject subject = new Subject();//✔
//		subject.setCoding("1004");
//		subject.setName("银行存款");
//		subject.setGeneralCategory("资产");
//		subject.setCategory("固定资产");
//		subject.setDirectionId(1);
//		boolean b = subjectService.add(subject);
//		if(b) {
//		System.out.println("添加成功");
//		}else System.out.println("添加失败");
		
//		Subject preUpdate = subjectService.preUpdate(5);//✔
//		System.out.println("更新前:"+preUpdate.getName());
//		preUpdate.setName("库存现金");
//		preUpdate.setDirectionId(-1);
//		subjectService.update(preUpdate);
//		System.out.println("更新后:"+preUpdate.getName());
      
//		System.out.println("删除");//✔
//		Integer i =1;
//		boolean bb=subjectService.delete(i);
//		if(bb) {
//			System.out.println("删除成功");
//		}else System.out.println("删除失败");
		
//		System.out.println("查询分页");//✔
//		Page<Subject> menu03=subjectService.findByPage(1, 10);
//		List<Subject> menu000=menu03.getContent();
//		for(Subject menu04:menu000) {
//			String s=menu04.getName();
//			System.out.println(s);
//		}
		
//		System.out.println("名字模糊查询");//✔
//		Page<Subject> menu05=subjectService.searchByName("现金", 1, 10);
//		for(Subject menu06:menu05) {
//			String s=menu06.getName();
//			System.out.println(s);
//		}
		
//		System.out.println("编号模糊查询");//✔
//		Page<Subject> menu005=subjectService.searchByCoding("01", 1, 10);
//		for(Subject menu006:menu005) {
//			String s=menu006.getName();
//			System.out.println(s);
//		}
		
//		System.out.println("模糊查询");//✔
//		Page<Subject> search = subjectService.searchAll("01", 1, 10);
//		for(Subject menu6:search) {
//			
//			String s=menu6.getName();
//			System.out.println(s);
//		}
	}
}
