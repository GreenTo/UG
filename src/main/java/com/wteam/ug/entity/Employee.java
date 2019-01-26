package com.wteam.ug.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "tb_employee")
public class Employee {

//    员工号,需要公司分配
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @ApiModelProperty(readOnly = true)
    private long id;
    @Column(unique = true,nullable = false)
    private String number;
    @Column(nullable = false,unique = true)
    private String username;
    @Column(nullable = false)
    @JsonIgnore
    private String password;
    private String name;
    @Column(length = 11)
    private String phone;
    @Column(length = 18,unique = true,nullable = false,updatable = false)
    private String idCard;
    @Column(updatable = false)
    @ApiModelProperty(readOnly = true)
    private Date createDate;
//  身份证正面
//    @Column(updatable = false)
    @ApiModelProperty(value = "身份证正面url",readOnly = true)
    private String front;
//    身份证反面
//    @Column(updatable = false)
    @ApiModelProperty(value = "身份证反面url",readOnly = true)
    private String back;
//    员工评分
    @ApiModelProperty(value = "员工评分",readOnly = true)
    private double evaluate;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "tb_employee_role", joinColumns = @JoinColumn(name = "employee_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roleSet;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Transient
//    @JsonIgnore
    private long companyId;

    @Transient
//    @JsonIgnore
    private List<Long> roles;
}
