package model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "id_category", "id_product" })
public class TagDTO {
	@JsonProperty("id")
	Integer id;

	@JsonProperty("id_category")
	Integer category;

	@JsonProperty("id_product")
	Integer product;

	public TagDTO() { }

	public TagDTO(Integer id, Integer categoryID, Integer productID) {
		this.id	= id;
		this.category = categoryID;
		this.product = productID;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer categoryID) {
		this.category = categoryID;
	}

	public Integer getProduct() {
		return product;
	}

	public void setProduct(Integer productID) {
		this.product = productID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(category, product);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof TagDTO)) {
			return false;
		}
		TagDTO other = (TagDTO) obj;
		return Objects.equals(category, other.category) && Objects.equals(product, other.product);
	}
}
