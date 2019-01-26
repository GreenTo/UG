package com.wteam.ug.serviceImpl;

import com.wteam.ug.entity.Company;
import com.wteam.ug.entity.Employee;
import com.wteam.ug.entity.EmployeeEvaluate;
import com.wteam.ug.entity.Role;
import com.wteam.ug.repository.CompanyRepository;
import com.wteam.ug.repository.EmployeeEvaluateRepository;
import com.wteam.ug.repository.EmployeeRepository;
import com.wteam.ug.repository.RoleRepository;
import com.wteam.ug.service.EmployeeService;
import com.wteam.ug.util.UploadUtils2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    EmployeeEvaluateRepository employeeEvaluateRepository;

//    静态资源文件夹路径
    @Value("${my.uploadDir}")
    private String filePath;

//    图片的具体存放位置
    private String definePath = "/image/employee/";

    @Override
    public boolean add(Employee employee, MultipartFile[] files) {
        boolean b;
        try {
            String[] urls = UploadUtils2.uploadFile(files, filePath, definePath);
            employee.setFront(urls[0]);
            employee.setBack(urls[1]);
            employee.setCreateDate(new Date());
            Company company = companyRepository.findById(employee.getCompanyId()).get();
            employee.setCompany(company);
            Set<Role> roleList = new HashSet<>();
            if (employee.getRoles() != null){
                for (Long id : employee.getRoles()){
                    Role role = roleRepository.findById(id).get();
                    roleList.add(role);
                }
                employee.setRoleSet(roleList);
            }
            employeeRepository.save(employee);
            b = true;
        }catch (RuntimeException e){
            e.printStackTrace();
            b = false;
        }
        return b;
    }

    @Override
    public Employee login(String username, String password) {
        Employee employee = employeeRepository.findByUsernameAndPassword(username, password);
        return employee;
    }

    @Override
    public boolean update(Employee employee) {
        boolean b;
        try {
//            先把该员工找出来,再更新替换,这样防止更新时把本来存在的信息设为空,比如密码
            Employee old = employeeRepository.findById(employee.getId()).get();
            old.setNumber(employee.getNumber());
            old.setPhone(employee.getPhone());
            old.setName(employee.getName());
            old.setUsername(employee.getUsername());
//            old.setIdCard(employee.getIdCard());
            Set<Role> roleList = new HashSet<>();
            if (employee.getRoles() != null){
                for (Long id : employee.getRoles()){
                    Role role = roleRepository.findById(id).get();
                    roleList.add(role);
                }
                old.setRoleSet(roleList);
            }
            //            计算员工的平均评分
            List<EmployeeEvaluate> evaluateList = employeeEvaluateRepository.findByEmployee_Id(employee.getId());
            if (evaluateList != null){
                double sum = 0;
                for (EmployeeEvaluate evaluate : evaluateList){
                    sum = evaluate.getScore() + sum;
                }
                double everage = sum/evaluateList.size();
                old.setEvaluate(everage);
            }
            employeeRepository.save(old);
            b = true;
        }catch (RuntimeException e){
            e.printStackTrace();
            b = false;
        }
        return b;
    }

    @Override
    public boolean editPassword(long id, String oldPassword, String newPassword) {
        try {
            Employee employee = employeeRepository.findById(id).get();
            if (employee.getPassword().equals(oldPassword.trim())){
                employee.setPassword(newPassword.trim());
                employeeRepository.save(employee);
                return true;
            }
        }catch (RuntimeException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(long id) {
        try {
            employeeRepository.deleteById(id);
            return true;
        }catch (RuntimeException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Employee findById(long id) {
        Employee employee = employeeRepository.findById(id).get();
        employee.setPassword(null);
        return employee;
    }

    @Override
    public Page<Employee> findByNumber(String number, Integer page, Integer size) {
        Pageable pageable = new PageRequest(page - 1, size);
        Page<Employee> employeePage = employeeRepository.findAll(new Specification<Employee>() {
            @Override
            public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                predicate.getExpressions().add(
                        criteriaBuilder.like(
                                root.get("number").as(String.class), "%" + number.trim() + "%"
                        )
                );
                return predicate;
            }
        }, pageable);
        return employeePage;
    }

    @Override
    public Page<Employee> findByName(String name, Integer page, Integer size) {
        Pageable pageable = new PageRequest(page - 1, size);
        Page<Employee> employeePage = employeeRepository.findAll(new Specification<Employee>() {
            @Override
            public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                predicate.getExpressions().add(
                        criteriaBuilder.like(
                                root.get("name").as(String.class), "%" + name.trim() + "%"
                        )
                );
                return predicate;
            }
        }, pageable);
        return employeePage;
    }

    @Override
    public Page<Employee> findByPhone(String phone, Integer page, Integer size) {
        Pageable pageable = new PageRequest(page - 1, size);
        Page<Employee> employeePage = employeeRepository.findAll(new Specification<Employee>() {
            @Override
            public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                predicate.getExpressions().add(
                        criteriaBuilder.like(
                                root.get("phone").as(String.class), "%" + phone.trim() + "%"
                        )
                );
                return predicate;
            }
        }, pageable);
        return employeePage;
    }

    @Override
    public Page<Employee> search(String param, Integer page, Integer size) {
        Pageable pageable = new PageRequest(page - 1, size);
        Page<Employee> employeePage = employeeRepository.findAll(new Specification<Employee>() {
            @Override
            public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate p1 = criteriaBuilder.like(root.get("number").as(String.class),"%" + param.trim() + "%");
                Predicate p2 = criteriaBuilder.like(root.get("name").as(String.class),"%" + param.trim() + "%");
                Predicate p3 = criteriaBuilder.like(root.get("phone").as(String.class),"%" + param.trim() + "%");
                criteriaQuery.where(criteriaBuilder.or(p1, p2, p3));
                return criteriaQuery.getRestriction();
            }
        }, pageable);
        return employeePage;
    }
}
