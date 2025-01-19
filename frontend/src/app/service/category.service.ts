import { Injectable } from '@angular/core';
import { ApiReviewService } from "./api-review.service";
import { Observable } from "rxjs";
import { Category, CategoryInsert } from "../model/Category";

@Injectable({
  providedIn: 'root'
})

export class CategoryService {
	private readonly endpoint = 'category';
  constructor(private api: ApiReviewService) { }

	getCategories(): Observable<Category[]> {
		return this.api.get<Category[]>(this.endpoint);
	}

	getCategory(id: number): Observable<Category> {
		return this.api.get<Category>(`${this.endpoint}/${id}`);
	}

	insertCategory(category: CategoryInsert): Observable<CategoryInsert> {
		return this.api.post<CategoryInsert>(this.endpoint, category)
	}

	updateCategory(category: Category): Observable<Category> {
		return this.api.put<Category>(this.endpoint, category)
	}

	deleteCategory(id: number): Observable<void> {
		return this.api.delete<void>(`${this.endpoint}/${id}`)
	}
}
