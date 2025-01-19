import { TestBed } from '@angular/core/testing';

import { ApiReviewService } from './api-review.service';

describe('ApiReviewService', () => {
  let service: ApiReviewService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ApiReviewService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
