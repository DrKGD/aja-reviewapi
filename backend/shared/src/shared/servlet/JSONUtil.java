package shared.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;

public class JSONUtil {
	public static <T> T decode(HttpServletRequest req, Class<T> clazz) throws IOException {
		StringBuilder json = new StringBuilder();
		try(BufferedReader rd = req.getReader()) { String line;
			while ((line = rd.readLine()) != null) {
				json.append(line);
			}
		}

		// Map to Object
		ObjectMapper objectMapper= JsonMapper.builder()
			.addModule(new JavaTimeModule())
			.build();

		return objectMapper.readValue(json.toString(), clazz);
	}
}
