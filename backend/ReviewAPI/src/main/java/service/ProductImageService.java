package service;

import java.util.List;

import dao.ProductImageDAO;
import model.ProductImage;
import shared.service.ServiceBulkable;

public class ProductImageService implements ServiceBulkable {

	private final ProductImageDAO dao;

	public ProductImageService(ProductImageDAO dao) {
		this.dao = dao;
	}

	public List<ProductImage> fetchAll() throws RuntimeException {
		return ServiceBulkable.super.bulk(this.dao, (dao) -> dao.fetchAll());
	}

	public List<ProductImage> fetchAllImagesForProduct(int id) throws RuntimeException {
		return ServiceBulkable.super.bulk(this.dao, (dao) -> dao.fetchAllImagesForProduct(id));
	}
}
