import { KeyValuePipe, NgFor } from "@angular/common";
import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-filter-whitelist',
  imports: [NgFor, KeyValuePipe],
  templateUrl: './filter-whitelist.component.html',
  styleUrl: './filter-whitelist.component.css'
})

export class FilterWhitelistComponent {
	@Input()
		elements: { [k: number]: { name: string, selected: boolean }} = {};
  @Output() 
		selected = new EventEmitter<number>();

	onTagClick(selected_id: number) {
		this.selected.emit(selected_id)
	}
}
