package shared.service;

import java.util.List;

import shared.ThrowingFunction;
import shared.dao.Bulkable;
import shared.dao.DAOException;

public interface ServiceBulkable{
	default <T, DAO extends Bulkable> List<T> bulk(DAO dao, ThrowingFunction<DAO, List<T>, DAOException> bulk) throws RuntimeException {
		try { 
			return bulk.apply(dao);
		}

		catch (DAOException e) {
			throw new RuntimeException("Failed in fetching: " + e.getMessage(), e);
		}
	}
}
