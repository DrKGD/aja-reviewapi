package shared.servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shared.ThrowingFunction;

public interface JSONServletSave {
	default <T> boolean save(
		HttpServletRequest req, 
		HttpServletResponse resp, 
		Class<T> clazz,
		ThrowingFunction<T, Boolean, Exception> save
	) throws ServletException, IOException {
		try {
			T data = JSONUtil.decode(req, clazz);
			if (!save.apply(data)) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not save the resource!");
			}

			else  {
				resp.setStatus(HttpServletResponse.SC_CREATED);
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
