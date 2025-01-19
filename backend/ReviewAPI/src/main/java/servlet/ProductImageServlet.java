package servlet; 

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import service.ProductImageService;
import shared.servlet.JSONServletBulkable;

@WebServlet(urlPatterns = { "/api/productImage", "/api/productImage/*" })
public class ProductImageServlet extends HttpServlet implements 
	JSONServletBulkable {
	private ProductImageService productImageService;

	@Override
	public void init() throws ServletException {
		this.productImageService = (ProductImageService) getServletContext().getAttribute("productImageService");
	}

	// GET /api/productImage
	// GET /api/productImage/id
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();

		if(pathInfo == null || ("/").equals(pathInfo)) {
			JSONServletBulkable.super.bulk(req, resp, 
				(Void)	-> null, 
				(id)		-> this.productImageService.fetchAll());
		}

		else if(pathInfo.startsWith("/")) {
			JSONServletBulkable.super.bulk(req, resp, 
				(Void)	-> Integer.parseInt(pathInfo.substring("/".length())),
				(id)		-> this.productImageService.fetchAllImagesForProduct(id));
		}

		else {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL: " + pathInfo);
		}
	}

}
