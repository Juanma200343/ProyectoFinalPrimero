package controlador;

import models.Fase;
import repository.FaseRepositorio;
import java.sql.SQLException;

public class GestionaFase {

    private FaseRepositorio faseRepositorio;

   
    public GestionaFase() {
        try {
         
            this.faseRepositorio = new FaseRepositorio();
        } catch (Exception e) {
           
            System.err.println("Error al inicializar FaseRepositorio: " + e.getMessage());
         
        }
    }

   
    public boolean agregarFase(String nombreFase) {
        Fase fase = new Fase();
        fase.setNombreFase(nombreFase);

        try {
       
            faseRepositorio.agregarFase(fase);
            return true;
        } catch (SQLException e) {
          
            System.err.println("Error al agregar la fase: " + e.getMessage());
            return false;
        }
    }

   
    public static void main(String[] args) {
        GestionaFase gestionaFase = new GestionaFase();
        
        if (gestionaFase.agregarFase("Primera Fase")) {
            System.out.println("Primera fase agregada correctamente.");
        }

        if (gestionaFase.agregarFase("Segunda Fase")) {
            System.out.println("Segunda fase agregada correctamente.");
        }

        if (gestionaFase.agregarFase("Semifinal")) {
            System.out.println("Semifinal agregada correctamente.");
        }

        if (gestionaFase.agregarFase("Final")) {
            System.out.println("Final agregada correctamente.");
        }/*else {
        	
        	
        	
        }*/
    }
    
    
    
    
    private String menu(String menu) {
        switch (menu) {
            case "Menu": return "Menu";
            case "Puntuaciones": return "Puntuaciones";
            case "Pruebas": return "Pruebas";
            case "Listados": return "Listados";
            default: return "Inicio";
        }
    }
}
