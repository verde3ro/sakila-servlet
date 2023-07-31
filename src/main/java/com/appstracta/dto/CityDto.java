package com.appstracta.dto;

import java.io.Serializable;
import java.util.Date;

public class CityDto implements Serializable {

	private static final long serialVersionUID = -1922537763157213337L;
	private String city;
	private Date update;
	private int countryId;

	public CityDto() {
	}

	public CityDto(String city, Date update, int countryId) {
		this.city = city;
		this.update = update;
		this.countryId = countryId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Date getUpdate() {
		return update;
	}

	public void setUpdate(Date update) {
		this.update = update;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

}
