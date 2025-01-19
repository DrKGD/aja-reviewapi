
package servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.LoginDTO;
import model.RegisterDTO;
import model.User;
import service.UserService;
import shared.servlet.JSONServletSave;
import shared.servlet.JSONUtil;

@WebServlet(urlPatterns = { "/api/auth", "/api/auth/*" })
public class AuthServlet extends HttpServlet implements 
	JSONServletSave {
	private UserService userService;

	@Override
	public void init() throws ServletException {
		this.userService = (UserService) getServletContext().getAttribute("userService");
	}

	// GET /api/auth
	// Check whether or not the JSESSION token provided by user
	// Is valid, thus return user informations
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false); // Don't create a new session

		ObjectMapper obj   = new ObjectMapper();
		Map<String, Object> jsonResponse = new HashMap<>();
		if (session != null) {
			jsonResponse.put("kind", "AUTH");
			jsonResponse.put("username", (String)session.getAttribute("user"));
			jsonResponse.put("role", (String)session.getAttribute("role"));
			jsonResponse.put("id", (Integer)session.getAttribute("id"));
		} else {
			jsonResponse.put("kind", "GUEST");
		}

		resp.setStatus(HttpServletResponse.SC_OK);
		resp.getWriter().write(obj.writeValueAsString(jsonResponse));
	}


	// POST /api/auth/register
	// POST /api/auth/login
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();

		if(pathInfo == null || ("/").equals(pathInfo)) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL: " + pathInfo);
		}

		// Register new user
		else if(("/register").equals(pathInfo)) {
			JSONServletSave.super.save(req, resp, RegisterDTO.class, 
				(data) -> userService.saveNewUser(data));
		}

		// Kill current session (if present)
		else if(("/logout").equals(pathInfo)) {
			HttpSession oldSession = req.getSession(false);
			if (oldSession != null) {
				oldSession.invalidate();
			}

			resp.setStatus(HttpServletResponse.SC_OK);
		}

		// Attempting to login
		else if(("/login").equals(pathInfo)) {
			LoginDTO user = JSONUtil.decode(req, LoginDTO.class);

			ObjectMapper obj   = new ObjectMapper();
			Map<String, String> jsonResponse = new HashMap<>();

			// Bad credentials
			if (!userService.validateCredentials(user)){
				jsonResponse.put("kind", "BAD_CREDENTIALS");
				jsonResponse.put("message", "Bad credentials!");
			}

			// Start new User Session
			else {
				User u = userService.getUserWithUsername(user.getUsername());

				// Kill previous session
				// Prevent the "Session Fixation"
				HttpSession oldSession = req.getSession(false);
				if (oldSession != null) {
					oldSession.invalidate();
				}

				HttpSession session = req.getSession(true);
				session.setAttribute("user", u.getUsername());	// Set user
				session.setAttribute("role", u.getRole());			// Set role
				session.setAttribute("id", u.getId());					// Set id
				session.setMaxInactiveInterval(10 * 60);				// Timeout in 10 minutes

				jsonResponse.put("kind", "OK");
				jsonResponse.put("message", "Login successful!");
			}

			// Send response
			resp.setContentType("application/json");
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.getWriter().write(obj.writeValueAsString(jsonResponse));
		}

		else {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL: " + pathInfo);
		}
	}
}
