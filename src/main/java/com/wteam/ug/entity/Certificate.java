package com.wteam.ug.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tb_certificate")
public class Certificate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@ApiModelProperty(readOnly = true)
	private long id;

	@Column(updatable = false)
	private Long certificateId;
	private String summary;
	private Double debitAmount;
	private Double creditAmount;
	private Double overr;
	private String username;
	@Transient
	private Long userId;

//	@ManyToMany
//    @JoinTable(name = "tb_bill_certificate", joinColumns = { @JoinColumn(name = "certificate_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "bill_id", referencedColumnName = "id") })
//	@JsonIgnore
//	private Set<Bill> billSet = new HashSet<Bill>();
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="bill_id")
	@JsonIgnore
//	@Basic(fetch=FetchType.LAZY)
	private Bill bill;
	
	
	@Column(updatable = false)
	@ApiModelProperty(readOnly = true)
	private String subjectName;
	@Column(updatable = false)
	@ApiModelProperty(readOnly = true)
	private String generalCategory;
	@Transient
	private Integer subjectId;

	@Column(updatable = false)
//	@JsonFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(readOnly = true)
	private Date creatDate;

	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getCertificateId() {
		return certificateId;
	}

	public void setCertificateId(Long certificateId) {
		this.certificateId = certificateId;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Double getDebitAmount() {
		return debitAmount;
	}

	public void setDebitAmount(Double debitAmount) {
		this.debitAmount = debitAmount;
	}

	public Double getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(Double creditAmount) {
		this.creditAmount = creditAmount;
	}

	public Double getOverr() {
		return overr;
	}

	public void setOverr(Double overr) {
		this.overr = overr;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getGeneralCategory() {
		return generalCategory;
	}

	public void setGeneralCategory(String generalCategory) {
		this.generalCategory = generalCategory;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public Date getCreatDate() {
		return creatDate;
	}

	public void setCreatDate(Date creatDate) {
		this.creatDate = creatDate;
	}
	
	
}
