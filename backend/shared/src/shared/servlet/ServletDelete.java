package shared.servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shared.ThrowingFunction;

public interface ServletDelete {
	default <Key> void delete(
		HttpServletRequest req, 
		HttpServletResponse resp, 
		ThrowingFunction<HttpServletRequest, Key, IllegalArgumentException> validateRequest,
		ThrowingFunction<Key, Boolean, Exception> delete) throws ServletException, IOException {

		try {
			if (!delete.apply(validateRequest.apply(req))) {
				resp.sendError(HttpServletResponse.SC_NOT_FOUND, String.format("Could not delete the given resource as it was not found!"));
			} 

			else {
				resp.sendError(HttpServletResponse.SC_OK);
			}
		} 

		catch (NumberFormatException e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, 
				"Specified id is not a valid id!");
		}

		catch (Exception e) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
					String.format("An error occurred: %s", e.getMessage()));
		}
	}
	
}
