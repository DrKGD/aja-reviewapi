package shared.dao;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import shared.ThrowingConsumer;

public interface Saveable {
	default boolean save(DataSource source, String query, ThrowingConsumer<PreparedStatement, SQLException> prepare) throws DAOException {
		try (
			Connection connection = source.getConnection();
			PreparedStatement stmt = connection.prepareStatement(query)
		) {
			prepare.apply(stmt);
			return stmt.executeUpdate() == 1;
		}

		catch (SQLException e) {
			throw new DAOException("Failure in saving: " + e.getMessage(), e);
		}
	}
}
