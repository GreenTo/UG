package com.wteam.ug.serviceImpl;

import com.wteam.ug.entity.Menu;
import com.wteam.ug.entity.MenuItem;
import com.wteam.ug.repository.MenuItemRepository;
import com.wteam.ug.repository.MenuRepository;
import com.wteam.ug.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MenuItemServiceImpl implements MenuItemService{

	@Autowired
	MenuItemRepository menuItemRepository;
	@Autowired
	MenuRepository menuRepository;
	
	@Override
	public boolean add(MenuItem menuItem) {
		boolean b;
        try {
//        	判断这个节点是顶级结点还是子节点,是顶级节点就直接保存,是子节点就通过parentId找出父节点,再保存
            Integer parentId = menuItem.getParentId();
            if (parentId != 0){
                MenuItem parent = menuItemRepository.findById(parentId).get();
                menuItem.setParent(parent);
//                parent.getChildrenSet().add(menuItem);
            }
            //关联菜单
            Integer menuId = menuItem.getMenuId();
            Menu menu=menuRepository.findById(menuId).get();
            Set<Menu> menus=new HashSet<Menu>();
            menus.add(menu);
            menuItem.setMenuSet(menus);
            
            menuItemRepository.save(menuItem);
            b=true;
        }catch (RuntimeException e){
            e.printStackTrace();
            b=false;
        }
        return b;
	}

	@Override
	public MenuItem findById(Integer id) {
		MenuItem menuItem=menuItemRepository.findById(id).get();
    	return menuItem; 
	}
	
	@Override
	public MenuItem preUpdate(Integer id) {
		MenuItem menuItem = menuItemRepository.findById(id).get();
//		MenuItem parent = menuItem.getParent();
//		Set<MenuItem> menuItems = parent.getChildrenSet();
//		menuItems.remove(menuItem);
    	return menuItem;
	}

	@Override
	public boolean update(MenuItem menuItem) {
		boolean b;
        try {
        	
        	Integer parentId = menuItem.getParentId();
            if (parentId == 0){
            	menuItem.setParent(null);
            }else if(parentId != 0) {
            	MenuItem parent = menuItemRepository.findById(parentId).get();
            	menuItem.setParent(parent);
//            	parent.getChildrenSet().add(menuItem);
            }
            menuItemRepository.save(menuItem);
            b=true;
        }catch (RuntimeException e){
            e.printStackTrace();
            b=false;
        }
        return b;
	}

	@Override
	public boolean delete(Integer id) {
		boolean b;
		try {
			
			//判断是否存在子菜单项
			MenuItem menuItem=menuItemRepository.findById(id).get();
			Set<MenuItem> menuItems=menuItem.getChildrenSet();
			if(menuItems.size()==0) {
				menuItemRepository.deleteById(id);
				b=true;
			}else {
				b=false;
			}
		}catch(RuntimeException e) {
			e.printStackTrace();
			b=false;
		}
    	return b;
	}

	@Override
	public long findTotal() {
		long count = menuItemRepository.count();
        return count;
	}
	
	//获取该菜单下所有的菜单项
	@Override
	public List<MenuItem> findAllMenuItem(Integer id) {
//		Integer menuId=menu.getId();
		List<MenuItem> menuItems = menuItemRepository.findParent(id);
		return menuItems;
	}
}
