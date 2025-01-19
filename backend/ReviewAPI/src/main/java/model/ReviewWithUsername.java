
package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "id_product", "id_user", "rating", "short_note", "note", "date", "last_modified" })
public class ReviewWithUsername {
	@JsonProperty("id")
	Integer id;

	@JsonProperty("id_product")
	Integer product;

	@JsonProperty("id_user")
	Integer user;

	@JsonProperty("username")
	String username;

	@JsonProperty("rating")
	BigDecimal rating;

	@JsonProperty("short_note")
	String shortNote;

	@JsonProperty("note")
	String note;

	@JsonProperty("date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	LocalDateTime date;

	@JsonProperty("last_modified")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	LocalDateTime lastModified;

	public ReviewWithUsername() { }
	public ReviewWithUsername(Integer id, Integer product, Integer user, String username, BigDecimal rating, String shortNote, String note, LocalDateTime date, LocalDateTime lastModified) {
		this.id = id;
		this.product = product;
		this.user = user;
		this.username = username;
		this.rating = rating;
		this.shortNote = shortNote;
		this.note = note;
		this.date = date;
		this.lastModified = lastModified;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProduct() {
		return product;
	}

	public void setProduct(Integer product) {
		this.product = product;
	}

	public Integer getUser() {
		return user;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public void setUser(Integer user) {
		this.user = user;
	}

	public BigDecimal getRating() {
		return rating;
	}

	public void setRating(BigDecimal rating) {
		this.rating = rating;
	}

	public String getShortNote() {
		return shortNote;
	}

	public void setShortNote(String shortNote) {
		this.shortNote = shortNote;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public LocalDateTime getLastModified() {
		return lastModified;
	}

	public void setLastModified(LocalDateTime lastModified) {
		this.lastModified = lastModified;
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
		if (!(obj instanceof ReviewDTO)) {
			return false;
		}
		ReviewDTO other = (ReviewDTO) obj;
		return Objects.equals(id, other.id);
	}
}
