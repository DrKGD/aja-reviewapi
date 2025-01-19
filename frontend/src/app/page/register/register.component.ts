import { Component } from '@angular/core';
import { AuthService } from "../../service/auth.service";
import { FormControl, FormGroup, ReactiveFormsModule, Validators, AbstractControl } from "@angular/forms";
import { Router } from "@angular/router";
import { NgIf } from "@angular/common";
import { UserRegistration } from "../../model/User";
import { ToastService } from "../../service/toast.service";

@Component({
  selector: 'app-register',
  imports: [NgIf, ReactiveFormsModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})

export class RegisterComponent {
	protected form = new FormGroup({
		username: new FormControl(undefined, 
			[ Validators.required ]),

		password: new FormControl(undefined, 
			[ Validators.required ]),

		confirm_password: new FormControl(undefined, 
			[ Validators.required ]),

		email: new FormControl(undefined, 
			[ Validators.required, Validators.email ]),
	}, { validators: [ this.passwordMatchValidator ]})

  // Custom validator to check if passwords match
  private passwordMatchValidator(group: AbstractControl): { [key: string]: boolean } | null {
    const password = group.get('password')?.value;
    const confirmPassword = group.get('confirm_password')?.value;

    return password === confirmPassword ? null : { passwordMismatch: true };
  }

	constructor(
		private authService: AuthService,
		private toastService: ToastService,
		private router: Router
	) { }

	doRegister() {
		// Form is invalid, mark everything as touched to display error messages
    if (this.form.invalid) {
      this.form.markAllAsTouched(); return;
    }

		// Get the form values directly
		const { username, password, email } = this.form.value;

		const register: UserRegistration = {
			username: username!,
			password: password!,
			email: email!,
		}

		this.authService.register(register)
			.subscribe({
					next: () => {
						this.router.navigate([ "/" ])
							.then(() => {
								this.toastService.showMessage("User registered!")
							})
					},

					error:  (err) => {
						this.toastService.showMessage("Could not register: " + err.message, "ERROR")
					}
			})
	}
}
