
package shared.servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shared.ThrowingFunction;

public interface JSONServletUpdate {
	default <T> boolean update(
		HttpServletRequest req, 
		HttpServletResponse resp, 
		Class<T> clazz,
		ThrowingFunction<T, Boolean, Exception> update
	) throws ServletException, IOException {
		try {
			T data = JSONUtil.decode(req, clazz);
			if (!update.apply(data)) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not complete the update request!");
			}

			else  {
				resp.setStatus(HttpServletResponse.SC_OK);
				resp.getWriter().write("Resource updated successfully");
				return true;
			}
		}

		catch (IllegalArgumentException e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		}

		catch (Exception e) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, String.format("Could not fulfill the request: %s", e.getMessage()));
		}

		return false;
	}
}
