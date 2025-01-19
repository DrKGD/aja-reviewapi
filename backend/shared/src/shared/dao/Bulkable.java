package shared.dao;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import shared.ThrowingConsumer;
import shared.ThrowingFunction;

public interface Bulkable {
	default public <T> List<T> bulk(DataSource source, String query, ThrowingFunction<ResultSet, T, SQLException> mapper, ThrowingConsumer<PreparedStatement, SQLException> prepare) throws DAOException {
		List<T> ts = new ArrayList<T>();

		try (
			Connection connection = source.getConnection();
			PreparedStatement stmt = connection.prepareStatement(query);
		) {
			prepare.apply(stmt);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					ts.add(mapper.apply(rs));
				}
			}
		}

		catch (SQLException e) {
			throw new DAOException("Failed in fetching an entry:" + e.getMessage(), e);
		}

		return ts;
	}
}
