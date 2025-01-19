package dao;

import javax.sql.DataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.ProductImage;
import shared.dao.Bulkable;
import shared.dao.DAOException;

public class ProductImageDAO implements Bulkable {
	private final DataSource source;

	private static final String GET_ENTRIES = """
		SELECT id as id_image, id_product, path, nth FROM ProductImage
	""";

	private static final String GET_ENTRIES_FOR_PRODUCT = """
		SELECT id as id_image, id_product, path, nth FROM ProductImage 
		WHERE id_product = ?
		ORDER BY nth ASC
	""";

	public ProductImageDAO(DataSource source) {
		this.source = source;
	}

	static ProductImage mapResult(ResultSet rs) throws SQLException {
		ProductImage pi = new ProductImage();
			pi.setId(rs.getInt("id_image"));
			pi.setProduct(rs.getInt("id_product"));
			pi.setPath(rs.getString("path"));
			pi.setNth(rs.getInt("nth"));
		return pi;
	}

	public List<ProductImage> fetchAll() throws DAOException {
		return Bulkable.super.bulk(this.source, GET_ENTRIES, ProductImageDAO::mapResult, (stmt) -> { });
	}

	public List<ProductImage> fetchAllImagesForProduct(Integer id) throws DAOException {
		return Bulkable.super.bulk(this.source, GET_ENTRIES_FOR_PRODUCT, ProductImageDAO::mapResult, (stmt) -> { 
			stmt.setInt(1, id);
		});
	}
}
