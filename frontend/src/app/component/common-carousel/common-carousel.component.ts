import { NgFor, NgIf } from "@angular/common";
import { Component, Input, OnInit } from '@angular/core';
import { FontAwesomeModule } from "@fortawesome/angular-fontawesome";
import { faCaretLeft, faCaretRight } from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: 'app-common-carousel',
  imports: [FontAwesomeModule, NgFor],
  templateUrl: './common-carousel.component.html',
  styleUrl: './common-carousel.component.css'
})

export class CommonCarouselComponent implements OnInit {
	protected faCaretLeft = faCaretLeft;
	protected faCaretRight  = faCaretRight;
	@Input()
		images: string[] = [ '/assets/img/no_picture.jpg' ];
	@Input()
		otherImagesInView: number = 4;
	selected: number = 0;

	protected getFlexBasis() {
		return `${100 / this.otherImagesInView}%`
	}

	protected hideControl() {
		return this.images.length <= this.otherImagesInView
	}

	protected scrollLeft(container: HTMLElement) {
    container.scrollBy({ left: - container.offsetWidth / this.otherImagesInView, behavior: 'smooth' });
	}

	protected scrollRight(container: HTMLElement) {
    container.scrollBy({ left: container.offsetWidth / this.otherImagesInView, behavior: 'smooth' });
	}

	protected select(id: number){
		this.selected = id
	}

	ngOnInit(): void {
		if (this.images.length == 0)
			this.images.push( '/assets/img/no_picture.jpg' );
	}
}
