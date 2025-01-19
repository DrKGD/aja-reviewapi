import { NgIf } from "@angular/common";
import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from "@angular/forms";
import { CategoryInsert } from "../../../model/Category";
import { CategoryService } from "../../../service/category.service";
import { ToastService } from "../../../service/toast.service";

@Component({
  selector: 'app-create-category',
  imports: [ReactiveFormsModule, NgIf],
  templateUrl: './create-category.component.html',
  styleUrl: './create-category.component.css'
})

export class CreateCategoryComponent {
	protected form = new FormGroup({
		category: new FormControl(undefined, 
			[ Validators.required ]),
	})

	constructor(
		private categoryService: CategoryService,
		private toastService: ToastService,
	) { }

	addCategory() {
		// Form is invalid, mark everything as touched to display error messages
    if (this.form.invalid) {
      this.form.markAllAsTouched(); return;
    }

		// Get the form values directly
		const { category } = this.form.value;

		const register: CategoryInsert = {
			name: category!,
		}

		this.categoryService.insertCategory(register)
			.subscribe({
					next: () => {
						this.toastService.showMessage("Category was added!")
						this.form.reset()
					},

					error:  (err) => {
						this.toastService.showMessage("Could not add category: " + err.message, "ERROR")
					}
			})
	}
}
