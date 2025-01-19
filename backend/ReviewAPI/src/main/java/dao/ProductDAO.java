package dao;

import javax.sql.DataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.Product;
import model.ProductWithAVG;
import shared.dao.Bulkable;
import shared.dao.DAOException;
import shared.dao.Deletable;
import shared.dao.Saveable;
import shared.dao.Selectable;
import shared.dao.Updatable;

public class ProductDAO implements 
	Selectable, 
	Bulkable, 
	Saveable, 
	Deletable, 
	Updatable {
	private final DataSource source;

	private static final String GET_ENTRIES = """
		SELECT id as id_product, name, description, price FROM Product
	""";

	private static final String GET_ENTRIES_WITH_AVG = """
		SELECT Product.id as id_product, Product.name, Product.description, Product.price, CAST(AVG(Review.rating) AS DECIMAL(3,2)) as average
		FROM Product
		LEFT JOIN Review ON Product.id = Review.id_product
		GROUP BY Product.id
		ORDER BY average DESC;
	""";

	private static final String GET_ENTRY = """
		SELECT id as id_product, name, description, price FROM Product WHERE id = ?
	""";

	private static final String GET_ENTRY_WITH_AVG = """
		SELECT Product.id as id_product, Product.name, Product.description, Product.price, CAST(AVG(Review.rating) AS DECIMAL(3,2)) as average
		FROM Product
		LEFT JOIN Review ON Product.id = Review.id_product
		WHERE Product.id = ?
		GROUP BY Product.id
	""";

	private static final String SAVE_ENTRY = """
		INSERT INTO Product(name, description, price)
		VALUES (?, ?, ?)
	""";

	private static final String DELETE_ENTRY = """
		DELETE FROM Product WHERE id = ?
	""";

	private static final String UPDATE_ENTRY = """
		UPDATE Category
		SET name = ?, description = ?, price = ?
		WHERE id = ?
	""";


	public ProductDAO(DataSource source) {
		this.source = source;
	}

	static Product mapResult(ResultSet rs) throws SQLException {
		Product pr = new Product();
			pr.setId(rs.getInt("id_product"));
			pr.setName(rs.getString("name"));
			pr.setDescription(rs.getString("description"));
			pr.setPrice(rs.getBigDecimal("price"));
		return pr;
	}

	static ProductWithAVG mapResultWithAVG(ResultSet rs) throws SQLException {
		ProductWithAVG pr = new ProductWithAVG();
			pr.setId(rs.getInt("id_product"));
			pr.setName(rs.getString("name"));
			pr.setDescription(rs.getString("description"));
			pr.setPrice(rs.getBigDecimal("price"));
			pr.setAverage(rs.getBigDecimal("average"));
		return pr;
	}

	public List<Product> fetchAll() throws DAOException {
		return Bulkable.super.bulk(this.source, GET_ENTRIES, ProductDAO::mapResult, (stmt) -> { });
	}

	public List<ProductWithAVG> fetchAllWithAVGs() throws DAOException {
		return Bulkable.super.bulk(this.source, GET_ENTRIES_WITH_AVG, ProductDAO::mapResultWithAVG, (stmt) -> { });
	}

	public Product get(Integer id) throws DAOException {
		return Selectable.super.select(this.source, GET_ENTRY, ProductDAO::mapResult, (stmt) -> {
			stmt.setInt(1, id);
		});
	}

	public ProductWithAVG getProductAndAVG(Integer id) throws DAOException {
		return Selectable.super.select(this.source, GET_ENTRY_WITH_AVG, ProductDAO::mapResultWithAVG, (stmt) -> { 
			stmt.setInt(1, id);
		});
	}

	public boolean delete(Integer id) throws DAOException {
		return Deletable.super.delete(this.source, DELETE_ENTRY, (stmt) -> {
			stmt.setInt(1, id);
		});
	}

	public boolean update(Product dto) throws DAOException {
		return Updatable.super.update(this.source, UPDATE_ENTRY, 
			(stmt) -> {
				stmt.setString(1, dto.getName());
				stmt.setString(2, dto.getDescription());
				stmt.setBigDecimal(3, dto.getPrice());
				stmt.setInt(4, dto.getId());
			}
		);
	}

	public boolean save(Product dto) throws DAOException {
		return Saveable.super.save(this.source, SAVE_ENTRY, (stmt) -> {
			stmt.setString(1, dto.getName());
			stmt.setString(2, dto.getDescription());
			stmt.setBigDecimal(3, dto.getPrice());
		});
	}
}
