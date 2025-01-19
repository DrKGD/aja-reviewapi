package shared.service;

import shared.ThrowingFunction;
import shared.dao.DAOException;
import shared.dao.Deletable;

public interface ServiceDeletable{
	default <DAO extends Deletable> boolean delete(DAO dao, ThrowingFunction<DAO, Boolean, DAOException> delete) throws RuntimeException {
		try {
			return delete.apply(dao);
		}

		catch (DAOException e) {
			throw new RuntimeException("Failed deleting the entry: " + e.getMessage(), e);
		}
	}
}
