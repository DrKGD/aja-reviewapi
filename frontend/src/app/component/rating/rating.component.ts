import { NgFor } from "@angular/common";
import { Component, Input, OnInit } from '@angular/core';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome'
import { faStar } from '@fortawesome/free-solid-svg-icons/faStar';

@Component({
  selector: 'app-rating',
  imports: [FontAwesomeModule, NgFor],
  templateUrl: './rating.component.html',
  styleUrl: './rating.component.css'
})

export class RatingComponent implements OnInit{
	@Input()
		faIcon		= faStar;
	@Input()
		maxRating = 5;

  @Input() rating: number | null | undefined = null;
  @Input() readonly: boolean = false;

	ngOnInit(): void {
		this.rating = Math.round((this.rating ?? 0) - 0.01);
	}

  setRating(value: number) {
    if (this.readonly) return;
    this.rating = value;
  }
}
