import { Injectable } from '@angular/core';
import { ApiReviewService } from "./api-review.service";
import { UserAuth, UserLogin, UserRegistration } from "../model/User";
import { BehaviorSubject, Observable, tap } from "rxjs";
import { AuthResponse, LoginResponse } from "../model/Response";

@Injectable({
  providedIn: 'root'
})

export class AuthService {
	private readonly endpoint = 'auth';

  private userSubject = new BehaviorSubject<UserAuth | null>(null);
	user$ = this.userSubject.asObservable();

	// Attempt to login
	tryLogin(u: UserLogin): Observable<LoginResponse> {
		return this.api.post<LoginResponse>(`${this.endpoint}/login`, u);
	}

	register(u: UserRegistration): Observable<UserRegistration> {
		return this.api.post<UserRegistration>(`${this.endpoint}/register`, u);
	}

	logout() {
		return this.api.post<null>(`${this.endpoint}/logout`, { }).pipe(
			tap(() => this.userSubject.next({ role: "guest" })),
		)
	}

	getAuth() {
		this.api.get<AuthResponse>(`${this.endpoint}`)
			.subscribe({
					next: (response) => {
						switch(response.kind) {
							case "AUTH":
								this.userSubject.next({ username: response.username!, role: response.role!, id: response.id! })
								break;
							default: 
								this.userSubject.next({ role: "guest" })
						}
					},

					error:  (err) => {
						console.error('Could not check for authentication!', err.message);
					}
			})
	}

  constructor(private api: ApiReviewService) { 
		this.getAuth()
	}
}
