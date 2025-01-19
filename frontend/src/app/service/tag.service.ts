import { Injectable } from '@angular/core';
import { ApiReviewService } from "./api-review.service";
import { Observable } from "rxjs";
import { Tag } from "../model/Tag";

@Injectable({
  providedIn: 'root'
})

export class TagService {
	private readonly endpoint = 'tag';
  constructor(private api: ApiReviewService) { }

	getTags(): Observable<Tag[]> {
		return this.api.get<Tag[]>(this.endpoint);
	}

	insertTag(tag: Tag): Observable<Tag> {
		return this.api.post<Tag>(this.endpoint, tag);
	}
}
