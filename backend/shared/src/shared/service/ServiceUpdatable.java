package shared.service;

import shared.ThrowingFunction;
import shared.dao.DAOException;
import shared.dao.Updatable;

public interface ServiceUpdatable {
	default <DAO extends Updatable> boolean update(DAO dao, ThrowingFunction<DAO, Boolean, DAOException> update) throws RuntimeException {
		try {
			return update.apply(dao);
		} 

		catch (DAOException e) {
			throw new RuntimeException("Failed in updating: " + e.getMessage(), e);
		}
	}
}
