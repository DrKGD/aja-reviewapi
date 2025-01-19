import { Component, Input } from '@angular/core';
import { NgFor } from "@angular/common";
import { RouterLink } from "@angular/router";
import { RatingComponent } from "../rating/rating.component";
import { CompactTag } from "../../model/Tag";

export interface TaggedProductWithAVG {
	id: number
	name: string
	description: string
	average: number | null
	price: number
	tags: CompactTag[],
	images: string[]
}

@Component({
  selector: 'app-card-product',
  imports: [NgFor, RouterLink, RatingComponent],
  templateUrl: './card-product.component.html',
  styleUrl: './card-product.component.css'
})

export class CardProductComponent {
	@Input()
		product: TaggedProductWithAVG | null = null;
}
