import { Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { AuthService } from "../../service/auth.service";
import { Router } from "@angular/router";
import { ToastService } from "../../service/toast.service";

@Component({
  selector: 'app-login',
  imports: [FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})

export class LoginComponent {
	constructor(
		private authService: AuthService, 
		private router: Router,
		private toastService: ToastService
	) { }

	attemptLogin(f: NgForm) {
		if(!f.valid)
			return

		this.authService.tryLogin(f.value).subscribe({
			next: (response) => {
				switch(response.kind) {
					case "OK":
						this.authService.getAuth()
						this.router.navigate([ "/" ])
							.then(() => {
								this.toastService.showMessage("Logged in!")
							})
						break;

					case "BAD_CREDENTIALS":
						this.toastService.showMessage("Bad credentials!", "FAILURE")
						break;
				}
			},

			error:  (err) => {
				this.toastService.showMessage("Could not attempt login: " + err.message, "ERROR")
			}
		})
	}

}
