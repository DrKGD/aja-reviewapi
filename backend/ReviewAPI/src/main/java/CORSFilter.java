import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = "/*")
public class CORSFilter implements Filter {
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// Cast the response to HttpServletResponse
		HttpServletResponse httpResponse	= (HttpServletResponse) response;
		HttpServletRequest httpRequest		= (HttpServletRequest) request;

		System.out.println("Origin: " + httpRequest.getHeader("Origin"));
		System.out.println("Host: " + httpRequest.getHeader("Host"));

		String host = httpRequest.getHeader("Host");
		switch(host) {
			case "localhost:5050":
				httpResponse.setHeader("Access-Control-Allow-Origin",		"http://localhost:4200");
				break;
			case "aja-tomcat:8080":
				httpResponse.setHeader("Access-Control-Allow-Origin",		"http://aja-tomcat:8080");
				break;

		}

		httpResponse.setHeader("Access-Control-Allow-Methods",	"GET, POST, OPTIONS, PUT, DELETE");
		httpResponse.setHeader("Access-Control-Allow-Credentials",	"true");
		httpResponse.setHeader("Access-Control-Allow-Headers",	"Origin, Content-Type, Authorization, Access-Control-Allow-Origin");
		httpResponse.setHeader("Access-Control-Max-Age", "3600");

    // Handle preflight requests
    if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
			httpResponse.setStatus(HttpServletResponse.SC_NO_CONTENT);
			return; // No need to continue the filter chain for OPTIONS requests
    }

		// Continue the filter chain
		chain.doFilter(request, response);
	}
}
