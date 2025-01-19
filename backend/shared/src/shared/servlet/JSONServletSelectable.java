package shared.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shared.ThrowingFunction;

public interface JSONServletSelectable {
	default <Key, T> void select(
		HttpServletRequest req, 
		HttpServletResponse resp, 
		ThrowingFunction<HttpServletRequest, Key, IllegalArgumentException> validateRequest,
		ThrowingFunction<Key, T, Exception> get) throws ServletException, IOException {
		try {
			T entry = get.apply(validateRequest.apply(req));

			// Map everything as json
			ObjectMapper objectMapper = JsonMapper.builder()
					.addModule(new JavaTimeModule())
					.build();
			String json = objectMapper.writeValueAsString(entry);

			// Write as json to output
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			try (PrintWriter out = resp.getWriter();) {
				out.write(json);
			}
		} 

		catch (IllegalArgumentException e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, 
				"Could not handle input!");
		}

		catch (Exception e) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
					String.format("An error occurred: %s", e.getMessage()));
		}
	}
}
