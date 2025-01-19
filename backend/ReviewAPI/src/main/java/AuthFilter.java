import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter(urlPatterns = { 
	"/api/*" 
})

public class AuthFilter implements Filter {
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest		= (HttpServletRequest) request;
		HttpServletResponse httpResponse	= (HttpServletResponse) response;

		String method				= httpRequest.getMethod();
		HttpSession session = httpRequest.getSession(false);
		String servletPath	= httpRequest.getServletPath();

		// Check if the user is authenticated
		boolean isAuthenticated = session != null && session.getAttribute("user") != null;
		boolean isEditor				= session != null && session.getAttribute("role") == "editor";

		// Always allow GET, on all paths
		if (method.equals("GET")) {
			chain.doFilter(request, response);
			return;
		}

		// All methods from the auth servlet are always enabled 
		if(servletPath == null || ("/api/auth").equals(servletPath)) {
			chain.doFilter(request, response);
			return;
		}

		// May always view and edit the content
		if (isAuthenticated && isEditor) {
			chain.doFilter(request, response);
			return;
		}

		// Only authenticated users may post/delete/put content
		if (isAuthenticated && (method.equals("POST") || method.equals("DELETE") || method.equals("PUT"))) {
			chain.doFilter(request, response);
			return;
		}

		// Disallow all other requests
		httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied for unauthenticated users.");
	}
}
