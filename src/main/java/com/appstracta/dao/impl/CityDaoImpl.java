package com.appstracta.dao.impl;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.appstracta.bean.CityBean;
import com.appstracta.bean.CountryBean;
import com.appstracta.connection.Conexion;
import com.appstracta.dao.ICityDao;
import com.appstracta.dto.CityDto;
import com.appstracta.exceptions.InternalException;

public class CityDaoImpl implements ICityDao {

	@Override
	public List<CityBean> obtenerTodos() throws InternalException {
		Conexion conexion = null;
		List<CityBean> ciudades = new ArrayList<>();

		try {
			String sql = "SELECT city.city_id," +
					" city.city," +
					" city.country_id," +
					" city.last_update," +
					" country.country_id," +
					" country.country, " +
					" country.last_update AS last_update_country " +
					"FROM country " +
					"INNER JOIN city ON country.country_id = city.country_id " +
					"ORDER BY city.city_id";
			conexion = new Conexion();
			conexion.conectar();

			try(PreparedStatement ps = conexion.getConnection().prepareStatement(sql)) {
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						CityBean cityBean = new CityBean();

						cityBean.setCityId(rs.getInt("city_id"));
						cityBean.setCity(rs.getString("city"));
						cityBean.setUpdate(rs.getDate("last_update"));
						cityBean.setCountry(new CountryBean(rs.getInt("country_id"), rs.getString("country"), rs.getDate("last_update_country")));

						ciudades.add(cityBean);
					}
				}
			}

			return ciudades;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new InternalException("Ocurrió un error al obtener los datos de las ciudades");
		} finally {
			if (conexion != null) {
				try {
					conexion.cerrar();
				} catch (Exception ex) {
					throw new InternalException(ex.getMessage());
				}
			}
		}
	}

	@Override
	public CityBean obtenerPorId(int cityId) throws InternalException {
		Conexion conexion = null;
		CityBean cityBean = new CityBean();

		try {
			String sql = "SELECT city.city_id," +
					" city.city," +
					" city.country_id," +
					" city.last_update," +
					" country.country_id," +
					" country.country, " +
					" country.last_update AS last_update_country " +
					"FROM country " +
					"INNER JOIN city ON country.country_id = city.country_id " +
					"WHERE city.city_id = ?";
			conexion = new Conexion();
			conexion.conectar();

			try (PreparedStatement ps = conexion.getConnection().prepareStatement(sql)) {
				ps.setInt(1, cityId);

				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						cityBean.setCityId(rs.getInt("city_id"));
						cityBean.setCity(rs.getString("city"));
						cityBean.setUpdate(rs.getDate("last_update"));
						cityBean.setCountry(new CountryBean(rs.getInt("country_id"), rs.getString("country"), rs.getDate("last_update_country")));
					}
				}
			}

			return cityBean;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new InternalException(String.format("Ocurrió un error al obtener los datos de la ciudad con id %d.", cityId));
		} finally {
			if (conexion != null) {
				try {
					conexion.cerrar();
				} catch (Exception ex) {
					throw new InternalException(ex.getMessage());
				}
			}
		}
	}

	@Override
	public CityBean guardar(CityDto cityDto) throws InternalException {
		Conexion conexion = null;
		CityBean cityBean = null;

		try {
			conexion = new Conexion();
			String sql = "INSERT INTO city (city, country_id, last_update) VALUES(?,?,?)";
			conexion.conectar();
			try (PreparedStatement ps = conexion.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				ps.setString(1, cityDto.getCity().trim());
				ps.setInt(2, cityDto.getCountryId());
				ps.setDate(3, new java.sql.Date(cityDto.getUpdate().getTime()));
				ps.executeUpdate(); // Para isert y update

				// Recuperamos el id del valor que se acaba de insertar
				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {
					cityBean = this.obtenerPorId(rs.getInt(1));
				}
			}

			return cityBean;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new InternalException("Ocurrión un error al insertar los datos de la ciudad");
		} finally {
			if (conexion != null) {
				try {
					conexion.cerrar();
				} catch (Exception ex) {
					throw new InternalException(ex.getMessage());
				}
			}
		}
	}

	@Override
	public CityBean actualizar(CityDto cityDto, int cityId) throws InternalException {
		Conexion conexion = null;
		CityBean cityBean = null;

		try {
			int registros;
			conexion = new Conexion();
			String sql = "UPDATE city SET city = ?, country_id = ?, last_update = ? WHERE city_id = ?";
			conexion.conectar();
			try (PreparedStatement ps = conexion.getConnection().prepareStatement(sql)) {
				ps.setString(1, cityDto.getCity().trim());
				ps.setInt(2, cityDto.getCountryId());
				ps.setDate(3, new java.sql.Date(cityDto.getUpdate().getTime()));
				ps.setInt(4, cityId);
				registros = ps.executeUpdate(); // Para insert y update

				if (registros == 0) {
					throw new InternalException(String.format("El id %d no se encuentra registrado en la base de datos", cityId));
				}

				cityBean = this.obtenerPorId(cityId);
			}

			return cityBean;
		} catch (InternalException ex) {
			throw ex;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new InternalException("Ocurrión un error al actualizar los datos de la ciudad");
		} finally {
			if (conexion != null) {
				try {
					conexion.cerrar();
				} catch (Exception ex) {
					throw new InternalException(ex.getMessage());
				}
			}
		}
	}

	@Override
	public int borrar(int cityId) throws InternalException {
		Conexion conexion = null;

		try {
			int registros;
			conexion = new Conexion();
			String sql = "DELETE FROM city WHERE city_id = ?";
			conexion.conectar();
			try (PreparedStatement ps = conexion.getConnection().prepareStatement(sql)) {
				ps.setInt(1, cityId);
				registros = ps.executeUpdate(); // Para insert y update

				if (registros == 0) {
					throw new InternalException(String.format("El id %d no se encuentra registrado en la base de datos", cityId));
				}
			}

			return cityId;
		} catch (InternalException ex) {
			throw ex;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new InternalException("Ocurrión un error al borrar los datos de la ciudad");
		} finally {
			if (conexion != null) {
				try {
					conexion.cerrar();
				} catch (Exception ex) {
					throw new InternalException(ex.getMessage());
				}
			}
		}
	}


	@Override
	public String obtenerPorIdStore(int cityId) throws InternalException {
		Conexion conexion = null;
		String ciudad = null;

		try {
			String sql = "{call sp_city(?)}";
			conexion = new Conexion();
			conexion.conectar();

			try (CallableStatement cs = conexion.getConnection().prepareCall(sql)) {
				cs.setInt(1, cityId);

				try (ResultSet rs = cs.executeQuery();) {
					while (rs.next()) {
						ciudad = rs.getString("city");
					}
				}
			}

			return ciudad;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new InternalException(String.format("Ocurrió un error al obtener el nombre de la ciudad con id %d.", cityId));
		} finally {
			if (conexion != null) {
				try {
					conexion.cerrar();
				} catch (Exception ex) {
					throw new InternalException(ex.getMessage());
				}
			}
		}
	}

}
