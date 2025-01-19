import { Injectable, EventEmitter } from '@angular/core';
import { BehaviorSubject, take } from "rxjs";

@Injectable({
  providedIn: 'root'
})

export class ConfirmationService {
	private isVisibleSubject	= new BehaviorSubject<boolean>(false);
	private promptSubject			= new BehaviorSubject<string[]>([]);

	isVisible$				= this.isVisibleSubject.asObservable();
	prompt$						=	this.promptSubject.asObservable();
	emitter$					= new EventEmitter<boolean>();

	public showPrompt(message: string | string[]) {
		const messageArray = Array.isArray(message) ? message : [message];
		this.promptSubject.next(messageArray);
		this.isVisibleSubject.next(true);
		return this.emitter$.asObservable().pipe(take(1));
	}

	public hidePrompt(){
		this.isVisibleSubject.next(false);
	}
}
