package com.appstracta.bean;

import java.io.Serializable;
import java.util.Date;

public class CityBean implements Serializable {

	private static final long serialVersionUID = 446231299383117017L;
	private int cityId;
	private String city;
	private String status;
	private Date update;
	private CountryBean country;

	public CityBean() {
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getUpdate() {
		return update;
	}

	public void setUpdate(Date update) {
		this.update = update;
	}

	public CountryBean getCountry() {
		return country;
	}

	public void setCountry(CountryBean country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "CityBean [cityId=" + cityId + ", city=" + city + ", status=" + status + ", update=" + update + ", country="
				+ country + "]";
	}

}
