import { Injectable } from '@angular/core';
import { ApiReviewService } from "./api-review.service";
import { Product, ProductWithAVG } from "../model/Product";
import { Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})

export class ProductService {
	private readonly endpoint = 'product';
	private readonly endpointAVG = 'productWithAVG';
  constructor(private api: ApiReviewService) { }

	getProducts(): Observable<Product[]> {
		return this.api.get<Product[]>(this.endpoint);
	}

	getProductsWithAVGs(): Observable<ProductWithAVG[]> {
		return this.api.get<ProductWithAVG[]>(this.endpointAVG);
	}

	getProduct(id: number): Observable<Product> {
		return this.api.get<Product>(`${this.endpoint}/${id}`);
	}

	getProductWithAVGs(id: number): Observable<ProductWithAVG> {
		return this.api.get<ProductWithAVG>(`${this.endpointAVG}/${id}`);
	}

	insertProduct(product: Product): Observable<Product> {
		return this.api.post<Product>(this.endpoint, product)
	}

	updateProduct(product: Product): Observable<Product> {
		return this.api.put<Product>(this.endpoint, product)
	}

	deleteProduct(id: number): Observable<void> {
		return this.api.delete<void>(`${this.endpoint}/${id}`)
	}
}
