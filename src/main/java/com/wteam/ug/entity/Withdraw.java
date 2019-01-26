package com.wteam.ug.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "tb_withdraw")
public class Withdraw {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@ApiModelProperty(readOnly = true)
	private long id;

	private String username;
	private String company;
	private double amount;
	private String type;
	
	@Column(updatable = false)
	@JsonFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(readOnly = true)
	private Date creatDate;
}
