package dao;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JdbcDao {
    private String url;
    private String user;
    private String password;

    public JdbcDao() throws Exception {
        Properties props = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties")) {
           
            props.load(input);

            url = props.getProperty("db.url");
            user = props.getProperty("db.user");
            password = props.getProperty("db.password");

        }
    }


    public Connection getConnection() throws SQLException {
    	Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
        	e.printStackTrace();
        }
		return conn;
    }

    public void insertarEquipo(String nombreEquipo, int anioCreacion) throws SQLException {
        String query = "INSERT INTO Equipo (nombreEquipo, anioCreacion) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, nombreEquipo);
            stmt.setInt(2, anioCreacion);

            stmt.executeUpdate();
            System.out.println("¡Equipo insertado correctamente!");
        } catch (SQLException e) {
            System.err.println("Error al insertar equipo: " + e.getMessage());
        }
    }
    
    public List<String> obtenerEquipos() throws SQLException {
        String query = "SELECT nombreEquipo FROM Equipo";
        List<String> equipos = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                equipos.add(rs.getString("nombreEquipo"));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener equipos: " + e.getMessage());
        }

        return equipos;
    }
    
    public void eliminarEquipo(String nombreEquipo) throws SQLException {
        String query = "DELETE FROM Equipo WHERE nombreEquipo = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
     
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setString(1, nombreEquipo);  
     
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("¡Equipo eliminado correctamente!");
            } else {
                System.out.println("No se encontró el equipo con el nombre especificado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
    }
  
  }




