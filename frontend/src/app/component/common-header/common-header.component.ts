import { NgIf } from "@angular/common";
import { Component, OnInit } from '@angular/core';
import { AuthService } from "../../service/auth.service";
import { UserAuth } from "../../model/User";
import { ToastService } from "../../service/toast.service";

@Component({
  selector: 'app-common-header',
  imports: [NgIf],
  templateUrl: './common-header.component.html',
  styleUrl: './common-header.component.css'
})

export class CommonHeaderComponent implements OnInit {
	user: UserAuth | null = null;

	constructor(
		private authService: AuthService,
		private toastService: ToastService
	) { }

	logout(): void {
		this.authService.logout().subscribe({
			next: () => this.toastService.showMessage("Successfully logged out!"),
			error: (err) => this.toastService.showMessage("Could not logout: " + err.message, "ERROR")
		})
	}

	ngOnInit(): void {
		this.authService.user$.subscribe((
			(user) => { 
				this.user = user 
			})
		)
	}
}
