import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommonCarouselComponent } from './common-carousel.component';

describe('CommonCarouselComponent', () => {
  let component: CommonCarouselComponent;
  let fixture: ComponentFixture<CommonCarouselComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CommonCarouselComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CommonCarouselComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
