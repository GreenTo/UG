package com.wteam.ug.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "sys_action")
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true,nullable = false)
    private String name;

    @Column(updatable = false)
    @ApiModelProperty(readOnly = true)
    private Date createDate;

    @Transient
    @ApiModelProperty(value = "权限id的list集合")
    private List<Long> permissions;

    @OneToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "sys_action_permission",
    joinColumns = {@JoinColumn(name = "action_id")},
    inverseJoinColumns = {@JoinColumn(name = "permission_id",unique = true)})
    @ApiModelProperty(readOnly = true)
    private Set<Permission> permissionSet;

//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public Date getTime() {
//        return time;
//    }
//
//    public void setTime(Date time) {
//        this.time = time;
//    }
//
//    public List<Long> getPermissions() {
//        return permissions;
//    }
//
//    public void setPermissions(List<Long> permissions) {
//        this.permissions = permissions;
//    }
//
//    public Set<Permission> getPermissionSet() {
//        return permissionSet;
//    }
//
//    public void setPermissionSet(Set<Permission> permissionSet) {
//        this.permissionSet = permissionSet;
//    }
}
