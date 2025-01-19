package model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "username", "email", "password" })
public class RegisterDTO {
	@JsonProperty("username")
	String username;

	@JsonProperty("email")
	String email;

	@JsonProperty("password")
	String password;

	public RegisterDTO() { }

	public RegisterDTO(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		return Objects.hash(username, email, password);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof RegisterDTO)) {
			return false;
		}
		RegisterDTO other = (RegisterDTO) obj;
		return Objects.equals(username, other.username) && Objects.equals(email, other.email)
				&& Objects.equals(password, other.password);
	}
}
