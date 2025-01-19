package dao;

import javax.sql.DataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.Category;
import shared.dao.Bulkable;
import shared.dao.DAOException;
import shared.dao.Saveable;
import shared.dao.Selectable;
import shared.dao.Deletable;
import shared.dao.Updatable;

public class CategoryDAO implements	
	Bulkable,
	Selectable,
	Saveable, 
	Deletable, 
	Updatable {
	private final DataSource source;

	private static final String GET_ENTRIES = """
		SELECT id as id_category, name FROM Category
	""";

	private static final String GET_ENTRY = """
		SELECT id as id_category, name FROM Category WHERE id = ?
	""";

	private static final String SAVE_ENTRY = """
		INSERT INTO Category(name)
		VALUES (?)
	""";

	private static final String DELETE_ENTRY = """
		DELETE FROM Category WHERE id = ?
	""";

	private static final String UPDATE_ENTRY = """
		UPDATE Category
		SET name = ?
		WHERE id = ?
	""";

	public CategoryDAO(DataSource source) {
		this.source = source;
	}

	static Category mapResult(ResultSet rs) throws SQLException {
		Category ct = new Category();
			ct.setId(rs.getInt("id_category"));
			ct.setName(rs.getString("name"));
		return ct;
	}

	public List<Category> fetchAll() throws DAOException {
		return Bulkable.super.bulk(this.source, GET_ENTRIES, CategoryDAO::mapResult, (stmt) -> { });
	}

	public Category get(Integer id) throws DAOException {
		return Selectable.super.select(this.source, GET_ENTRY, CategoryDAO::mapResult, (stmt) -> {
			stmt.setInt(1, id);
		});
	}

	public boolean delete(Integer id) throws DAOException {
		return Deletable.super.delete(this.source, DELETE_ENTRY, (stmt) -> {
			stmt.setInt(1, id);
		});
	}

	public boolean save(Category dto) throws DAOException {
		return Saveable.super.save(this.source, SAVE_ENTRY, (stmt) -> {
			stmt.setString(1, dto.getName());
		});
	}

	public boolean update(Category dto) throws DAOException {
		return Updatable.super.update(this.source, UPDATE_ENTRY, 
			(stmt) -> {
				stmt.setString(1, dto.getName());
				stmt.setInt(2, dto.getId());
			}
		);
	}
}
