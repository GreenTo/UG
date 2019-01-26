package com.wteam.ug.baidu;

import com.wteam.ug.entity.Company;

import javax.persistence.*;

@Entity
@Table(name = "tb_point")
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

	private double lng;
	
	private double lat;
	
	private String address;

	@OneToOne
    @JoinColumn(name = "company_id")
    private Company company;
	
	public Point(){}
	
	public Point(double lat,double lng,String address){
		this.lat = lat;
		this.lng = lng;
		this.address = address;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
