package com.wteam.ug.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "tb_photo")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(readOnly = true)
    private long id;

//    @ManyToOne(cascade = CascadeType.MERGE)
//    @JoinColumn(name = "order_id")
//    @JsonIgnore
//    private Order order;
//    @ManyToOne(cascade = CascadeType.MERGE)
//    @JoinColumn(name = "employee_id")
//    @JsonIgnore
//    private Employee employee;
    @Column(updatable = false)
    private String url;
}
