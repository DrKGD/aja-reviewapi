package service;

import java.util.List;

import dao.ReviewDAO;
import model.ReviewDTO;
import model.ReviewInsertDTO;
import model.ReviewUpdateDTO;
import model.ReviewWithUsername;
import shared.service.ServiceDeletable;
import shared.service.ServiceBulkable;
import shared.service.ServiceSelectable;
import shared.service.ServiceSavable;
import shared.service.ServiceUpdatable;

public class ReviewService implements
	ServiceBulkable,
	ServiceSelectable,
	ServiceDeletable, 
	ServiceUpdatable, 
	ServiceSavable {

	private final ReviewDAO dao;

	public ReviewService(ReviewDAO dao) {
		this.dao = dao;
	}

	public List<ReviewDTO> fetchAll() throws RuntimeException {
		return ServiceBulkable.super.bulk(this.dao, (dao) -> dao.fetchAll());
	}

	public List<ReviewWithUsername> fetchAllReviewsForProduct(int id) throws RuntimeException {
		return ServiceBulkable.super.bulk(this.dao, (dao) -> dao.fetchAllReviewsForProduct(id));
	}


	public boolean delete(int id) throws RuntimeException {
		return ServiceDeletable.super.delete(this.dao, (dao) -> dao.delete(id));
	}

	public ReviewDTO get(int id) throws RuntimeException {
		return ServiceSelectable.super.get(this.dao, (dao) -> dao.get(id));
	}
	

	public boolean save(ReviewInsertDTO rv) throws RuntimeException, IllegalArgumentException {
		if(rv.getUser() == null)
			throw new IllegalArgumentException("Missing user for review, cannot perform insert!");

		if(rv.getProduct() == null)
			throw new IllegalArgumentException("Missing product for review, cannot perform insert!");

		if(rv.getShortNote() == null || rv.getShortNote().isBlank())
			throw new IllegalArgumentException("Missing short_note for review, cannot perform insert!");

		if(rv.getNote() == null || rv.getNote().isBlank())
			throw new IllegalArgumentException("Missing note for review, cannot perform insert!");

		if(rv.getRating() == null)
			throw new IllegalArgumentException("Missing rating for review, cannot perform insert!");

		return ServiceSavable.super.save(this.dao, (dao) -> dao.save(rv));
	}

	public boolean update(ReviewUpdateDTO rv) throws RuntimeException {
		if(rv.getId() == null)
			throw new IllegalArgumentException("Missing id for review, cannot perform update!");

		if(rv.getShortNote() == null || rv.getShortNote().isBlank())
			throw new IllegalArgumentException("Missing short_note for review, cannot perform update!");

		if(rv.getNote() == null || rv.getNote().isBlank())
			throw new IllegalArgumentException("Missing note for review, cannot perform update!");

		if(rv.getRating() == null)
			throw new IllegalArgumentException("Missing rating for review, cannot perform update!");


		return ServiceUpdatable.super.update(this.dao, (dao) -> dao.update(rv));
	}

}
