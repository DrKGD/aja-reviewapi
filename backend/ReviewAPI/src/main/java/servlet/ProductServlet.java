
package servlet;

import java.io.IOException;

import model.Product;
import service.ProductService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shared.servlet.JSONServletBulkable;
import shared.servlet.JSONServletSave;
import shared.servlet.JSONServletSelectable;
import shared.servlet.JSONServletUpdate;
import shared.servlet.ServletDelete;

@WebServlet(urlPatterns = { "/api/product", "/api/product/*" })
public class ProductServlet extends HttpServlet implements 
	JSONServletBulkable,
	JSONServletSave,
	JSONServletUpdate,
	JSONServletSelectable,
	ServletDelete {
	private ProductService productService;

	@Override
	public void init() throws ServletException {
		this.productService = (ProductService) getServletContext().getAttribute("productService");
	}

	// GET /api/product
	// GET /api/product/id
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();

		if(pathInfo == null || ("/").equals(pathInfo)) {
			JSONServletBulkable.super.bulk(req, resp, 
				(Void)	-> null, 
				(id)		-> this.productService.fetchAll());
		}

		else if(pathInfo.startsWith("/")) {
			JSONServletSelectable.super.select(req, resp,
				(Void) -> Integer.parseInt(pathInfo.substring("/".length())),
				(id)	 -> this.productService.get(id));
		}

		else {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL: " + pathInfo);
		}
	}
	
	// Insert new element
	// POST /api/product
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();

		if(pathInfo == null || ("/").equals(pathInfo)) {
			switch(req.getHeader("Content-Type")){
				case "application/json":
					JSONServletSave.super.save(req, resp, Product.class, (data) -> this.productService.save(data));
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
	// PUT /api/product
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();

		if(pathInfo == null || ("/").equals(pathInfo)) {
			JSONServletUpdate.super.update(req, resp, Product.class, (data) -> this.productService.update(data));
		} 

		else {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL: " + pathInfo);
		}
	}

	// Delete a specific id
	// DELETE /api/category/id
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		if(pathInfo == null) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL: " + pathInfo);
		}

		else if(pathInfo != null && pathInfo.startsWith("/")) {
			ServletDelete.super.delete(req, resp, 
				(Void)	-> Integer.parseInt(pathInfo.substring(1)), 
				(id)		-> this.productService.delete(id));
		}

		else {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL: " + pathInfo);
		}
	}
}
