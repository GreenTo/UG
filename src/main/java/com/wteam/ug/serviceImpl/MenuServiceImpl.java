package com.wteam.ug.serviceImpl;

import com.wteam.ug.entity.Menu;
import com.wteam.ug.entity.MenuItem;
import com.wteam.ug.repository.MenuItemRepository;
import com.wteam.ug.repository.MenuRepository;
import com.wteam.ug.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Service
public class MenuServiceImpl implements MenuService{
	
	@Autowired
	MenuRepository menuRepository;
	@Autowired
	MenuItemRepository menuItemRepository;

	@Override
	public boolean add(Menu menu) {
		boolean b;
        try {
        	menu.setCreateDate(new Date());
            menuRepository.save(menu);
            b=true;
        }catch (RuntimeException e){
            e.printStackTrace();
            b=false;
        }
        return b;
	};

	@Override
	public List<Menu> findAll(){
    	List<Menu> menus=menuRepository.findAll();
    	return menus;
    };
    
    @Override
    public Menu preUpdate(Integer id) {
    	Menu menu=menuRepository.findById(id).get();
    	return menu;
    };
    
    @Override
    public boolean update(Menu menu) {
    	boolean b;
        try {
            menuRepository.save(menu);
            b=true;
        }catch (RuntimeException e){
            e.printStackTrace();
            b=false;
        }
        return b;
    };
    
    @Override
    public boolean delete(Integer id) {
    	boolean b;
    	try {
    		//删除该菜单下的所有菜单项
    		Menu menu=menuRepository.findById(id).get();
    		Set<MenuItem> menuItems=menu.getMenuItemSet();
    		if(menuItems.size()!=0) {
    			for(MenuItem menuItem:menuItems) {
//    				Integer i=menuItem.getId();
    				menuItemRepository.delete(menuItem);
    			}
    		}
    		menuRepository.deleteById(id);
    		b=true;
    	}catch(RuntimeException e) {
    		e.printStackTrace();
    		b=false;
    	}
    	return b;
    };
    
    @Override
    public long findTotal() {
    	 long count = menuRepository.count();
         return count;
    }

	@Override
	public Page<Menu> findByPage(Integer page, Integer size) {
		Pageable pageable = new PageRequest(page-1, size);
        Page<Menu> menuPage = menuRepository.findAll(pageable);
        return menuPage;
	}

	@Override//根据完整名字查询
	public List<Menu> findByName(String name) {
		List<Menu> menus=menuRepository.findByName(name);
    	return menus;
	}

	@Override
    public Page<Menu> search(String param, Integer page, Integer size) {
        Pageable pageable = new PageRequest(page-1,size);
        Page<Menu> menuPage = menuRepository.findAll(new Specification<Menu>() {
            @Override
            public Predicate toPredicate(Root<Menu> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                predicate.getExpressions().add(
                        criteriaBuilder.like(
//                                root从数据表中获得名为"name"的属性,它是string类型的,
                                root.get("name").as(String.class), "%" + param.trim() + "%"
                        )
                );
                return predicate;
            }
        }, pageable);
        return menuPage;
    }

}
