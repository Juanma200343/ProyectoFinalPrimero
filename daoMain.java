package dao;


import java.util.List;

public class daoMain {

   public static void main(String[] args) {
       try {
           
           JdbcDao dao = new JdbcDao();
           
           dao.insertarEquipo("Equipo 35", 2015);  

           
           List<String> equipos = dao.obtenerEquipos();
           System.out.println("Equipos: ");
           for (String equipo : equipos) {
               System.out.println(equipo);  
           }


          
           dao.eliminarEquipo("Equipo 35");
           System.out.println("Equipo eliminado correctamente.");


           List<String> equiposActualizados = dao.obtenerEquipos();
           System.out.println("Equipos después de la eliminación: ");
           for (String equipo : equiposActualizados) {
               System.out.println(equipo);  
           }


       } catch (Exception e) {
           e.printStackTrace();  
       }
   }
}