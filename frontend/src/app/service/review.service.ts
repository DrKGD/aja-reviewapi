import { Injectable } from '@angular/core';
import { Review, ReviewInsert, ReviewUpdate, ReviewUsername } from "../model/Review";
import { ApiReviewService } from "./api-review.service";
import { Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})

export class ReviewService {
	private readonly endpoint = 'review';
  constructor(private api: ApiReviewService) { }

	getReviews(): Observable<Review[]> {
		return this.api.get<Review[]>(this.endpoint);
	}

	getReview(id: number): Observable<Review> {
		return this.api.get<Review>(`${this.endpoint}/${id}`);
	}
	
	getReviewForProduct(id_product: number): Observable<ReviewUsername[]> {
		return this.api.get<ReviewUsername[]>(`${this.endpoint}/product/${id_product}`);
	}

	insertReview(review: ReviewInsert): Observable<ReviewInsert> {
		return this.api.post<ReviewInsert>(this.endpoint, review)
	}

	updateReview(review: ReviewUpdate): Observable<ReviewUpdate> {
		return this.api.put<ReviewUpdate>(this.endpoint, review)
	}

	deleteReview(id: number): Observable<void> {
		return this.api.delete<void>(`${this.endpoint}/${id}`)
	}
}
