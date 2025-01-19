package model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "username", "role", "email", "salt", "secret" })
public class User {
	@JsonProperty("id")
	Integer id;

	@JsonProperty("username")
	String username;

	@JsonProperty("role")
	String role;

	@JsonProperty("email")
	String email;

	@JsonProperty("salt")
	String salt;

	@JsonProperty("secret")
	String secret;

	public User() { }

	public User(Integer id, String username, String role, String email, String salt, String secret) {
		this.id = id;
		this.username = username;
		this.role = role;
		this.email = email;
		this.salt = salt;
		this.secret = secret;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof User)) {
			return false;
		}
		User other = (User) obj;
		return Objects.equals(id, other.id);
	}
}
