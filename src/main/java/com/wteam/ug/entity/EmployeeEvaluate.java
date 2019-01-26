package com.wteam.ug.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "tb_employee_evaluate")
public class EmployeeEvaluate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @ApiModelProperty(readOnly = true)
    private long id;
    @Column(updatable = false)
    private double score;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

//    @Transient
//    @JsonIgnore
//    private long employeeId;
}
