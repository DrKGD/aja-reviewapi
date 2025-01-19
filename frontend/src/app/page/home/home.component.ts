import { Component, OnInit } from '@angular/core';
import { CategoryService } from "../../service/category.service";
import { NgFor } from "@angular/common";
import { forkJoin } from "rxjs";
import { CardCategoryComponent, CategoryWithImage } from "../../component/card-category/card-category.component";
import { ApiReviewService } from "../../service/api-review.service";
import { RouterLink } from "@angular/router";

@Component({
  selector: 'app-home',
  imports: [NgFor, CardCategoryComponent, RouterLink],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})

export class HomeComponent implements OnInit {
	protected categories: CategoryWithImage[] = [];

	constructor(
		private categoryService: CategoryService,
		private api: ApiReviewService
	) { }

	ngOnInit(): void {
		const categories$ = this.categoryService.getCategories();

		forkJoin([categories$])
			.subscribe(([categories]) => {
				this.categories =	
					categories.map((c) => ({ ...c, image: this.api.staticURL(`/static/category/${c.name.toLowerCase().replace(/ /g,"_")}.png`) }))
			})
	}
}
