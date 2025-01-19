import { AfterViewInit, ChangeDetectorRef, Component, OnInit, QueryList, ViewChildren } from '@angular/core';
import { ProductService } from "../../service/product.service";
import { CategoryService } from "../../service/category.service";
import { TagService } from "../../service/tag.service";
import { CardProductComponent, TaggedProductWithAVG } from "../../component/card-product/card-product.component";
import { forkJoin } from "rxjs";
import { NgFor, NgIf } from "@angular/common";
import { ProductImageService } from "../../service/product-image.service";
import { ApiReviewService } from "../../service/api-review.service";
import { FilterWhitelistComponent } from "../../component/filter-whitelist/filter-whitelist.component";
import { ActivatedRoute, Router } from "@angular/router";

@Component({
  selector: 'app-list-products',
  imports: [CardProductComponent, NgIf, NgFor, FilterWhitelistComponent],
  templateUrl: './list-products.component.html',
  styleUrl: './list-products.component.css'
})

export class ListProductsComponent implements OnInit, AfterViewInit {
	protected filteredCategories: { [k: number]: { id: number, name: string, selected: boolean } } = {};
	protected taggedProductsWithAVG: TaggedProductWithAVG[] = [];

	@ViewChildren(CardProductComponent)
		private shownProducts!: QueryList<CardProductComponent>;
	protected hasProducts: boolean = true;

	constructor(
		private productService: ProductService,
		private productImageService: ProductImageService,
		private categoryService: CategoryService,
		private tagService: TagService,
		private api: ApiReviewService,
		private cdr: ChangeDetectorRef,
		private router: Router,
		private route: ActivatedRoute
	) { }

	onFilterSelected(id: number): void {
		this.filteredCategories[id].selected	= !this.filteredCategories[id].selected 

		const categories		= Object.values(this.filteredCategories)
		const selected_ids	= 
			categories
				.filter((c) => c.selected)
				.map((c) => c.id)

		this.router.navigate([], {
			relativeTo: this.route,
			queryParams: { 
				selected: selected_ids.length < categories.length && selected_ids.join(",") || null
			},
			queryParamsHandling: 'merge',
			replaceUrl: true
		})
	}

	isWhitelisted(product: TaggedProductWithAVG): boolean {
		return product.tags.some(tag => this.filteredCategories[tag.id].selected )
	}

	ngAfterViewInit(): void {
		this.hasProducts = this.shownProducts.length > 0;
		this.cdr.detectChanges()

    this.shownProducts.changes.subscribe(() => { 
			this.hasProducts = this.shownProducts.length > 0
			this.cdr.detectChanges()
		})
	}

	ngOnInit(): void {
		const products$			= this.productService.getProductsWithAVGs();
		const categories$ 	= this.categoryService.getCategories();
		const tags$					= this.tagService.getTags();
		const images$				= this.productImageService.getAllProductsImages();

		// Get initial selected categories
		const querySelected = this.route.snapshot.queryParamMap.get('selected') 
		let initFilter: number[] = []
		if (querySelected) { 
			initFilter = querySelected.split(',')
				.map((q) => parseInt(q)) 
				.filter((q) => !isNaN(q))
		}

		forkJoin([products$, categories$, tags$, images$])
			.subscribe(([products, categories, tags, images]) => {
				this.filteredCategories = 
					Object.fromEntries(categories.map(x => [x.id, { 
						id:				x.id, 
						name:			x.name, 
						selected: (initFilter.length === 0) || (initFilter.find((p) => p == x.id) != null)
					}]));

				products.forEach((p) => { 
					// Get tags for each product
					const taggedWith = tags
						.filter((t) => t.id_product == p.id)
						.map((t)		=> categories.find((cat) => cat.id == t.id_category)!)

					const imagesOfProduct =	images
						.filter((i) => i.id_product == p.id)
						.map((i) =>  this.api.staticURL(i.path))

					this.taggedProductsWithAVG.push({ ...p, images: imagesOfProduct, tags: taggedWith! })
				})
			})
	}
}
