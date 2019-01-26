package com.wteam.ug.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_menu")
public class Menu {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@ApiModelProperty(readOnly = true)
	private Integer id;
	@Column(unique = true,nullable = false)
	private String name;

	@ManyToMany(targetEntity = MenuItem.class,fetch=FetchType.EAGER)
    @JoinTable(name = "tb_menu_menuItem", joinColumns = { @JoinColumn(name = "menuId", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "menuItemId", referencedColumnName = "id") })
	@JsonIgnore
    private Set<MenuItem> menuItemSet = new HashSet<MenuItem>();

	@Column(updatable = false)
	@ApiModelProperty(readOnly = true)
	private Date createDate;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Set<MenuItem> getMenuItemSet() {
		return menuItemSet;
	}
	public void setMenuItemSet(Set<MenuItem> menuItemSet) {
		this.menuItemSet = menuItemSet;
	}
	

	
}
