package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.servlet.IServletWebExchange;
import org.thymeleaf.web.servlet.JavaxServletWebApplication;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


import dao.JdbcDao;
import models.Equipo;
import models.Participantes;

@WebServlet("/ProyectoServlet")
public class ProyectoServlet extends HttpServlet {
    private JdbcDao dao;
    private static final long serialVersionUID = 2051990309999713971L;
	private TemplateEngine templateEngine;


    @Override
    public void init() throws ServletException {
		System.out.println("En init");
		try {
			dao=new JdbcDao();
		}catch(Exception e) {
			
			throw new ServletException("Error al conectarse en la base de Datos",e);
		}
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		System.out.println("En get");

	
		// Datos de ejemplo: lista de nombres

		String action = request.getParameter("action"); // Ejemplo: /listarUsuarios o null
		 if (action == null || action.trim().isEmpty() || "inicio".equalsIgnoreCase(action)) {
		        // Verifica si la URL ya es 'inicio.html' para evitar redirección infinita
		        String currentPath = request.getRequestURI();
		        if (!currentPath.endsWith("inicio.html")) {
		            response.sendRedirect(request.getContextPath() + "/inicio.html");
		        }
		
			
		} else if ("indexJ".equals(action)) {
			response.sendRedirect("indexJ.html");
			
		} else if ("altaParticipante".equals(action)) {
			response.sendRedirect("altaPartipante.html");
			
			}else if ("Fase".equals(action)) {
				response.sendRedirect("Fase.html");
				
			}else {
			
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "Ruta no válida: " + action);
			}
		}
	


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pathInfo = request.getPathInfo();

	if("/altaParticipante".equals(pathInfo)) {
		boolean participanteCreado = altaParticipante(request,response);
		if(participanteCreado) {
			
			response.sendRedirect("inicio.html");
		}
		else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND,"ruta no valida" + pathInfo);
		}
	}
}
    boolean altaParticipante(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	String nombre = request.getParameter("nombre");
		String apellidos = request.getParameter("apellidos");
		String telefono = request.getParameter("telefono");
		String correo = request.getParameter("correo");
		String equipo = request.getParameter("equipo");

		boolean correcto = false;

	    try {
	        // Crear un nuevo objeto Participantes con los datos recibidos
	        Participantes participante = new Participantes(nombre, apellidos, telefono, correo, equipo);

	        // Validar e insertar el participante en la base de datos
	        correcto = participante.validarParticipante(); // Método que debes implementar en la clase Participantes
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return correcto; // Retorna true si la inserción fue exitosa, de lo contrario false
	}

    private void generarFormulario(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
 
        String fase = request.getParameter("fase");
 
        if (fase == null || fase.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"El parámetro 'fase' es obligatorio.\"}");
            return;
        }
 
        try {
            List<Equipo> equiposDisponibles = obtenerEquiposDeBD(fase);
 
            if (equiposDisponibles == null || equiposDisponibles.size() < 2 || equiposDisponibles.size() % 2 != 0) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Número de equipos inválido para la fase.\"}");
                return;
            }
 
      
            response.getWriter().write("{\"mensaje\": \"Equipos obtenidos correctamente\"}");
 
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Error interno al obtener los equipos.\"}");
        }
    }
  

    private List<Equipo> obtenerEquiposDeBD(String fase) throws SQLException {
        List<Equipo> equipos = new ArrayList<>();
        String sql = "SELECT DISTINCT e.idEquipo, e.nombreEquipo FROM Equipo e " +
                     "JOIN CruceEquipo ce ON e.idEquipo = ce.idEquipo " +
                     "JOIN Cruce c ON ce.idCruce = c.idCruce WHERE c.idFase = ?";

        int idFase = obtenerIdFase(fase);
        if (idFase == -1) return equipos;

        try (var conn = dao.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idFase);
            try (var rs = stmt.executeQuery()) {
                while (rs.next()) {
                    equipos.add(new Equipo(rs.getInt("idEquipo"), rs.getString("nombreEquipo")));
                }
            }
        }

        return equipos;
    }

    private int obtenerIdFase(String fase) {
        switch (fase.toLowerCase()) {
            case "primera fase": return 1;
            case "segunda fase": return 2;
            case "semifinal": return 3;
            case "final": return 4;
            default: return -1;
        }
    }
}
