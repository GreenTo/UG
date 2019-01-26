package com.wteam.ug.service;

import com.wteam.ug.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CompanyService {
    boolean add(Company company, MultipartFile[] files);

    boolean update(Company company);

    boolean editPassword(long id, String oldPassword, String newPassword);

    boolean delete(long id);

    boolean changeStatus(long id);

    Company findById(long id);

    Page<Company> findByName(String name, Integer page, Integer size);

    Page<Company> findByType(String type, Integer page, Integer size);

    Page<Company> findByBusinessScope(String businessScope, Integer page, Integer size);

    Page<Company> findByStatus(String status, Integer page, Integer size);

    Page<Company> search(String param, Integer page, Integer size);

    Page<Company> findByTypeAndStatus(String type, String status, Integer page, Integer size);

    Company login(String username, String password);

    boolean add(Company company, MultipartFile symbolFile, MultipartFile frontFile, MultipartFile backFile, MultipartFile serveFile, MultipartFile licenseFile);

    boolean update(Company old, MultipartFile symbolFile, MultipartFile frontFile, MultipartFile backFile, MultipartFile serveFile, MultipartFile licenseFile);

    List<Company> findByLngAndLat(double lng, double lat, String bussinessScope);

    List<Company> findByLngAndLat(double lng, double lat, long bussinessScopeId);

    List<Company> findAroundCompanyOrderByName(double lng, double lat);
}
