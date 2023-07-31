package com.appstracta.connection;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Conexion {

	private Connection connection;

	public void conectar() {
		try {
			// Conexión a través de JNDI
			Context initContext = new InitialContext();
			DataSource dataSource = (DataSource)initContext.lookup("java:/dsSakila");

			this.connection = dataSource.getConnection();
		} catch (SQLException | NamingException e) {
			e.printStackTrace();
		}
	}

	public void cerrar() {
		if (this.connection != null) {
			try {
				// De acuerdo a la lectura es el paso 4
				this.connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public Connection getConnection() {
		return this.connection;
	}

}
