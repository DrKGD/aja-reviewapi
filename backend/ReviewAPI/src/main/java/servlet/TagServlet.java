package servlet;

import java.io.IOException;

import model.TagDTO;
import service.TagService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shared.servlet.JSONServletBulkable;
import shared.servlet.JSONServletSave;
import shared.servlet.ServletDelete;

@WebServlet(urlPatterns = { "/api/tag", "/api/tag/*" })
public class TagServlet extends HttpServlet implements 
	JSONServletBulkable,
	ServletDelete,
	JSONServletSave {

	private TagService tagService;

	@Override
	public void init() throws ServletException {
		this.tagService = (TagService) getServletContext().getAttribute("tagService");
	}

	// GET /api/tag
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();

		if(pathInfo == null || ("/").equals(pathInfo)) {
			JSONServletBulkable.super.bulk(req, resp, 
				(Void)	-> null, 
				(id)		-> this.tagService.fetchAll());
		}

		else {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL: " + pathInfo);
		}
	}
	

	// POST /api/tag
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();

		if(pathInfo == null || ("/").equals(pathInfo)) {
			switch(req.getHeader("Content-Type")){
				case "application/json":
					JSONServletSave.super.save(req, resp, TagDTO.class, (data) -> this.tagService.save(data));
					break;

				default:
					resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not complete the request, unhandled Content-Type specified!");
			}
		}

		else {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL: " + pathInfo);
		}
	}

	// DELETE /api/tag/id
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		if(pathInfo == null) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL: " + pathInfo);
		}

		else if(pathInfo != null && pathInfo.startsWith("/")) {
			ServletDelete.super.delete(req, resp, 
				(Void)	-> Integer.parseInt(pathInfo.substring(1)), 
				(id)		-> this.tagService.delete(id));
		}

		else {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL: " + pathInfo);
		}
	}


}
