package shared.service;

import shared.ThrowingFunction;
import shared.dao.DAOException;
import shared.dao.Saveable;

public interface ServiceSavable {
	default <DAO extends Saveable> boolean save(DAO dao, ThrowingFunction<DAO, Boolean, DAOException> save) throws RuntimeException {
		try {
			return save.apply(dao);
		} 

		catch (DAOException e) {
			throw new RuntimeException("Failed in saving: " + e.getMessage(), e);
		}
	}
}
