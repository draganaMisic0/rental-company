import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VehiclesMapViewComponent } from './vehicles-map-view.component';

describe('VehiclesMapViewComponent', () => {
  let component: VehiclesMapViewComponent;
  let fixture: ComponentFixture<VehiclesMapViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VehiclesMapViewComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VehiclesMapViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
