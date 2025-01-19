import { Component, OnInit } from '@angular/core';
import { AuthService } from "../../service/auth.service";
import { UserAuth } from "../../model/User";

@Component({
  selector: 'app-editor-home',
  imports: [],
  templateUrl: './editor-home.component.html',
  styleUrl: './editor-home.component.css'
})

export class EditorHomeComponent implements OnInit {
	user: UserAuth | null = null;

	constructor(
		private authService: AuthService,
	) { }

	ngOnInit(): void {
		this.authService.user$.subscribe((
			(user) => { 
				this.user = user 
			})
		)
	}
}
