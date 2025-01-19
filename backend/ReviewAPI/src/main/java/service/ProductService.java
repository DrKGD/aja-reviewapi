package service;

import java.util.List;

import dao.ProductDAO;
import model.Product;
import model.ProductWithAVG;
import shared.service.ServiceDeletable;
import shared.service.ServiceBulkable;
import shared.service.ServiceSelectable;
import shared.service.ServiceSavable;
import shared.service.ServiceUpdatable;

public class ProductService implements 
	ServiceBulkable, 
	ServiceSelectable, 
	ServiceDeletable, 
	ServiceUpdatable, 
	ServiceSavable {

	private final ProductDAO dao;

	public ProductService(ProductDAO dao) {
		this.dao = dao;
	}

	public List<Product> fetchAll() throws RuntimeException {
		return ServiceBulkable.super.bulk(this.dao, (dao) -> dao.fetchAll());
	}

	public List<ProductWithAVG> fetchAllWithAVGs() throws RuntimeException {
		return ServiceBulkable.super.bulk(this.dao, (dao) -> dao.fetchAllWithAVGs());
	}

	public Product get(int id) throws RuntimeException {
		return ServiceSelectable.super.get(this.dao, (dao) -> dao.get(id));
	}

	public ProductWithAVG getProductWithAVG(int id) throws RuntimeException {
		return ServiceSelectable.super.get(this.dao, (dao) -> dao.getProductAndAVG(id));
	}

	public boolean delete(int id) throws RuntimeException {
		return ServiceDeletable.super.delete(this.dao, (dao) -> dao.delete(id));
	}

	public boolean save(Product pr) throws RuntimeException {
		if(pr.getName() == null || pr.getName().isBlank()) 
			throw new IllegalArgumentException("Missing name for product, cannot perform insert!");

		if(pr.getDescription() == null || pr.getDescription().isBlank())
			throw new IllegalArgumentException("Missing description for product, cannot perform insert!");

		return ServiceSavable.super.save(this.dao, (dao) -> dao.save(pr));
	}

	public boolean update(Product pr) throws RuntimeException {
		if(pr.getId() == null)
			throw new IllegalArgumentException("Missing ID for product, cannot perform update!");

		if(pr.getName() == null || pr.getName().isBlank()) 
			throw new IllegalArgumentException("Missing name for product, cannot perform update!");

		if(pr.getDescription() == null || pr.getDescription().isBlank())
			throw new IllegalArgumentException("Missing description for product, cannot perform update!");

		return ServiceUpdatable.super.update(this.dao, (dao) -> dao.update(pr));
	}
}
