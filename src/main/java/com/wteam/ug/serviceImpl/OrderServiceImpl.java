package com.wteam.ug.serviceImpl;

import com.alipay.demo.trade.payUtils;
import com.wteam.ug.entity.*;
import com.wteam.ug.repository.*;
import com.wteam.ug.service.OrderService;
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
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderRecordRepository orderRecordRepository;
    @Autowired
    PhotoRepository photoRepository;
    @Autowired
    BusinessScopeRepository businessScopeRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    OrderStatusRepository orderStatusRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    EmployeeEvaluateRepository employeeEvaluateRepository;
    @Autowired
    CompanyRepository companyRepository;

    //    静态资源文件夹路径
    @Value("${my.uploadDir}")
    private String filePath;

    //    图片的具体存放位置
    private String definePath = "/image/order/";

    @Override
    public boolean add(Order order, MultipartFile[] files) {
        boolean b;
        try {
//            一级类目和类目
            BusinessScope businessScope = businessScopeRepository.findById(order.getBussinessScopeId()).get();
            order.setCategory(businessScope.getName());
            BusinessScope businessScopeParent = businessScope.getParent();
            order.setFirstCategory(businessScopeParent.getName());

            order.setAddress(addressRepository.findById(order.getAddressId()).get());
            order.setCustomer(customerRepository.findById(order.getCustomerId()).get());
//            工单状态:新建单
            OrderStatus orderStatus = orderStatusRepository.findById(1).get();
            order.setOrderStatus(orderStatus);
            Order save = orderRepository.save(order);
//            保存订单图片路径
            String[] urls = UploadUtils2.uploadFile(files, filePath, definePath);
            List<Photo> photoList = new ArrayList<>();
            for (int i = 0; i < urls.length; i++) {
//                Photo photo = new Photo();
//                photo.setUrl(urls[i]);
//                photo.setOrder(save);
//                photoRepository.save(photo);

                Photo photo = new Photo();
                photo.setUrl(urls[i]);
                photoRepository.save(photo);
                photoList.add(photo);
            }
            order.setPhotoList(photoList);
//            历史记录
            OrderRecord orderRecord = new OrderRecord();
            orderRecord.setCreateDate(new Date());
            orderRecord.setTitle("添加工单:");
            orderRecord.setMsg(orderRecord.getCreateDate() + " 工单被添加了");
            orderRecord.setOrder(save);
            orderRecordRepository.save(orderRecord);
            b = true;
        } catch (RuntimeException e) {
            e.printStackTrace();
            b = false;
        }
        return b;
    }

    @Override
    public boolean cancel(long id) {
        boolean b = false;
        try {
            //            工单状态:已取消
            OrderStatus orderStatus = orderStatusRepository.findById(6).get();
            Order order = orderRepository.findById(id).get();
            order.setOrderStatus(orderStatus);
            orderRepository.save(order);
//            历史记录
            OrderRecord orderRecord = new OrderRecord();
            orderRecord.setOrder(order);
            orderRecord.setCreateDate(new Date());
            orderRecord.setTitle("取消工单:");
            orderRecord.setMsg(orderRecord.getCreateDate() + " 工单被取消了");
            orderRecordRepository.save(orderRecord);
            b = true;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return b;
    }

    @Override
    public String pay(long customerId, long orderId) {
        String url = null;
        try {
//            工单状态:待评价
            OrderStatus orderStatus = orderStatusRepository.findById(7).get();
            Order order = orderRepository.findById(orderId).get();
            order.setOrderStatus(orderStatus);
            orderRepository.save(order);
//            历史记录
            OrderRecord orderRecord = new OrderRecord();
            orderRecord.setOrder(order);
            orderRecord.setCreateDate(new Date());
            orderRecord.setTitle("用户付费:");
            orderRecord.setMsg(orderRecord.getCreateDate() + " 工单被付费了");
            orderRecordRepository.save(orderRecord);
//            调用支付宝接口
            url = payUtils.trade_precreate(order.getMoney(), order.getRemark());
        }catch (RuntimeException e){
            e.printStackTrace();
        }
        return url;
    }

    @Override
    public boolean comment(long employeeId, double evaluate, long orderId) {
        boolean b;
        try {
            Order order = orderRepository.findById(orderId).get();
            Employee employee = employeeRepository.findById(employeeId).get();
            if (employee == null || order == null)
                return false;
            EmployeeEvaluate employeeEvaluate = new EmployeeEvaluate();
            employeeEvaluate.setEmployee(employee);
            employeeEvaluate.setScore(evaluate);
            employeeEvaluateRepository.save(employeeEvaluate);
//            工单状态:已完结
            OrderStatus orderStatus = orderStatusRepository.findById(5).get();
            order.setOrderStatus(orderStatus);
            orderRepository.save(order);
//            历史记录表
            OrderRecord orderRecord = new OrderRecord();
            orderRecord.setCreateDate(new Date());
            orderRecord.setTitle("评价工单:");
            orderRecord.setMsg(orderRecord.getCreateDate() + " 工单被评价了");
            orderRecord.setOrder(order);
            orderRecordRepository.save(orderRecord);
            b = true;
        } catch (RuntimeException e) {
            e.printStackTrace();
            b = false;
        }
        return b;
    }

    @Override
    public boolean update(Order order) {
        boolean b = false;
        try {
            Order order1 = orderRepository.findById(order.getId()).get();
            order1.setTime(order.getTime());
            order1.setPayment(order.getPayment());
            order1.setMoney(order.getMoney());
//        工单状态:待付费
            OrderStatus orderStatus = orderStatusRepository.findById(4).get();
            order1.setOrderStatus(orderStatus);
            orderRepository.save(order1);
//            历史记录表
            OrderRecord orderRecord = new OrderRecord();
            orderRecord.setCreateDate(new Date());
            orderRecord.setTitle(order.getPayment() + "消费:");
            orderRecord.setMsg(orderRecord.getCreateDate() + " 工单被设置为" + order.getPayment() + "收费了");
            orderRecord.setOrder(order);
            orderRecordRepository.save(orderRecord);
            b = true;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return b;
    }

    @Override
    public boolean assgin(long orderId, long companyId) {
        boolean b = false;
        try {
            Order order = orderRepository.findById(orderId).get();
            Company company = companyRepository.findById(companyId).get();
            order.setCompany(company);
//            工单状态:待分配
            OrderStatus orderStatus = orderStatusRepository.findById(2).get();
            order.setOrderStatus(orderStatus);
            orderRepository.save(order);
//            历史记录表
            OrderRecord orderRecord = new OrderRecord();
            orderRecord.setOrder(order);
            orderRecord.setCreateDate(new Date());
            orderRecord.setTitle("分派工单:");
            orderRecord.setMsg(orderRecord.getCreateDate() + "工单被分派给" + company.getContact() + "了 " + company.getPhone() + "(联系电话)");
            orderRecordRepository.save(orderRecord);
            b = true;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean distribute(long orderId, long employeeId) {
        boolean b = false;
        try {
            Order order = orderRepository.findById(orderId).get();
            Employee employee = employeeRepository.findById(employeeId).get();
            order.setEmployee(employee);
//            工单状态:进行中
            OrderStatus orderStatus = orderStatusRepository.findById(3).get();
            order.setOrderStatus(orderStatus);
            orderRepository.save(order);
//            历史记录表
            OrderRecord orderRecord = new OrderRecord();
            orderRecord.setCreateDate(new Date());
            orderRecord.setOrder(order);
            orderRecord.setTitle("分配工单:");
            orderRecord.setMsg(orderRecord.getCreateDate() + " 工单被分配给" + employee.getName() + "了 " + employee.getPhone() + "(联系电话)");
            orderRecordRepository.save(orderRecord);
            b = true;
        }catch (RuntimeException e){
            e.printStackTrace();
        }
        return b;
    }

    @Override
    public List<Order> findByCustomerId(long id) {
        List<Order> orderList = orderRepository.findByCustomer_Id(id);
        return orderList;
    }

    @Override
    public Page<Order> findByFirstCategory(String firstCategory, Integer page, Integer size) {
        Pageable pageable = new PageRequest(page - 1, size);
        Page<Order> orderPage = orderRepository.findAll(new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                predicate.getExpressions().add(
                        criteriaBuilder.equal(
                                root.get("firstCategory").as(String.class), firstCategory.trim()
                        )
                );
                return predicate;
            }
        }, pageable);
        return orderPage;
    }

    @Override
    public Page<Order> findByStatus(Integer statusId, Integer page, Integer size) {
        Pageable pageable = new PageRequest(page - 1, size);
        Page<Order> orderPage = orderRepository.findAll(new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                predicate.getExpressions().add(
                        criteriaBuilder.equal(
                                root.get("orderStatus").as(OrderStatus.class), statusId
                        )
                );
                return predicate;
            }
        }, pageable);
        return orderPage;
    }

    @Override
    public Page<Order> findByStatusAndFirstCategoryAndPayment(Integer id, String firstCategory, String payment, Integer page, Integer size) {
        Pageable pageable = new PageRequest(page - 1, size);
        Page<Order> orderPage = orderRepository.findAll(new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                if (id != 0)
                    list.add(criteriaBuilder.equal(root.get("orderStatus").as(OrderStatus.class),id));
                if (!payment.trim().equals(""))
                    list.add(criteriaBuilder.equal(root.get("payment").as(String.class),payment));
                list.add(criteriaBuilder.equal(root.get("firstCategory").as(String.class),firstCategory));
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        }, pageable);
        return orderPage;
    }

    @Override
    public Page<Order> search(String param, Integer page, Integer size) {
        Pageable pageable = new PageRequest(page - 1, size);
        Page<Order> orderPage = orderRepository.findAll(new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate p1 = criteriaBuilder.like(root.get("firstCategory").as(String.class),"%" + param.trim() + "%");
                Predicate p2 = criteriaBuilder.like(root.get("category").as(String.class), "%" + param.trim() + "%");
                Predicate p3 = criteriaBuilder.like(root.get("payment").as(String.class), "%" + param.trim() + "%");
                criteriaQuery.where(criteriaBuilder.or(p1,p2,p3));
                return criteriaQuery.getRestriction();
            }
        }, pageable);
        return orderPage;
    }

    @Override
    public List<Order> findByCustomerAndStatus(long customerId, long statusId) {
        List<Order> orderList = orderRepository.findByCustomer_IdAndOrderStatus_Id(customerId, statusId);
        return orderList;
    }
}
