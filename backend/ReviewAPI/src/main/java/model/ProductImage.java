package model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "id_product", "path", "nth" })
public class ProductImage {
	@JsonProperty("id")
	Integer id;

	@JsonProperty("id_product")
	Integer product;

	@JsonProperty("path")
	String	path;

	@JsonProperty("nth")
	Integer nth;

	public ProductImage() { }

	public ProductImage(Integer id, Integer product, String path, Integer nth) {
		this.id = id;
		this.product = product;
		this.path = path;
		this.nth = nth;
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getNth() {
		return nth;
	}

	public void setNth(Integer nth) {
		this.nth = nth;
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
		if (!(obj instanceof ProductImage)) {
			return false;
		}
		ProductImage other = (ProductImage) obj;
		return Objects.equals(id, other.id);
	}
}
