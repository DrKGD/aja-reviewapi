package model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "id_product", "id_user", "rating", "short_note", "note" })
public class ReviewInsertDTO {
	@JsonProperty("id_product")
	Integer product;

	@JsonProperty("id_user")
	Integer user;

	@JsonProperty("rating")
	BigDecimal rating;

	@JsonProperty("short_note")
	String shortNote;

	@JsonProperty("note")
	String note;

	public ReviewInsertDTO() { }

	public ReviewInsertDTO(Integer id, Integer product, Integer user, BigDecimal rating, String shortNote, String note) {
		this.product = product;
		this.user = user;
		this.rating = rating;
		this.shortNote = shortNote;
		this.note = note;
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
}
