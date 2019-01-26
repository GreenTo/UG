package com.wteam.ug.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "tb_company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @ApiModelProperty(readOnly = true)
    private long id;
    @Column(unique = true,nullable = false,length = 21)
    @ApiModelProperty(readOnly = true)//登录名只读
    private String username;
    @Column(nullable = false)
    @JsonIgnore
    private String password;
//    公司名
    @Column(nullable = false,unique = true)
    private String name;
//    公司类型
    @ApiModelProperty(readOnly = true)//类型下拉菜单只读
    private String type;

    @Column(updatable = false)
    @ApiModelProperty(readOnly = true)
    private Date createDate;

    private String province;
    private String city;
    private String area;
//    详细地址
    private String detail;
//  联系人姓名
    @Column(length = 10)
    @ApiModelProperty(value = "联系人姓名")
    private String contact;
    @Column(length = 11)
    private String phone;
    @Column(length = 18)
    private String idCard;
    @ApiModelProperty(value = "信用代码")
    private String code;

    @ApiModelProperty(value = "经度",readOnly = true)
    private double lng;
    @ApiModelProperty(value = "纬度",readOnly = true)
    private double lat;
//  以下都是图片url
    @ApiModelProperty(value = "公司标志url")
    private String symbol;
    @ApiModelProperty(value = "身份证正面url",readOnly = true)
    private String front;
    @ApiModelProperty(value = "身份证反面url",readOnly = true)
    private String back;
    @ApiModelProperty(value = "服务详情url",readOnly = true)
    private String serve;
    @ApiModelProperty(value = "营业执照url",readOnly = true)
    private String license;

    @OneToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "tb_company_business_scope",joinColumns = {@JoinColumn(name = "companyId")},inverseJoinColumns = {@JoinColumn(name = "businessScopeId",unique = true)})
    @ApiModelProperty(readOnly = true)
    public List<BusinessScope> businessScopeList;

//    经营范围
    @ApiModelProperty(readOnly = true)
    private String businessScope;
//    状态
    @ApiModelProperty(value = "状态,只有正常和冻结")
    private String status;
//    评价
    @ApiModelProperty(value = "评分")
    private double evaluate;

    @Transient
    private List<Long> businessScopeIdList;

    @Transient
    @ApiModelProperty(readOnly = true)
    private String distance;

    @Transient
    @ApiModelProperty(readOnly = true)
    private String Authorization;
}
