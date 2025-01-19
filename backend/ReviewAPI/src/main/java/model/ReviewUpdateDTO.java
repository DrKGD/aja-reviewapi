package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "rating", "short_note", "note" })
public class ReviewUpdateDTO {
	@JsonProperty("id")
	Integer id;

	@JsonProperty("rating")
	BigDecimal rating;

	@JsonProperty("short_note")
	String shortNote;

	@JsonProperty("note")
	String note;

	public ReviewUpdateDTO() { }

	public ReviewUpdateDTO(Integer id, Integer product, Integer user, BigDecimal rating, String shortNote, String note, LocalDateTime date, LocalDateTime lastModified) {
		this.id = id;
		this.rating = rating;
		this.shortNote = shortNote;
		this.note = note;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
