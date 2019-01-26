package com.wteam.ug.serviceImpl;

import com.wteam.ug.entity.BusinessScope;
import com.wteam.ug.repository.BusinessScopeRepository;
import com.wteam.ug.service.BusinessScopeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("businessScope1")
public class BusinessScopeServiceImpl implements BusinessScopeService {

    @Autowired
    BusinessScopeRepository businessScopeRepository;

    @Override
    public boolean add(BusinessScope businessScope) {
        boolean b;
        try {
//            判断这个节点是顶级结点还是子节点,是顶级节点就直接保存,是子节点就通过parentId找出父节点,再保存
            long parentId = businessScope.getParentId();
            if (parentId != 0){
                BusinessScope parent = businessScopeRepository.findById(parentId).get();
                businessScope.setParent(parent);
            }else {
                businessScope.setParent(null);
            }
            businessScopeRepository.save(businessScope);
            b = true;
        }catch (RuntimeException e){
            e.printStackTrace();
            b = false;
        }
        return b;
    }

    @Override
    public boolean update(BusinessScope businessScope) {
        boolean b;
        try {
//            判断这个节点是顶级结点还是子节点,是顶级节点就直接保存,是子节点就通过parentId找出父节点,再保存
            long parentId = businessScope.getParentId();
            if (parentId != 0){
                BusinessScope parent = businessScopeRepository.findById(parentId).get();
                businessScope.setParent(parent);
            }else {
                businessScope.setParent(null);
            }
            businessScopeRepository.save(businessScope);
            b = true;
        }catch (RuntimeException e){
            e.printStackTrace();
            b = false;
        }
        return b;
    }

    @Override
    public boolean delete(long id) {
        boolean b;
        try {
            businessScopeRepository.deleteById(id);
            b = true;
        }catch (RuntimeException e){
            e.printStackTrace();
            b = false;
        }
        return b;
    }

    @Override
    public BusinessScope findById(long id) {
        BusinessScope businessScope = businessScopeRepository.findById(id).get();
        return businessScope;
    }

    @Override
    public List<BusinessScope> findAll() {
        List<BusinessScope> scopeList = businessScopeRepository.findParent();
        return scopeList;
    }
}
