package servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.UserService;
import shared.servlet.JSONServletBulkable;
import shared.servlet.JSONServletSelectable;

@WebServlet(urlPatterns = { "/api/user", "/api/user/*" })
public class UserServlet extends HttpServlet implements
	JSONServletSelectable, 
	JSONServletBulkable {

	private UserService userService;

	@Override
	public void init() throws ServletException {
		this.userService = (UserService) getServletContext().getAttribute("userService");
	}

	// GET /api/user
	// GET /api/user/id
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();

		if(pathInfo == null || ("/").equals(pathInfo)) {
			JSONServletBulkable.super.bulk(req, resp, 
				(Void) -> null,
				(Void) -> this.userService.fetchAll());
		}

		else if(pathInfo.startsWith("/")) {
			JSONServletSelectable.super.select(req, resp, 
				(Void)	-> Integer.parseInt(pathInfo.substring(1)), 
				(id)		-> this.userService.get(id));
		}

		else {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL: " + pathInfo);
		}
	}
}
