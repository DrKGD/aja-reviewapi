package model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "username", "password" })
public class LoginDTO {
	@JsonProperty("username")
	String username;

	@JsonProperty("password")
	String password;

	public LoginDTO() { }

	public LoginDTO(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		return Objects.hash(username, password);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof LoginDTO)) {
			return false;
		}
		LoginDTO other = (LoginDTO) obj;
		return Objects.equals(username, other.username) && Objects.equals(password, other.password);
	}
}
