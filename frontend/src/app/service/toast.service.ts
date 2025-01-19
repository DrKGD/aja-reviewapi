import { Injectable } from '@angular/core';
import { BehaviorSubject } from "rxjs";

export type toastKind = 'SUCCESS' | 'FAILURE' | 'ERROR'

@Injectable({
  providedIn: 'root'
})

export class ToastService {
	private isVisibleSubject	= new BehaviorSubject<boolean>(false);
	private messageSubject		= new BehaviorSubject<string[]>([]);
	private	toastKindSubject	= new BehaviorSubject<toastKind>('SUCCESS');

	isVisible$					= this.isVisibleSubject.asObservable();
	message$						=	this.messageSubject.asObservable();
	kind$								=	this.toastKindSubject.asObservable();

	showMessage(message: string | string[], kind: toastKind = "SUCCESS", timeout: number = 3000): void {
		const messageArray = Array.isArray(message) ? message : [message];
		this.toastKindSubject.next(kind);
		this.messageSubject.next(messageArray);
		this.isVisibleSubject.next(true);
    setTimeout(() => { this.hide(); }, timeout);
	}

	hide(): void {
		this.isVisibleSubject.next(false);
	}
}
