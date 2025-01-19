
package servlet;

import java.io.IOException;

import service.ProductService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shared.servlet.JSONServletSelectable;

@WebServlet(urlPatterns = { "/api/productWithAVG", "/api/productWithAVG/*" })
public class ProductWithAVGServlet extends HttpServlet implements 
	JSONServletSelectable {
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
			JSONServletSelectable.super.select(req, resp,
				(Void)	-> null,
				(Void)	-> this.productService.fetchAllWithAVGs());
		}

		else if(pathInfo.startsWith("/")) {
			JSONServletSelectable.super.select(req, resp,
				(Void) -> Integer.parseInt(pathInfo.substring("/".length())),
				(id)		-> this.productService.getProductWithAVG(id));
		}

		else {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL: " + pathInfo);
		}
	}
}
