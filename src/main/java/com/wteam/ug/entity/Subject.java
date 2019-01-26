package com.wteam.ug.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "tb_subject")
public class Subject {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@ApiModelProperty(readOnly = true)
	private Integer id;

	private String coding;
	private String name;
	@Transient
	private Integer generalCategoryId;
	private String generalCategory;
	private String category;
	@Transient
	private Integer directionId;
	private String direction;




}
