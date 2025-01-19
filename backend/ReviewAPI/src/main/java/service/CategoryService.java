package service;

import java.util.List;

import dao.CategoryDAO;
import model.Category;
import shared.service.ServiceDeletable;
import shared.service.ServiceBulkable;
import shared.service.ServiceSelectable;
import shared.service.ServiceSavable;
import shared.service.ServiceUpdatable;

public class CategoryService implements 
	ServiceBulkable, 
	ServiceDeletable, 
	ServiceUpdatable, 
	ServiceSelectable,
	ServiceSavable {

	private final CategoryDAO dao;

	public CategoryService(CategoryDAO dao) {
		this.dao = dao;
	}

	public boolean save(Category ct) throws RuntimeException {
		if (ct.getName() == null || ct.getName().isBlank())
			throw new IllegalArgumentException("Missing name for category, cannot perform insert!");
		return ServiceSavable.super.save(this.dao, (dao) -> dao.save(ct));
	}

	public Category get(int id) throws RuntimeException {
		return ServiceSelectable.super.get(this.dao, (dao) -> dao.get(id));
	}

	public List<Category> fetchAll() throws RuntimeException {
		return ServiceBulkable.super.bulk(this.dao, (dao) -> dao.fetchAll());
	}

	public boolean delete(int id) throws RuntimeException {
		return ServiceDeletable.super.delete(this.dao, (dao) -> dao.delete(id));
	}

	public boolean update(Category ct) throws RuntimeException {
		if(ct.getId() == null)
			throw new IllegalArgumentException("Missing id for category, cannot perform update!");

		if(ct.getName() == null || ct.getName().isBlank())
			throw new IllegalArgumentException("Missing name for category, cannot perform update!");

		return ServiceUpdatable.super.update(this.dao, (dao) -> dao.update(ct));
	}
}
