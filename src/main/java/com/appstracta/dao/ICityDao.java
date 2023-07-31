package com.appstracta.dao;

import java.util.List;

import com.appstracta.bean.CityBean;
import com.appstracta.dto.CityDto;
import com.appstracta.exceptions.InternalException;

public interface ICityDao {

	List<CityBean> obtenerTodos() throws InternalException;

	CityBean obtenerPorId(int cityId) throws InternalException;

	CityBean guardar(CityDto cityDto) throws InternalException;

	CityBean actualizar(CityDto cityDto, int cityId) throws InternalException;

	int borrar(int cityId) throws InternalException;

	String obtenerPorIdStore(int cityId) throws InternalException;

}
