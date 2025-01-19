import { Route } from '@angular/router';
import { HomeComponent } from "./page/home/home.component";
import { ListProductsComponent } from "./page/list-products/list-products.component";
import { ProductComponent } from "./page/product/product.component";
import { ErrorNotfoundComponent } from "./page/error-notfound/error-notfound.component";
import { ErrorForbiddenComponent } from "./page/error-forbidden/error-forbidden.component";
import { LoginComponent } from "./page/login/login.component";
import { RegisterComponent } from "./page/register/register.component";
import { EditorHomeComponent } from "./page/editor-home/editor-home.component";
import { editorGuard } from "./guard/editor.guard";
import { loggedInGuard } from "./guard/logged-in.guard";
import { CreateCategoryComponent } from "./page/editor/create-category/create-category.component";

export interface ReviewRouteOpts extends Route {
	showHeaderFooter:		boolean;		// Whether it is a standard page or not
}

interface DiscoverableRoute extends Route {
	data: ReviewRouteOpts
}

export const routes: DiscoverableRoute[] = [
	{ path: '',
		data: {
			showHeaderFooter:		true,
		},
		component: HomeComponent,
		title: "Landing page" },

	{ path: 'register',
		canActivate: [loggedInGuard],
		data: {
			showHeaderFooter:		true,
		},
		component: RegisterComponent,
		title: "Register" },

	{ path: 'login',
		canActivate: [loggedInGuard],
		data: {
			showHeaderFooter:		true,
		},
		component: LoginComponent,
		title: "Login" },

	{ path: 'products',
		data: {
			showHeaderFooter:		true,
		},
		component: ListProductsComponent,
		title: "List availble products" },

	{ path: 'product/:id',
		data: {
			showHeaderFooter:		true,
		},
		component: ProductComponent,
		title: "List availble products" },

	{ path: 'editor',
		data: {
			showHeaderFooter:		true,
		},
		canActivate: [editorGuard],
		children: [
			{ path: '',
				component: EditorHomeComponent,
				title: "Editor Home" },

			{ path: 'create-category',
				component: CreateCategoryComponent,
				title: "New category" }
		] },

	{ path: 'forbidden',
		data: {
			showHeaderFooter:		true,
		},
		component: ErrorForbiddenComponent,
		title: "Error 403 - Forbidden" },

	{ path: '**',
		data: {
			showHeaderFooter:		true,
		},
		component: ErrorNotfoundComponent,
		title: "Error 404 - Page not found" },
];
