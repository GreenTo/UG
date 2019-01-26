package com.wteam.ug.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;



@Entity
@Table(name = "tb_menu_item")
public class MenuItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@ApiModelProperty(readOnly = true)
	private Integer id;
	
	@ManyToMany
    @JoinTable(name = "tb_menu_menuItem", joinColumns = { @JoinColumn(name = "menuItemId", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "menuId", referencedColumnName = "id") })
	@JsonIgnore
	private Set<Menu> menuSet = new HashSet<Menu>();
	
	@ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "parentId")
    @JsonIgnore
    private MenuItem parent;
    @OneToMany(mappedBy = "parent",cascade = {CascadeType.REMOVE,CascadeType.MERGE},fetch = FetchType.EAGER)
	@ApiModelProperty(readOnly = true)
    private Set<MenuItem> childrenSet;
    
    @Column(insertable=false, updatable=false)
	@Transient
    private Integer parentId;
    private Integer menuId;
    
	private String name;
	@ApiModelProperty(value = "是否显示")
	private boolean display;
	@ApiModelProperty(value = "菜单项页面")
	private String pageUrl;
	@ApiModelProperty(value = "菜单项路由")
	private String routing;
	@ApiModelProperty(value = "菜单项控制器")
	private String controller;
	private String icon;
	
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
	public boolean isDisplay() {
		return display;
	}
	public void setDisplay(boolean display) {
		this.display = display;
	}
	public String getPageUrl() {
		return pageUrl;
	}
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	public String getRouting() {
		return routing;
	}
	public void setRouting(String routing) {
		this.routing = routing;
	}
	public String getController() {
		return controller;
	}
	public void setController(String controller) {
		this.controller = controller;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Set<Menu> getMenuSet() {
		return menuSet;
	}
	public void setMenuSet(Set<Menu> menuSet) {
		this.menuSet = menuSet;
	}
	public MenuItem getParent() {
		return parent;
	}
	public void setParent(MenuItem parent) {
		this.parent = parent;
	}
	public Set<MenuItem> getChildrenSet() {
		return childrenSet;
	}
	public void setChildrenSet(Set<MenuItem> childrenSet) {
		this.childrenSet = childrenSet;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}


	
}
