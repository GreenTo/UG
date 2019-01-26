package com.wteam.ug;

import com.wteam.ug.repository.MenuRepository;
import com.wteam.ug.service.MenuItemService;
import com.wteam.ug.service.MenuService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//import org.codehaus.jackson.JsonGenerationException;
//import org.codehaus.jackson.map.JsonMappingException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class testMenu {
	@Autowired
	MenuService menuService;
	@Autowired
	MenuRepository menuRepository;
	@Autowired
	MenuItemService menuItemService;
//	@Autowired
//	MenuItemRepository menuItemRepository;
	
//	@Test
//	public void testMenus() throws JsonGenerationException, JsonMappingException, IOException {
//		Menu menu=new Menu();//✔
//		menu.setName("testMenu11");
//		menu.setTime(new Date());
//		Set<MenuItem> menuItems = new HashSet<MenuItem>();
//		MenuItem menuItem=menuItemService.findById(6);
//		menuItems.add(menuItem);
//		menu.setMenuItems(menuItems);
//		boolean b = menuService.add(menu);
//		if(b) {
//		System.out.println("添加成功");
//		}else System.out.println("添加失败");
		
//		Menu preUpdate = menuService.preUpdate(3);//✔
//        System.out.println("更新前:"+preUpdate.getName());
//        preUpdate.setName("testUpdate");
//        menuService.update(preUpdate);
//        System.out.println("更新后:"+preUpdate.getName());
        
//        System.out.println("删除菜单");//✔
//        Integer i =19;
//        boolean bb=menuService.delete(i);
//        if(bb) {
//    		System.out.println("删除成功");
//    	}else System.out.println("删除失败");
    		
        
//        System.out.println("根据名字查询菜单superAdmin");//✔
//        List<Menu> menuList=menuService.findByName("testMenu");
//        for(Menu menu02:menuList) {
//        	String s=menu02.getName();
//        	System.out.println(s);
//        }
        
//        System.out.println("查询菜单分页");//✔
////        ObjectMapper MAPPER = new ObjectMapper();
//        Page<Menu> menu03=menuService.findByPage(1, 10);
//        List<Menu> menu000=menu03.getContent();
////        String json = MAPPER.writeValueAsString(menu000);
////        System.out.println(json);
//        for(Menu menu04:menu000) {
//        	String s=menu04.getName();
//        	System.out.println(s);
//        }
        
//        System.out.println("模糊查询");//✔
//        Page<Menu> menu05=menuService.search("t", 1, 10);
//        for(Menu menu06:menu05) {
//        	String s=menu06.getName();
//        	System.out.println(s);
//        }
        
//		System.out.println("显示所有菜单项");//✔
//		Menu menu001 = menuRepository.findById(6).get();
//		List<MenuItem> menuItems = menuItemService.findAllMenuItem(menu001);
//		for(MenuItem menuItem:menuItems) {
//			
//				System.out.println(menuItem.getName());
//				Set<MenuItem> menuItems01 = menuItem.getChildrenSet();
//				for(MenuItem menuItem01:menuItems01) {
//					System.out.println("	"+menuItem01.getName());
//				}
//		}
		
//	}
	
	@Test
	public void testmenuitem() {
//		MenuItem menuItem=new MenuItem();//✔
//		menuItem.setName("testItem08");
//		menuItem.setParentId(0);
//		menuItem.setMenuId(6);
//		menuItem.setDisplay(true);
//		Menu testMenu=menuRepository.findById(5).get();
//		Set<Menu> menus=menuItem.getMenus();
//		menus.add(testMenu);
//		boolean b=menuItemService.add(menuItem);
//		if(b) {
//			System.out.println("添加成功");
//		}else System.out.println("添加失败");
			
//		Boolean bb=menuItemService.delete(1);//✔
//		if(bb) {
//			System.out.println("sc成功");
//		}else System.out.println("sc失败");
		
//		MenuItem preUpdate = menuItemService.preUpdate(14);//✔
//        System.out.println("更新前:"+preUpdate.getName());
//        preUpdate.setName("testUpdate");
//        preUpdate.setParentId(13);
//        menuItemService.update(preUpdate);
//        System.out.println("更新后:"+preUpdate.getName());
//		System.out.println("");
		
//		System.out.println("查询指定id的菜单项名");//✔
//		MenuItem menuItem02=menuItemService.findById(1);
//		System.out.println(menuItem02.getName());
	}
}
