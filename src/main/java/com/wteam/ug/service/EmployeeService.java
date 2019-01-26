package com.wteam.ug.service;

import com.wteam.ug.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface EmployeeService {
    boolean add(Employee employee, MultipartFile[] files);

    boolean update(Employee employee);

    Employee findById(long id);

    boolean editPassword(long id, String oldPassword, String newPassword);

    boolean delete(long id);

    Page<Employee> findByNumber(String number, Integer page, Integer size);

    Page<Employee> findByName(String name, Integer page, Integer size);

    Page<Employee> findByPhone(String phone, Integer page, Integer size);

    Page<Employee> search(String param, Integer page, Integer size);

    Employee login(String username, String password);
}
