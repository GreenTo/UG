package com.wteam.ug.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "tb_customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @ApiModelProperty(readOnly = true)
    private long id;
    @Column(unique = true,nullable = false,length = 20)
    private String username;
    @Column(nullable = false)
    @JsonIgnore
    private String password;
    @Column(length = 11)
    private String phone;

    @Column(updatable = false)
    @ApiModelProperty(readOnly = true)
    private Date createDate;
    @Transient
    private String code;

    @Transient
    @JsonIgnore
    private String vcode;

//    @OneToMany(cascade = CascadeType.MERGE)
//    @JoinColumn(name = "customer_id")
//    @JoinTable(name = "customer_order", joinColumns = @JoinColumn(name = "customer_id"), inverseJoinColumns = @JoinColumn(name = "order_id", unique = true))
//    private List<Order> orderList;

//    @OneToMany(cascade = CascadeType.MERGE)
//    @JoinColumn(name = "customer_id")
//    @JoinTable(name = "customer_address",joinColumns = @JoinColumn(name = "customer_id"),inverseJoinColumns = @JoinColumn(name = "address_id",unique = true))
//    private List<Address> addressList;


}
