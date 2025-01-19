import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from "../service/auth.service";
import { inject } from "@angular/core";
import { firstValueFrom } from "rxjs";
import { filter } from 'rxjs/operators';

export const loggedInGuard: CanActivateFn = async (route, state) => {
	const router: Router		= inject(Router)
	const auth: AuthService	= inject(AuthService)

	const user = await firstValueFrom(
		auth.user$.pipe(filter((user) => user !== null)) // Wait until user is not null
	)

	return user.role == "guest"
		? true
		: router.navigate(['/'])
};
