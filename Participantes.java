package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.JdbcDao;



public class Participantes {

	int id_participante;
	String nombre ;
	String apellidos;
	
	public Participantes(int id_participante, String nombre, String apellidos) {
		super();
		this.id_participante = id_participante;
		this.nombre = nombre;
		this.apellidos = apellidos;
	}

	public int getId_participante() {
		return id_participante;
	}

	public void setId_participante(int id_participante) {
		this.id_participante = id_participante;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	} 
	
	
	@Override
	public String toString() {
		return "Participantes [id_participante=" + id_participante + ", nombre=" + nombre + ", apellidos=" + apellidos
				+ "]";
	}
	
	

	public boolean validarParticipante() {
		
		
		return true;
	}
	
	
}
