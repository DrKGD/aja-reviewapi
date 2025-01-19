import { Injectable } from '@angular/core';
import { ApiReviewService } from "./api-review.service";
import { ProductImage } from "../model/ProductImage";
import { Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})

export class ProductImageService {
	private readonly endpoint = 'productImage';
  constructor(private api: ApiReviewService) { }

	getAllProductsImages(): Observable<ProductImage[]> {
		return this.api.get<ProductImage[]>(this.endpoint);
	}

	getProductImages(id_product: number): Observable<ProductImage[]> {
		return this.api.get<ProductImage[]>(`${this.endpoint}/${id_product}`);
	}
}
