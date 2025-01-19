import { AsyncPipe, NgFor, NgIf } from "@angular/common";
import { Component } from '@angular/core';
import { ConfirmationService } from "../../service/confirmation.service";
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome'
import { faXmark, faCheck } from "@fortawesome/free-solid-svg-icons";
import { trigger, transition, style, animate } from '@angular/animations';

@Component({
  selector: 'app-common-confirm',
  imports: [NgFor, NgIf, AsyncPipe, FontAwesomeModule],
  templateUrl: './common-confirm.component.html',
  styleUrl: './common-confirm.component.css',
  animations: [
    trigger('fadeInOut', [
      transition(':enter', [
        style({ opacity: 0 }),
        animate('0.2s ease-in-out', style({ opacity: 0.8 }))
      ]),

			transition(':leave', [
        animate('0.2s ease-in-out', style({ opacity: 0 }))
      ])
    ])
  ]
})

export class CommonConfirmComponent {
	protected faXmark = faXmark
	protected faCheck = faCheck

	isVisible$;
	prompt$;

	constructor(private confirmService: ConfirmationService)  { 
		this.isVisible$	= this.confirmService.isVisible$;
		this.prompt$		=	this.confirmService.prompt$;
	}

	emit(status: boolean) { 
		this.confirmService.emitter$.emit(status) 
		this.confirmService.hidePrompt()
	}
}
