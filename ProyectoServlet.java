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
import javax.servlet.http.*;


import dao.JdbcDao;
import models.Equipo;
import models.Participantes;


public class ProyectoServlet extends HttpServlet {
    private JdbcDao dao;
    private static final long serialVersionUID = 2051990309999713971L;
	private TemplateEngine templateEngine;


    @Override
    public void init() throws ServletException {
		System.out.println("En init");
		ServletContext servletContext = getServletContext();
		JavaxServletWebApplication application = JavaxServletWebApplication.buildApplication(servletContext);
		WebApplicationTemplateResolver templateResolver = new WebApplicationTemplateResolver(application);
		templateResolver.setPrefix("/WEB-INF/templates/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode(TemplateMode.HTML);
		templateEngine = new TemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		System.out.println("En get");

		ServletContext servletContext = getServletContext();
		JavaxServletWebApplication application = JavaxServletWebApplication.buildApplication(servletContext);
		IServletWebExchange webExchange = application.buildExchange(request, response);
		WebContext context = new WebContext(webExchange, request.getLocale());
		response.setContentType("text/html;charset=UTF-8");
		// Datos de ejemplo: lista de nombres

		String pathInfo = request.getPathInfo(); // Ejemplo: /listarUsuarios o null
		
		if (pathInfo == null || pathInfo.trim().isEmpty() || pathInfo.trim().equalsIgnoreCase("/inicio")) {
			// Redirigir a la página de Principal
			templateEngine.process("inicio", context, response.getWriter());
			response.sendRedirect("inicio.html");
			
		} else if (pathInfo == null || pathInfo.trim().isEmpty() || pathInfo.trim().equalsIgnoreCase("/indexJ")) {
			templateEngine.process("indexJ", context, response.getWriter());
			response.sendRedirect("indexJ.html");
			
		} else if (pathInfo == null || pathInfo.trim().isEmpty() || pathInfo.trim().equalsIgnoreCase("/altaParticipante")) {
			templateEngine.process("altaParticipante", context, response.getWriter());
			response.sendRedirect("altaPartipante.html");
			
			}else if (pathInfo == null || pathInfo.trim().isEmpty() || pathInfo.trim().equalsIgnoreCase("/Fase")) {
				templateEngine.process("Fase", context, response.getWriter());
				response.sendRedirect("Fase.html");
				
			}else {
			// Dividimos por segmentos
		    String[] partes = pathInfo.substring(1).split("/");
		    String accion = partes[0]; // ej: "detalleUsuario"
		    String parametro1 = partes.length > 1 ? partes[1] : null;
		    
		    System.out.println("Servlet invocado. accion: " + accion);

		    switch (accion) {
			case "index":
				templateEngine.process("index", context, response.getWriter());
				break;
				
			default:
				// Ruta no reconocida
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "Ruta no válida: " + pathInfo);
			}
		}
	}


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getServletPath();
		String pathInfo = request.getPathInfo(); // Ejemplo: /listarUsuarios o null
		System.out.println(pathInfo);
		ServletContext servletContext = getServletContext();
		JavaxServletWebApplication application = JavaxServletWebApplication.buildApplication(servletContext);
		IServletWebExchange webExchange = application.buildExchange(request, response);
		WebContext context = new WebContext(webExchange, request.getLocale());

		switch (pathInfo) {
		case "/altaParticipante":
			System.out.println("en el alta de participantes");
			// Lógica para listar usuarios
		boolean correcto = altaParticipante(request, response, context);
		if (correcto) {
				context.setVariable("error", false);
				templateEngine.process("Fase", context, response.getWriter());
			} else {
				context.setVariable("error", true);
				templateEngine.process("altaParticipante", context, response.getWriter());

			}
			
			
			
			break;
		default:
			// Ruta no reconocida
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Ruta no válida: " + path);
		}
	}
    
    boolean altaParticipante(HttpServletRequest request, HttpServletResponse response, WebContext context)
			throws ServletException, IOException {

	String nombre = request.getParameter("nombre");
		String apellidos = request.getParameter("apellidos");
		String telefono = request.getParameter("telefono");
		String correo = request.getParameter("correo");
		String equipo = request.getParameter("equipo");

		boolean correcto = false;

		try {
			System.out.println("¿Dime tu id?");
			Participantes dao = new Participantes(1, "Juan", "Miranda");
			correcto = dao.validarParticipante();
		} catch (Exception e) {
			e.printStackTrace();

		}
		
    	return true;
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
