package shared.service;

import shared.ThrowingFunction;
import shared.dao.DAOException;
import shared.dao.Selectable;

public interface ServiceSelectable {
	default <T, DAO extends Selectable> T get(DAO dao, ThrowingFunction<DAO, T, DAOException> get) throws RuntimeException {
		try {
			return get.apply(dao);
		}

		catch (DAOException e) {
			throw new RuntimeException("Failed fetching the entry: " + e.getMessage(), e);
		}
	}
}
