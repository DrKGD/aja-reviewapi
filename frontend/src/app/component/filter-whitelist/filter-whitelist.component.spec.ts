import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FilterWhitelistComponent } from './filter-whitelist.component';

describe('FilterWhitelistComponent', () => {
  let component: FilterWhitelistComponent;
  let fixture: ComponentFixture<FilterWhitelistComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FilterWhitelistComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FilterWhitelistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
