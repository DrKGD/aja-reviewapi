import { Component, Input } from '@angular/core';
import { Category } from "../../model/Category";

export interface CategoryWithImage {
	id: number
	name: string
	image: string
}

@Component({
  selector: 'app-card-category',
  imports: [],
  templateUrl: './card-category.component.html',
  styleUrl: './card-category.component.css'
})

export class CardCategoryComponent {
	@Input()
		category: CategoryWithImage | null = null;
}
