package dao;

import javax.sql.DataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.TagDTO;
import shared.dao.Bulkable;
import shared.dao.DAOException;
import shared.dao.Deletable;
import shared.dao.Saveable;

public class TagDAO implements 
	Saveable, 
	Bulkable,
	Deletable {
	private final DataSource source;

	private static final String GET_ENTRIES = """
		SELECT id as id_tag, id_category, id_product
		FROM Tag
	""";

	private static final String SAVE_ENTRY = """
		INSERT INTO Tag(id_category, id_product)
		VALUES (?, ?)
	""";

	private static final String DELETE_ENTRY = """
		DELETE FROM Tag WHERE id = ?
	""";


	public TagDAO(DataSource source) {
		this.source = source;
	}

	static TagDTO mapResult(ResultSet rs) throws SQLException {
		TagDTO tag = new TagDTO();
			tag.setId(rs.getInt("id_tag"));
			tag.setCategory(rs.getInt("id_category"));
			tag.setProduct(rs.getInt("id_product"));
		return tag;
	}

	public boolean delete(Integer id) throws DAOException {
		return Deletable.super.delete(this.source, DELETE_ENTRY, (stmt) -> {
			stmt.setInt(1, id);
		});
	}

	public boolean save(TagDTO dto) throws DAOException {
		return Saveable.super.save(this.source, SAVE_ENTRY, (stmt) -> {
			stmt.setInt(1, dto.getCategory());
			stmt.setInt(2, dto.getProduct());
		});
	}

	public List<TagDTO> fetchAll() throws DAOException {
		return Bulkable.super.bulk(this.source, GET_ENTRIES, TagDAO::mapResult, (stmt) -> { });
	}
}
