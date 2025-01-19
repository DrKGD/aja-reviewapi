package dao;

import javax.sql.DataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.User;
import shared.dao.Bulkable;
import shared.dao.DAOException;
import shared.dao.Deletable;
import shared.dao.Saveable;
import shared.dao.Selectable;
import shared.dao.Updatable;

public class UserDAO implements 
	Bulkable,
	Selectable,
	Saveable,
	Deletable, 
	Updatable {
	private final DataSource source;

	private static final String GET_ENTRIES = """
		SELECT id as id_user, username, role, email, salt, secret FROM User
	""";

	private static final String GET_ENTRY = """
		SELECT id as id_user, username, role, email, salt, secret FROM User WHERE id = ?
	""";

	private static final String GET_ENTRY_BY_USERNAME = """
		SELECT id as id_user, username, role, email, salt, secret FROM User WHERE username = ?
	""";

	private static final String GET_ENTRY_BY_EMAIL = """
		SELECT id as id_user, username, role, email, salt, secret FROM User WHERE email = ?
	""";

	private static final String SAVE_ENTRY = """
		INSERT INTO User(username, role, email, salt, secret)
		VALUES (?, ?, ?, ?, ?)
	""";

	private static final String DELETE_ENTRY = """
		DELETE FROM User WHERE id = ?
	""";

	private static final String UPDATE_ENTRY = """
		UPDATE 
		SET username = ?, role = ?, email = ?, salt = ?, secret = ?
		WHERE id = ?
	""";

	public UserDAO(DataSource source) {
		this.source = source;
	}

	static User mapResult(ResultSet rs) throws SQLException {
		User u = new User();
			u.setId(rs.getInt("id_user"));
			u.setUsername(rs.getString("username"));
			u.setRole(rs.getString("role"));
			u.setEmail(rs.getString("email"));
			u.setSalt(rs.getString("salt"));
			u.setSecret(rs.getString("secret"));
		return u;
	}

	public List<User> fetchAll() throws DAOException {
		return Bulkable.super.bulk(this.source, GET_ENTRIES, UserDAO::mapResult, (Void) -> { });
	}

	public User get(Integer id) throws DAOException {
		return Selectable.super.select(this.source, GET_ENTRY, UserDAO::mapResult, (stmt) -> {
			stmt.setInt(1, id);
		});
	}

	public User getUserWithUsername(String username) throws DAOException {
		return Selectable.super.select(this.source, GET_ENTRY_BY_USERNAME, UserDAO::mapResult, (stmt) -> {
			stmt.setString(1, username);
		});
	}

	public User getUserWithEmail(String email) throws DAOException {
		return Selectable.super.select(this.source, GET_ENTRY_BY_EMAIL, UserDAO::mapResult, (stmt) -> {
			stmt.setString(1, email);
		});
	}

	public boolean delete(Integer id) throws DAOException {
		return Deletable.super.delete(this.source, DELETE_ENTRY, (stmt) -> {
			stmt.setInt(1, id);
		});
	}

	public boolean update(User dto) throws DAOException {
		return Updatable.super.update(this.source, UPDATE_ENTRY, 
			(stmt) -> {
				stmt.setString(1, dto.getUsername());
				stmt.setString(2, dto.getRole());
				stmt.setString(3, dto.getEmail());
				stmt.setString(4, dto.getSalt());
				stmt.setString(5, dto.getSecret());
				stmt.setInt(6, dto.getId());
			}
		);
	}

	public boolean save(User dto) throws DAOException {
		return Saveable.super.save(this.source, SAVE_ENTRY, (stmt) -> {
			stmt.setString(1, dto.getUsername());
			stmt.setString(2, dto.getRole());
			stmt.setString(3, dto.getEmail());
			stmt.setString(4, dto.getSalt());
			stmt.setString(5, dto.getSecret());
		});
	}
}
