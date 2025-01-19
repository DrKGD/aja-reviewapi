import { NgIf } from "@angular/common";
import { Component, OnInit } from '@angular/core';
import { NavigationEnd, Router, RouterOutlet } from '@angular/router';
import { filter } from 'rxjs/operators';
import { CommonHeaderComponent } from "./component/common-header/common-header.component";
import { CommonFooterComponent } from "./component/common-footer/common-footer.component";
import { ReviewRouteOpts } from "./app.routes";
import { CommonToastComponent } from "./component/common-toast/common-toast.component";
import { CommonConfirmComponent } from "./component/common-confirm/common-confirm.component";

@Component({
  selector: 'app-root',
  imports: [NgIf, CommonHeaderComponent, CommonFooterComponent, RouterOutlet, CommonToastComponent, CommonConfirmComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})

export class AppComponent implements OnInit {
  title = 'frontend';
	hasHeaderFooter: boolean | null = true;

	constructor(private router: Router) { }

  ngOnInit() {
		this.router.events
			.pipe(filter(event => event instanceof NavigationEnd))
			.subscribe(() => {
				const currentRoute						= this.router.routerState.root.firstChild;
				const routeData: ReviewRouteOpts = {
					showHeaderFooter: false,
					...currentRoute?.snapshot.data
				}

				this.hasHeaderFooter = routeData.showHeaderFooter
			});
  }
}
