package com.wteam.ug.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "tb_order_record")
public class OrderRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(readOnly = true)
    private long id;
    @Column(updatable = false)
    @ApiModelProperty(readOnly = true)
    private Date createDate;

//    @ManyToOne
//    @JoinColumn(name = "company_id")
//    private Company company;
//
//    @ManyToOne
//    @JoinColumn(name = "employee_id")
//    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(updatable = false)
    private String title;

    @Column(updatable = false)
    private String msg;

}
