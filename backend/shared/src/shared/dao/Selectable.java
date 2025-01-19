
package shared.dao;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import shared.ThrowingConsumer;
import shared.ThrowingFunction;

// Return a singular entry from a query lookup
public interface Selectable {
	default public <T> T select(DataSource source, String query, ThrowingFunction<ResultSet, T, SQLException> mapper, ThrowingConsumer<PreparedStatement, SQLException> prepare) throws DAOException {
		T t = null;

		try (
			Connection connection = source.getConnection();
			PreparedStatement stmt = connection.prepareStatement(query)
		) {
			prepare.apply(stmt);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					t = mapper.apply(rs);
				}
			}
		}

		catch (SQLException e) {
			throw new DAOException("Failed in fetching an entry:" + e.getMessage(), e);
		}

		return t;
	}

}
