package com.wteam.ug.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_bill")
public class Bill {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@ApiModelProperty(readOnly = true)
	private long id;

	private String username;
	private Double overr;
	
//	@ManyToMany(targetEntity = Certificate.class,fetch=FetchType.EAGER)
	@OneToMany(mappedBy="bill",cascade={CascadeType.ALL},fetch = FetchType.EAGER)
//    @JoinTable(name = "tb_bill_certificate", joinColumns = { @JoinColumn(name = "bill_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "certificate_id", referencedColumnName = "id") })
	@JsonIgnore
    private List<Certificate> certificateSet = new ArrayList<Certificate>();

	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Double getOverr() {
		return overr;
	}

	public void setOverr(Double overr) {
		this.overr = overr;
	}

	public List<Certificate> getCertificateSet() {
		return certificateSet;
	}

	public void setCertificateSet(List<Certificate> certificateSet) {
		this.certificateSet = certificateSet;
	}
	
	
	
}
