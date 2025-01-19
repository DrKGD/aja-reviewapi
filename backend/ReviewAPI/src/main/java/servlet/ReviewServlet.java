package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ReviewInsertDTO;
import model.ReviewUpdateDTO;
import service.ReviewService;
import shared.servlet.JSONServletBulkable;
import shared.servlet.JSONServletSave;
import shared.servlet.JSONServletSelectable;
import shared.servlet.JSONServletUpdate;
import shared.servlet.ServletDelete;

@WebServlet(urlPatterns = { "/api/review", "/api/review/*" })
public class ReviewServlet extends HttpServlet implements
	JSONServletBulkable,
	JSONServletSelectable,
	JSONServletSave,
	JSONServletUpdate,
	ServletDelete {
	private ReviewService reviewService;
	
	@Override
	public void init() throws ServletException {
		this.reviewService = (ReviewService) getServletContext().getAttribute("reviewService");
	}

	// GET /api/review
	// GET /api/review/:id
	// GET /api/review/product/:id
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();

		if(pathInfo == null || ("/").equals(pathInfo)) {
			JSONServletBulkable.super.bulk(req, resp, 
				(Void)	-> null, 
				(id)		-> this.reviewService.fetchAll());
		}

		else if(pathInfo.startsWith("/product/")) {
			JSONServletBulkable.super.bulk(req, resp, 
				(Void) -> Integer.parseInt(pathInfo.substring("/product/".length())),
				(id)	 -> this.reviewService.fetchAllReviewsForProduct(id));
		}

		else if(pathInfo.startsWith("/")) {
			JSONServletSelectable.super.select(req, resp,
				(Void) -> Integer.parseInt(pathInfo.substring("/".length())),
				(id)	 -> this.reviewService.get(id));
		}

		else {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL: " + pathInfo);
		}
	}

	// Insert new element
	// POST /api/review
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();

		if(pathInfo == null || ("/").equals(pathInfo)) {
			switch(req.getHeader("Content-Type")){
				case "application/json":
					JSONServletSave.super.save(req, resp, ReviewInsertDTO.class, (data) -> this.reviewService.save(data));
					break;

				default:
					resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not complete the request, unhandled Content-Type specified!");
			}
		}

		else {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL: " + pathInfo);
		}
	}

	// Update content
	// PUT /api/review
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();

		if(pathInfo == null || ("/").equals(pathInfo)) {
			JSONServletUpdate.super.update(req, resp, ReviewUpdateDTO.class, (data) -> this.reviewService.update(data));
		} 

		else {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL: " + pathInfo);
		}
	}

	// Delete a specific id
	// DELETE /api/review/id
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		if(pathInfo == null) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL: " + pathInfo);
		}

		else if(pathInfo != null && pathInfo.startsWith("/")) {
			ServletDelete.super.delete(req, resp, 
				(Void)	-> Integer.parseInt(pathInfo.substring(1)), 
				(id)		-> this.reviewService.delete(id));
		}

		else {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL: " + pathInfo);
		}
	}
}
