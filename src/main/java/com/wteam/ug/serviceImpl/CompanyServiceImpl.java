package com.wteam.ug.serviceImpl;

import com.wteam.ug.baidu.BaiduMap;
import com.wteam.ug.baidu.Distance;
import com.wteam.ug.baidu.Point;
import com.wteam.ug.entity.BusinessScope;
import com.wteam.ug.entity.Company;
import com.wteam.ug.entity.Employee;
import com.wteam.ug.repository.BusinessScopeRepository;
import com.wteam.ug.repository.CompanyRepository;
import com.wteam.ug.repository.EmployeeRepository;
import com.wteam.ug.repository.PointRepository;
import com.wteam.ug.service.CompanyService;
import com.wteam.ug.util.UploadUtils2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    BusinessScopeRepository businessScopeRepository;
    @Autowired
    PointRepository pointRepository;

    //    静态资源文件夹路径
    @Value("${my.uploadDir}")
    private String filePath;

    //    图片的具体存放位置
    private String definePath = "/image/company/";

    @Override
    public boolean add(Company company, MultipartFile[] files) {
        boolean b;
        try {
            String[] urls = UploadUtils2.uploadFile(files, filePath, definePath);
            company.setSymbol(urls[0]);
            company.setFront(urls[1]);
            company.setBack(urls[2]);
            company.setServe(urls[3]);
//            只有公司有营业执照
            if (company.getCode() != null || !company.getCode().trim().equals(""))
                company.setLicense(urls[4]);
            company.setCreateDate(new Date());
            company.setStatus("正常");
//            添加经营范围
            List<Long> businessScopeIdList = company.getBusinessScopeIdList();
            List<BusinessScope> businessScopeList = new ArrayList<>();
            String str = "";
            for (Long id : businessScopeIdList) {
                BusinessScope businessScope = businessScopeRepository.findById(id).get();
                businessScopeList.add(businessScope);
                str = businessScope.getName() + " ";
            }
            company.setBusinessScope(str);
            company.setBusinessScopeList(businessScopeList);
//          添加point
            String address = company.getProvince() + company.getCity() + company.getArea() + company.getDetail();
            Point point = BaiduMap.geocoder(address);
            point.setCompany(company);
            pointRepository.save(point);
            company.setLng(point.getLng());
            company.setLat(point.getLat());
            companyRepository.save(company);
            b = true;
        }catch (RuntimeException e){
            e.printStackTrace();
            b = false;
        }
        return b;
    }

    @Override
    public boolean add(Company company, MultipartFile symbolFile, MultipartFile frontFile, MultipartFile backFile, MultipartFile serveFile, MultipartFile licenseFile) {
        boolean b;
        try {
//            上传图片
            String[] symbolMsg = UploadUtils2.uploadFile(symbolFile, filePath, definePath);
            if (!symbolMsg[0].contains("失败"))
                company.setSymbol(symbolMsg[1]);
            String[] frontMsg = UploadUtils2.uploadFile(frontFile, filePath, definePath);
            if (!frontMsg[0].contains("失败"))
                company.setFront(frontMsg[1]);
            String[] backMsg = UploadUtils2.uploadFile(backFile, filePath, definePath);
            if (backMsg[0].contains("成功"))
                company.setBack(backMsg[1]);
            String[] serveMsg = UploadUtils2.uploadFile(serveFile, filePath, definePath);
            if (serveMsg[0].contains("成功"))
                company.setServe(serveMsg[1]);
            if (licenseFile != null) {
                String[] licenseMsg = UploadUtils2.uploadFile(licenseFile, filePath, definePath);
                if (licenseMsg[0].contains("成功"))
                    company.setLicense(licenseMsg[1]);
            }
            company.setCreateDate(new Date());
            company.setStatus("正常");
//            添加经营范围
            List<Long> businessScopeIdList = company.getBusinessScopeIdList();
            List<BusinessScope> businessScopeList = new ArrayList<>();
            String str = "";
            for (Long id : businessScopeIdList) {
                BusinessScope businessScope = businessScopeRepository.findById(id).get();
                businessScopeList.add(businessScope);
                str = businessScope.getName() + " ";
            }
            company.setBusinessScope(str);
            company.setBusinessScopeList(businessScopeList);
//          添加point
            String address = company.getProvince() + company.getCity() + company.getArea() + company.getDetail();
            Point point = BaiduMap.geocoder(address);
            company.setLng(point.getLng());
            company.setLat(point.getLat());
            companyRepository.save(company);
//            point.setCompany(company);
//            pointRepository.save(point);
            b = true;
        }catch (RuntimeException e){
            e.printStackTrace();
            b = false;
        }
        return b;
    }

    @Override
    public Company login(String username, String password) {
        Company company = companyRepository.findByUsernameAndPassword(username, password);
        return company;
    }

    @Override
    public boolean update(Company company) {
        boolean b;
        try {
            Company old = companyRepository.findById(company.getId()).get();
            old.setName(company.getName());
            old.setProvince(company.getProvince());
            old.setCity(company.getCity());
            old.setArea(company.getArea());
            old.setContact(company.getContact());
            old.setPhone(company.getPhone());
            old.setIdCard(company.getIdCard());
            old.setCode(company.getCode());
//            添加经营范围
            List<Long> businessScopeIdList = company.getBusinessScopeIdList();
            List<BusinessScope> businessScopeList = new ArrayList<>();
            String str = "";
            for (Long id : businessScopeIdList) {
                BusinessScope businessScope = businessScopeRepository.findById(id).get();
                businessScopeList.add(businessScope);
                str = businessScope.getName() + " ";
            }
            company.setBusinessScope(str);
            company.setBusinessScopeList(businessScopeList);
//            计算公司评分
            List<Employee> employeeList = employeeRepository.findByCompany_Id(company.getId());
            double sum = 0;
            for (Employee employee : employeeList){
                sum = sum + employee.getEvaluate();
            }
            double everage = sum/employeeList.size();
            old.setEvaluate(everage);
//            更改point
//            Point oldPoint = pointRepository.findByCompany_Id(old.getId());
            String address = company.getProvince() + company.getCity() + company.getArea() + company.getDetail();
            Point point = BaiduMap.geocoder(address);
//            oldPoint.setLng(point.getLng());
//            oldPoint.setLat(point.getLat());
//            oldPoint.setAddress(point.getAddress());
//            pointRepository.save(oldPoint);
            company.setLng(point.getLng());
            company.setLat(point.getLat());
            companyRepository.save(old);
            b = true;
        }catch (RuntimeException e){
            e.printStackTrace();
            b = false;
        }
        return b;
    }

    @Override
    public boolean update(Company company, MultipartFile symbolFile, MultipartFile frontFile, MultipartFile backFile, MultipartFile serveFile, MultipartFile licenseFile) {
        boolean b;
        try {
            Company old = companyRepository.findById(company.getId()).get();
            old.setName(company.getName());
            old.setProvince(company.getProvince());
            old.setCity(company.getCity());
            old.setArea(company.getArea());
            old.setContact(company.getContact());
            old.setPhone(company.getPhone());
            old.setIdCard(company.getIdCard());
            old.setCode(company.getCode());
            //            上传图片
            if (symbolFile != null) {
                String[] symbolMsg = UploadUtils2.uploadFile(symbolFile, filePath, definePath);
                if (!symbolMsg[0].contains("失败"))
                    company.setSymbol(symbolMsg[1]);
            }
            if (frontFile != null) {
                String[] frontMsg = UploadUtils2.uploadFile(frontFile, filePath, definePath);
                if (!frontMsg[0].contains("失败"))
                    company.setFront(frontMsg[1]);
            }
            if (backFile != null) {
                String[] backMsg = UploadUtils2.uploadFile(backFile, filePath, definePath);
                if (backMsg[0].contains("成功"))
                    company.setBack(backMsg[1]);
            }
            if (serveFile != null) {
                String[] serveMsg = UploadUtils2.uploadFile(serveFile, filePath, definePath);
                if (serveMsg[0].contains("成功"))
                    company.setServe(serveMsg[1]);
            }
            if (licenseFile != null) {
                String[] licenseMsg = UploadUtils2.uploadFile(licenseFile, filePath, definePath);
                if (licenseMsg[0].contains("成功"))
                    company.setLicense(licenseMsg[1]);
            }
//            添加经营范围
            List<Long> businessScopeIdList = company.getBusinessScopeIdList();
            List<BusinessScope> businessScopeList = new ArrayList<>();
            String str = "";
            for (Long id : businessScopeIdList) {
                BusinessScope businessScope = businessScopeRepository.findById(id).get();
                businessScopeList.add(businessScope);
                str = businessScope.getName() + " ";
            }
            company.setBusinessScope(str);
            company.setBusinessScopeList(businessScopeList);
//            计算公司评分
            List<Employee> employeeList = employeeRepository.findByCompany_Id(company.getId());
            double sum = 0;
            for (Employee employee : employeeList){
                sum = sum + employee.getEvaluate();
            }
            double everage = sum/employeeList.size();
            old.setEvaluate(everage);
            companyRepository.save(old);
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
            Company company = companyRepository.findById(id).get();
            if (company.getPassword().equals(oldPassword.trim())){
                company.setPassword(newPassword.trim());
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
            companyRepository.deleteById(id);
            List<Employee> employeeList = employeeRepository.findByCompany_Id(id);
            for (Employee employee : employeeList) {
                employeeRepository.delete(employee);
            }
            return true;
        }catch (RuntimeException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean changeStatus(long id) {
        try {
            Company company = companyRepository.findById(id).get();
            if (company.getStatus().trim().equals("正常")){
                company.setStatus("冻结");
            }else {
                company.setStatus("正常");
            }
            return true;
        }catch (RuntimeException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Company findById(long id) {
        Company company = companyRepository.findById(id).get();
        return company;
    }

    @Override
    public Page<Company> findByName(String name, Integer page, Integer size) {
        Pageable pageable = new PageRequest(page - 1, size);
        Page<Company> companyPage = companyRepository.findAll(new Specification<Company>() {
            @Override
            public Predicate toPredicate(Root<Company> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                predicate.getExpressions().add(
                        criteriaBuilder.like(
                                root.get("name").as(String.class), "%" + name.trim() + "%"
                        )
                );
                return predicate;
            }
        }, pageable);
        return companyPage;
    }

    @Override
    public Page<Company> findByType(String type, Integer page, Integer size) {
        Pageable pageable = new PageRequest(page - 1, size);
        Page<Company> companyPage = companyRepository.findAll(new Specification<Company>() {
            @Override
            public Predicate toPredicate(Root<Company> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                predicate.getExpressions().add(
                        criteriaBuilder.equal(
                                root.get("type").as(String.class), type.trim()
                        )
                );
                return predicate;
            }
        }, pageable);
        return companyPage;
    }

    @Override
    public Page<Company> findByBusinessScope(String businessScope, Integer page, Integer size) {
        Pageable pageable = new PageRequest(page - 1, size);
        Page<Company> companyPage = companyRepository.findAll(new Specification<Company>() {
            @Override
            public Predicate toPredicate(Root<Company> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                predicate.getExpressions().add(
                        criteriaBuilder.like(
                                root.get("businessScope").as(String.class), "%" + businessScope.trim() + "%"
                        )
                );
                return predicate;
            }
        }, pageable);
        return companyPage;
    }

    @Override
    public Page<Company> findByStatus(String status, Integer page, Integer size) {
        Pageable pageable = new PageRequest(page - 1, size);
        Page<Company> companyPage = companyRepository.findAll(new Specification<Company>() {
            @Override
            public Predicate toPredicate(Root<Company> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                predicate.getExpressions().add(
                        criteriaBuilder.equal(
                                root.get("status").as(String.class), status.trim()
                        )
                );
                return predicate;
            }
        }, pageable);
        return companyPage;
    }

    @Override
    public Page<Company> search(String param, Integer page, Integer size) {
        Pageable pageable = new PageRequest(page - 1, size);
        Page<Company> companyPage = companyRepository.findAll(new Specification<Company>() {
            @Override
            public Predicate toPredicate(Root<Company> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate p1 = criteriaBuilder.like(root.get("name").as(String.class), "%" + param.trim() + "%");
                Predicate p2 = criteriaBuilder.like(root.get("type").as(String.class), "%" + param.trim() + "%");
                Predicate p3 = criteriaBuilder.like(root.get("businessScope").as(String.class), "%" + param.trim() + "%");
                Predicate p4 = criteriaBuilder.like(root.get("status").as(String.class), "%" + param.trim() + "%");
                Predicate p5 = criteriaBuilder.like(root.get("contract").as(String.class),"%" + param.trim() + "%");
                criteriaQuery.where(criteriaBuilder.or(p1,p2,p3,p4,p5));
                return criteriaQuery.getRestriction();
            }
        }, pageable);
        return companyPage;
    }

    @Override
    public Page<Company> findByTypeAndStatus(String type, String status, Integer page, Integer size) {
        Pageable pageable = new PageRequest(page - 1, size);
        Page<Company> companyPage = companyRepository.findAll(new Specification<Company>() {
            @Override
            public Predicate toPredicate(Root<Company> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate p2 = criteriaBuilder.equal(root.get("type").as(String.class), type.trim());
                Predicate p4 = criteriaBuilder.equal(root.get("status").as(String.class), status.trim());
                criteriaQuery.where(criteriaBuilder.and(p2,p4));
                return criteriaQuery.getRestriction();
            }
        }, pageable);
        return companyPage;
    }

    @Override
    public List<Company> findByLngAndLat(double lng, double lat, String bussinessScope) {
//        方圆30km
        int meter = 300000;
        Point points = BaiduMap.antiGeocoder(lng, lat);
        List<Point> pointList = pointRepository.findAll();
        List<Point> newPointList = new ArrayList<>();
        List<Distance> distanceList = BaiduMap.getDistance(points, pointList);
//        小于30千米就返回给前端
        for (int i = 0;i < distanceList.size();i++){
            Distance distance = distanceList.get(i);
            if (distance.getDistanceNum() < meter){
                Point point = pointList.get(i);
                point.getCompany().setDistance(distance.getDistanceText());
                pointList.set(i,point);
                newPointList.add(pointList.get(i));
            }
        }
        List<Company> companyList = new ArrayList<>();
//        根据经营范围筛选
        if (bussinessScope.equals("")){
            for (Point point : newPointList) {
                companyList.add(point.getCompany());
            }
        }else {
            for (Point point : newPointList) {
                Company company = point.getCompany();
                if (company.getBusinessScope().contains(bussinessScope)){
                    companyList.add(company);
                }
            }
        }
        //        查询商户距离服务的距离
        Point orgin = new Point();
        orgin.setLng(lng);
        orgin.setLat(lat);
        returnDistance(companyList,orgin);
        return companyList;
    }

    @Override
    public List<Company> findByLngAndLat(double lng, double lat, long bussinessScopeId) {
        List<BusinessScope> businessScopeList = new ArrayList<>();
        List<Company> companyList = new ArrayList<>();
        if (bussinessScopeId != 0){
            BusinessScope businessScope = businessScopeRepository.findById(bussinessScopeId).get();
            businessScopeList.add(businessScope);
            companyList = companyRepository.findByLngBetweenAndLatBetweenAndBusinessScopeListIn(lng - 0.5, lng + 0.5, lat - 0.5, lat + 0.5,businessScopeList);
        }else {
            companyList = companyRepository.findByLngBetweenAndLatBetween(lng - 0.5, lng + 0.5, lat - 0.5, lat + 0.5);
        }
        //        查询商户距离服务的距离
        Point orgin = new Point();
        orgin.setLng(lng);
        orgin.setLat(lat);
        returnDistance(companyList,orgin);
        return companyList;
    }

    @Override
    public List<Company> findAroundCompanyOrderByName(double lng, double lat) {
        List<Company> companyList = companyRepository.findByLngBetweenAndLatBetweenAndNameOrderByName(lng - 0.5, lng + 0.5, lat - 0.5, lat + 0.5);
        return companyList;
    }

    public static void returnDistance(List<Company> companyList,Point orgin){
        List<Point> pointList = new ArrayList<>();
        for (Company company : companyList) {
            Point point = new Point();
            point.setLng(company.getLng());
            point.setLat(company.getLat());
            pointList.add(point);
        }
        List<Distance> distanceList = BaiduMap.getDistance(orgin, pointList);
        for (int i = 0;i < distanceList.size();i++){
            companyList.get(i).setDistance(distanceList.get(i).getDistanceText());
        }
    }
}
