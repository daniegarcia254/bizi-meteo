/*
 * AUTOR: Javier Briz Alastrue
 * NIA: 576695
 */

package stw.p6;

import java.sql.*;

public class Mysql {
	Connection conn;
	public Mysql() {
		try {
			String myDriver = "com.mysql.jdbc.Driver";
			String myUrl = "jdbc:mysql://localhost/$DB";
			Class.forName(myDriver);
			this.conn = DriverManager.getConnection(myUrl, $PASSWORD, $USER);
		} catch (Exception e) {
			System.out.println("No se pudo conectar a la base de datos");
			System.out.println(e);
			this.conn = null;
		}
	}

	public void getAll() {
		try {
			String query = "select * from prediccion";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			ResultSet rs = preparedStmt.executeQuery();

			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					if (i > 1) System.out.print(",  ");
					String columnValue = rs.getString(i);
					System.out.print(columnValue + " " + rsmd.getColumnName(i));
				}
				System.out.println("");
			}
		} catch (Exception e) {}
	}

	public String getFila(String dia, String intervalo, String campo) {
		try {
			String query = "select valor from prediccion WHERE dia=? AND intervalo=? AND campo=? LIMIT 1";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, dia);
			preparedStmt.setString(2, intervalo);
			preparedStmt.setString(3, campo);

			ResultSet rs = preparedStmt.executeQuery();
			rs.next();
			return (rs.getString(1));
		} catch (Exception e) {
			return "";
		}
	}

	public void setFila(String dia, String intervalo, String campo, String valor) {
		try {
			String query = "insert into prediccion (dia,intervalo,campo,valor) values (?,?,?,?)";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, dia);
			preparedStmt.setString(2, intervalo);
			preparedStmt.setString(3, campo);
			preparedStmt.setString(4, valor);

			preparedStmt.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void clear() {
		try {
			String query = "delete from prediccion";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
