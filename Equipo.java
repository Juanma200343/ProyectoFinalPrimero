package models;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.JdbcDao;

public class Equipo {
    private int idEquipo;
    private String nombreEquipo;
    private int anioCreacion;

  
    public Equipo() {
        super();
    }

   
    public Equipo(String nombreEquipo, int anioCreacion) {
        super();
        this.nombreEquipo = nombreEquipo;
        this.anioCreacion = anioCreacion;
    }

    
    public Equipo(int idEquipo) {
        super();
        this.idEquipo = idEquipo;
    }

   
    public Equipo(int idEquipo, String nombreEquipo, int anioCreacion) {
        super();
        this.idEquipo = idEquipo;
        this.nombreEquipo = nombreEquipo;
        this.anioCreacion = anioCreacion;
    }

   
  

	public Equipo(int idEquipo, String nombreEquipo) {
		super();
		this.idEquipo = idEquipo;
		this.nombreEquipo = nombreEquipo;
	}


	public void cargarDetallesEquipo() throws SQLException, Exception {
        JdbcDao jdbcDao = new JdbcDao();
        String query = "SELECT nombreEquipo, añoCreacion FROM Equipo WHERE idEquipo = ?";

        try (Connection conn = jdbcDao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, this.idEquipo);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    this.nombreEquipo = rs.getString("nombreEquipo");
                    this.anioCreacion = rs.getInt("añoCreacion");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al cargar los detalles del equipo", e);
        }
    }


    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    public int getAnioCreacion() {
        return anioCreacion;
    }

    public void setAnioCreacion(int anioCreacion) {
        this.anioCreacion = anioCreacion;
    }
}
