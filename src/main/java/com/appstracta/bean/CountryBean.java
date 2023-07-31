package com.appstracta.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CountryBean implements Serializable {

	private static final long serialVersionUID = 8026220459167365031L;
	private int countryId;
	private String country;
	private Date update;
	private List<CityBean> cities;

	public CountryBean() {}

	public CountryBean(int countryId) {
		this.countryId = countryId;
	}

	public CountryBean(int countryId, String country, Date update) {
		this.countryId = countryId;
		this.country = country;
		this.update = update;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Date getUpdate() {
		return update;
	}

	public void setUpdate(Date update) {
		this.update = update;
	}

	public List<CityBean> getCities() {
		return cities;
	}

	public void setCities(List<CityBean> cities) {
		this.cities = cities;
	}

	@Override
	public String toString() {
		return "CountryBean [countryId=" + countryId + ", country=" + country + ", update=" + update + ", cities=" + cities + "]";
	}

}

