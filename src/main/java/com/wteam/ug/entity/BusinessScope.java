package com.wteam.ug.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tb_business_scope")
//@Data
public class BusinessScope {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @ApiModelProperty(readOnly = true)
    private long id;
    @Column(nullable = false)
    private String name;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "parentId")
    @JsonIgnore
    private BusinessScope parent;
    @OneToMany(mappedBy = "parent",cascade = {CascadeType.REMOVE,CascadeType.MERGE},fetch = FetchType.EAGER)
    private Set<BusinessScope> childrenSet;

    @Transient
    private long parentId;

    @Override
    public String toString() {
        return "BusinessScope{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", childrenSet=" + childrenSet +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BusinessScope getParent() {
        return parent;
    }

    public void setParent(BusinessScope parent) {
        this.parent = parent;
    }

    public Set<BusinessScope> getChildrenSet() {
        return childrenSet;
    }

    public void setChildrenSet(Set<BusinessScope> childrenSet) {
        this.childrenSet = childrenSet;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }
}
