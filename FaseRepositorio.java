package repository;

import models.Fase;
import java.sql.*;
import dao.JdbcDao;

public class FaseRepositorio {

	 private JdbcDao jdbcDao;

	    public FaseRepositorio() throws Exception {
	        this.jdbcDao = new JdbcDao();
	    }

	
	    public void agregarFase(Fase fase) throws SQLException {
	        String query = "INSERT INTO fase (nombreFase) VALUES (?)";

	        try (Connection conn = jdbcDao.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
	            stmt.setString(1, fase.getNombreFase());
	            stmt.executeUpdate();
	        } catch (SQLException e) {
	            throw new SQLException("Error al insertar la fase en la base de datos", e);
	        }
	    }

	   
	    public Fase obtenerFasePorId(int idFase) throws SQLException {
	        String query = "SELECT * FROM fase WHERE idFase = ?";
	        Fase fase = null;

	        try (Connection conn = jdbcDao.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
	            stmt.setInt(1, idFase);

	            ResultSet rs = stmt.executeQuery();
	            if (rs.next()) {
	            	int id = rs.getInt("idFase"); 
	                String nombre = rs.getString("nombreFase"); 
	                
	                fase = new Fase(id,nombre);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            throw new SQLException("Error al obtener la fase de la base de datos", e);
	        }

	        return fase;
	    }

	   
	    public void actualizarFase(Fase fase) throws SQLException {
	        String query = "UPDATE fase SET nombreFase = ? WHERE idFase = ?";

	        try (Connection conn = jdbcDao.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
	            stmt.setString(1, fase.getNombreFase());
	            stmt.setInt(2, fase.getIdFase());
	            stmt.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	            throw new SQLException("Error al actualizar la fase en la base de datos", e);
	        }
	    }

	    
	    public void eliminarFase(int idFase) throws SQLException {
	        String query = "DELETE FROM fase WHERE idFase = ?";

	        try (Connection conn = jdbcDao.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
	            stmt.setInt(1, idFase);
	            stmt.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	            throw new SQLException("Error al eliminar la fase en la base de datos", e);
	        }
	    }
}