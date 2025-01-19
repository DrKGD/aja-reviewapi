package dao;

import javax.sql.DataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.ReviewDTO;
import model.ReviewInsertDTO;
import model.ReviewUpdateDTO;
import model.ReviewWithUsername;
import shared.dao.Bulkable;
import shared.dao.DAOException;
import shared.dao.Deletable;
import shared.dao.Saveable;
import shared.dao.Selectable;
import shared.dao.Updatable;

public class ReviewDAO implements 
	Selectable,
	Bulkable,
	Saveable, 
	Deletable, 
	Updatable {
	private final DataSource source;

	private static final String GET_ENTRIES = """
		SELECT id as id_review, id_product, id_user, rating, short_note, note, date, last_modified FROM Review
	""";

	private static final String GET_ENTRY = """
		SELECT id as id_review, id_product, id_user, rating, short_note, note, date, last_modified FROM Review WHERE id = ?
	""";

	private static final String GET_ENTRIES_FOR_PRODUCT_WITH_USERNAME = """
		SELECT Review.id as id_review, Review.id_product, Review.id_user, User.username, Review.rating, Review.short_note, Review.note, Review.date, Review.last_modified 
		FROM Review 
		JOIN User ON id_user = User.id
		WHERE id_product = ?
	""";

	private static final String SAVE_ENTRY = """
		INSERT INTO Review (id_product, id_user, rating, short_note, note)
		VALUES (?, ?, ?, ?, ?)
	""";

	private static final String DELETE_ENTRY = """
		DELETE FROM Review WHERE id = ?
	""";

	private static final String UPDATE_ENTRY = """
		UPDATE Review
		SELECT rating = ?, short_note = ?, note = ?,
		WHERE id = ?
	""";


	public ReviewDAO(DataSource source) {
		this.source = source;
	}

	static ReviewDTO mapResult(ResultSet rs) throws SQLException {
		ReviewDTO rv = new ReviewDTO();
			rv.setId(rs.getInt("id_review"));
			rv.setProduct(rs.getInt("id_product"));
			rv.setUser(rs.getInt("id_user"));
			rv.setRating(rs.getBigDecimal("rating"));
			rv.setShortNote(rs.getString("short_note"));
			rv.setNote(rs.getString("note"));
			rv.setDate(rs.getTimestamp("date").toLocalDateTime());
			rv.setLastModified(rs.getTimestamp("last_modified").toLocalDateTime());
		return rv;
	}

	static ReviewWithUsername mapResultWithUsername(ResultSet rs) throws SQLException {
		ReviewWithUsername rv = new ReviewWithUsername();
			rv.setId(rs.getInt("id_review"));
			rv.setProduct(rs.getInt("id_product"));
			rv.setUser(rs.getInt("id_user"));
			rv.setUsername(rs.getString("username"));
			rv.setRating(rs.getBigDecimal("rating"));
			rv.setShortNote(rs.getString("short_note"));
			rv.setNote(rs.getString("note"));
			rv.setDate(rs.getTimestamp("date").toLocalDateTime());
			rv.setLastModified(rs.getTimestamp("last_modified").toLocalDateTime());
		return rv;
	}

	public List<ReviewDTO> fetchAll() throws DAOException {
		return Bulkable.super.bulk(this.source, GET_ENTRIES, ReviewDAO::mapResult, (stmt) -> { });
	}

	public List<ReviewWithUsername> fetchAllReviewsForProduct(Integer id) throws DAOException {
		return Bulkable.super.bulk(this.source, GET_ENTRIES_FOR_PRODUCT_WITH_USERNAME, ReviewDAO::mapResultWithUsername, (stmt) -> { 
			stmt.setInt(1, id);
		});
	}

	public ReviewDTO get(Integer id) throws DAOException {
		return Selectable.super.select(this.source, GET_ENTRY, ReviewDAO::mapResult, (stmt) -> {
			stmt.setInt(1, id);
		});
	}

	public boolean delete(Integer id) throws DAOException {
		return Deletable.super.delete(this.source, DELETE_ENTRY, (stmt) -> {
			stmt.setInt(1, id);
		});
	}

	public boolean update(ReviewUpdateDTO dto) throws DAOException {
		return Updatable.super.update(this.source, UPDATE_ENTRY, 
			(stmt) -> {
				stmt.setBigDecimal(1, dto.getRating());
				stmt.setString(2, dto.getShortNote());
				stmt.setString(3, dto.getNote());
				stmt.setInt(4, dto.getId());
			}
		);
	}

	public boolean save(ReviewInsertDTO dto) throws DAOException {
		return Saveable.super.save(this.source, SAVE_ENTRY, (stmt) -> {
			stmt.setInt(1, dto.getProduct());
			stmt.setInt(2, dto.getUser());
			stmt.setBigDecimal(3, dto.getRating());
			stmt.setString(4, dto.getShortNote());
			stmt.setString(5, dto.getNote());
		});
	}
}
