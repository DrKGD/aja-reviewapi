import { Component } from '@angular/core';
import { ToastService } from "../../service/toast.service";
import { AsyncPipe, LowerCasePipe, NgClass, NgFor, NgIf } from "@angular/common";
import { trigger, transition, style, animate } from '@angular/animations';

@Component({
  selector: 'app-common-toast',
  imports: [AsyncPipe, NgFor, NgIf, NgClass, LowerCasePipe],
  templateUrl: './common-toast.component.html',
  styleUrl: './common-toast.component.css',
  animations: [
    trigger('fadeInOut', [
      transition(':enter', [
        style({ opacity: 0 }),
        animate('0.5s ease-in-out', style({ opacity: 1 }))
      ]),

			transition(':leave', [
        animate('0.5s ease-in-out', style({ opacity: 0 }))
      ])
    ])
  ]
})

export class CommonToastComponent {
	isVisible$;
	message$;
	kind$;

	constructor(private toastService: ToastService)  { 
		this.isVisible$	= this.toastService.isVisible$;
		this.message$		=	this.toastService.message$;
		this.kind$			=	this.toastService.kind$;
	}
}
