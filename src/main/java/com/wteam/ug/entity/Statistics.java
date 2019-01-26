package com.wteam.ug.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "tb_statistics")
public class Statistics {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(readOnly = true)
	private long id;
	@Column(updatable = false)
	@JsonFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(readOnly = true)
	private Date date;
	private long registeredUsers;
	private long activeUsers;
	private long workOrders;
	private long businessVisits;
}
