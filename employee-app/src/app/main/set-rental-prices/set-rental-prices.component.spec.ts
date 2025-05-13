import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SetRentalPricesComponent } from './set-rental-prices.component';

describe('SetRentalPricesComponent', () => {
  let component: SetRentalPricesComponent;
  let fixture: ComponentFixture<SetRentalPricesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SetRentalPricesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SetRentalPricesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
