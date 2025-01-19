package service;

import java.util.List;

import dao.TagDAO;
import model.TagDTO;
import shared.service.ServiceDeletable;
import shared.service.ServiceBulkable;
import shared.service.ServiceSavable;

public class TagService implements 
	ServiceSavable,
	ServiceBulkable,
	ServiceDeletable {
	private final TagDAO dao;

	public TagService(TagDAO dao) {
		this.dao = dao;
	}

	public boolean save(TagDTO tag) throws RuntimeException, IllegalArgumentException {
		if(tag.getCategory() == null) {
			throw new IllegalArgumentException("Missing id_category for new product, cannot perform insert!");
		}

		if(tag.getProduct() == null) {
			throw new IllegalArgumentException("Missing id_product for new product, cannot perform insert!");
		}

		return ServiceSavable.super.save(this.dao, (dao) -> dao.save(tag));
	}

	public List<TagDTO> fetchAll() throws RuntimeException {
		return ServiceBulkable.super.bulk(this.dao, (dao) -> dao.fetchAll());
	}

	public boolean delete(int id) throws RuntimeException {
		return ServiceDeletable.super.delete(this.dao, (dao) -> dao.delete(id));
	}
}
