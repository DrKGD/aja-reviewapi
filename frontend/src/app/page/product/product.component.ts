import { Component } from '@angular/core';
import { ProductService } from "../../service/product.service";
import { TagService } from "../../service/tag.service";
import { ApiReviewService } from "../../service/api-review.service";
import { CategoryService } from "../../service/category.service";
import { ProductImageService } from "../../service/product-image.service";
import { ActivatedRoute, Router } from '@angular/router';
import { forkJoin } from "rxjs";
import { CommonCarouselComponent } from "../../component/common-carousel/common-carousel.component";
import { DatePipe, NgFor, NgIf } from "@angular/common";
import { ReviewService } from "../../service/review.service";
import { Review, ReviewInsert, ReviewUsername } from "../../model/Review";
import { RatingComponent } from "../../component/rating/rating.component";
import { UserAuth } from "../../model/User";
import { AuthService } from "../../service/auth.service";
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from "@angular/forms";
import { ToastService } from "../../service/toast.service";
import { FontAwesomeModule } from "@fortawesome/angular-fontawesome";
import { faXmark } from "@fortawesome/free-solid-svg-icons";
import { ConfirmationService } from "../../service/confirmation.service";

interface Product {
	id: number
	name: string
	description: string
	average: number | null
	price: number
	tags: string[]
	images: string[]
	reviews: ReviewUsername[]
}

@Component({
  selector: 'app-product',
  imports: [CommonCarouselComponent, RatingComponent, NgIf, NgFor, DatePipe, ReactiveFormsModule, FontAwesomeModule],
  templateUrl: './product.component.html',
  styleUrl: './product.component.css'
})

export class ProductComponent {
	protected product:	Product | null = null; 
	protected user:			UserAuth | null = null;
	protected	faXmark	= faXmark

	protected form = new FormGroup({
		rating: new FormControl(undefined, 
			[Validators.required, Validators.min(0), Validators.max(5)]),

		short_note: new FormControl(undefined, 
			[ Validators.required, Validators.maxLength(128) ]),

		note: new FormControl(undefined, 
			[ Validators.required, Validators.maxLength(2048) ]),
	});

	constructor(
		private productService: ProductService,
		private productImageService: ProductImageService,
		private tagService: TagService,
		private categoryService: CategoryService,
		private reviewService: ReviewService,
		private api: ApiReviewService,
		private route: ActivatedRoute,
    private router: Router,
		private authService: AuthService,
		private toastService: ToastService,
		private confirmationService: ConfirmationService,
	) { }

	ngOnInit(): void {
    // Retrieve the "id" parameter from the route
		// If product is not a valid id, return to product list
    const productID = Number(this.route.snapshot.paramMap.get('id'));
		if (!productID)
			this.router.navigate(['/products'])

		// Get all required resources to display product
		const product$		= this.productService.getProductWithAVGs(productID);
		const categories$ = this.categoryService.getCategories();
		const tags$				= this.tagService.getTags();
		const images$			= this.productImageService.getProductImages(productID);
		const reviews$		= this.reviewService.getReviewForProduct(productID);

		this.authService.user$.subscribe((
			(user) => { 
				this.user = user 
			})
		)

		forkJoin([product$, categories$, tags$, images$, reviews$])
			.subscribe(([p, categories, tags, images, reviews]) => {
					if(!p)
						this.router.navigate(['/notfound'])

					const taggedWith = tags
						.filter((t) => t.id_product == p.id)
						.map((t)		=> categories.find((cat) => cat.id == t.id_category)!.name)

					const imagesOfProduct =	images
						.map((i) =>  this.api.staticURL(i.path))

					const sortedReviews = reviews
						.sort((a, b) => new Date(b.date).getTime() - new Date(a.date).getTime());

					this.product = { ...p, images: imagesOfProduct, tags: taggedWith, reviews: sortedReviews }
			})
	}

	removeReview(review: Review) {
		this.confirmationService.showPrompt([
			"Are you absolutely sure you want to delete the review?",
			"This operation is not reversible!"
		])
			.subscribe({ 
				next: (result: boolean) => {
					if (!result) return;
					this.reviewService.deleteReview(review.id)
						.subscribe({
							next: () => {
								this.updateReviews()
								this.toastService.showMessage("Review was deleted!", "SUCCESS")
							},

							error: (err) => {
								this.toastService.showMessage("Could not delete the review: " + err.message, "ERROR")
							}
						})
				},

				error: (err) => {
					this.toastService.showMessage("Could not show prompt: " + err.message, "ERROR")
        }
			})
	}

	// ENHANCE:
	// Does not auto-update for now
	// I'd require a websocket
	// Thus I just "reload" the content manually
	// wouldn't work if multiple
	// users are trying to push new content at the same time
	updateReviews() {
		this.reviewService.getReviewForProduct(this.product!.id)
			.subscribe((reviews) => {
				const sortedReviews = reviews
					.sort((a, b) => new Date(b.date).getTime() - new Date(a.date).getTime());
				this.product!.reviews = sortedReviews;
			})
	}

	addReview() {
		// Form is invalid, mark everything as touched to display error messages
    if (this.form.invalid) {
      this.form.markAllAsTouched(); return;
    }

		// Get the form values directly
		const { rating, short_note, note } = this.form.value;

		const reviewInsert: ReviewInsert = {
			id_product: this.product!.id,
			id_user:		this.user!.id!,
			rating:			rating!,
			short_note: short_note!,
			note:				note!,
		}

		this.reviewService.insertReview(reviewInsert)
			.subscribe({
					next: () => {
						this.updateReviews()
						this.toastService.showMessage("Review was added!", "SUCCESS")
					},

					error:  (err) => {
						this.toastService.showMessage("Could not add review: " + err.message, "ERROR")
					}
			})
	}
}
