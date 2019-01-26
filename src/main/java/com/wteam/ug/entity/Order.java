package com.wteam.ug.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "tb_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @ApiModelProperty(readOnly = true)
    private long id;
//  一级分类
    @ApiModelProperty(readOnly = true)
    private String firstCategory;
//  类目
    @ApiModelProperty(readOnly = true)
    private String category;

    @Column(updatable = false)
    @ApiModelProperty(readOnly = true)
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm")
    private Date time;

    private double money;

    private String description;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "支付方式")
    private String payment;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "address_id")
    private Address address;

//    @ManyToOne(cascade = CascadeType.MERGE)
//    @JoinColumn(name = "business_scope_id")
//    private BusinessScope businessScope;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "customer_id")
    @ApiModelProperty(readOnly = true)
    private Customer customer;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "orderStatus_id")
    @ApiModelProperty(readOnly = true)
    private OrderStatus orderStatus;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "employee_id")
    @ApiModelProperty(readOnly = true)
    private Employee employee;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "company_id")
    @ApiModelProperty(readOnly = true)
    private Company company;

//    @OneToMany(targetEntity = Permission.class, cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
//    @JoinTable(name = "sys_role_permission", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id"))


    @OneToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "tb_order_photo",joinColumns = @JoinColumn(name = "order_id"),inverseJoinColumns = @JoinColumn(name = "photo_id"))
    @ApiModelProperty
    private List<Photo> photoList;

    @Transient
    private long addressId;
    @Transient
    private long bussinessScopeId;
    @Transient
    private long customerId;
    @Transient
    private Integer statusId;

//    @Transient
//    private long employeeId;

//    @Transient
//    private long companyId;
}
