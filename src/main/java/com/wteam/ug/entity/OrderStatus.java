package com.wteam.ug.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "tb_order_status")
@ApiModel(value =
        "1:新建单," +
        "2:待分配," +
        "3:进行中," +
        "4:待付费," +
        "5:已完结," +
        "6:已取消," +
        "7:待评价")
public class OrderStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(readOnly = true)
    private int id;

    @Column(updatable = false,nullable = false,unique = true)
    private String name;

}
