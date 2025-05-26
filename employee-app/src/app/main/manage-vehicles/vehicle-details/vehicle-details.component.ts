import { Component, OnInit } from '@angular/core';
import { MaterialModule } from '../../../material/material-imports';
import { MatTabsModule } from '@angular/material/tabs';
import { ActivatedRoute } from '@angular/router';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { ScooterService } from '../../../services/scooter.service';
import { BicycleService } from '../../../services/bicycle.service';
import { CarService } from '../../../services/car.service';
import { MalfunctionService } from '../../../services/malfunction.service';
import { RentalService } from '../../../services/rental.service';
import { Malfunction } from '../../../../models/malfunction-data';
import { Rental } from '../../../../models/rental-data';

@Component({
  selector: 'app-vehicle-details',
  imports: [MaterialModule, MatTabsModule, MatTableModule, MatPaginatorModule, MatSortModule],
  templateUrl: './vehicle-details.component.html',
  styleUrl: './vehicle-details.component.css',
  providers: [BicycleService, CarService, ScooterService, MalfunctionService, RentalService]
})
export class VehicleDetailComponent implements OnInit {
  vehicle: any;
  faults: any[] = [];
  rentals: Rental[] = [];

displayedColumns: string[] = ['clientName', 'dateAndTime', 'duration', 'totalPrice', 'vehicleModel'];

  constructor(
    private route: ActivatedRoute,
    private carService: CarService,
    private bicycleService: BicycleService,
    private scooterService: ScooterService,
    private malfunctionService: MalfunctionService,
    private rentalService: RentalService
  ) {
    
    const id = this.route.snapshot.paramMap.get('id');
    console.log(id);
 
  }

  ngOnInit() {
    const { type, id } = this.route.snapshot.params;
    
    if (!id || !type) return;

  switch (type) {
    case 'bicycles':
      this.bicycleService.getById(id).subscribe(v => this.vehicle = v);
      break;
    case 'cars':
      this.carService.getById(id).subscribe(v => this.vehicle = v);
      break;
    case 'scooters':
      this.scooterService.getById(id).subscribe(v => this.vehicle = v);
      break;
  }

  this.malfunctionService.getAllByVehicleId(id).subscribe((malfunctions: Malfunction[]) => {
    console.log(malfunctions);
    this.faults = malfunctions;
  });

  this.rentalService.getByVehicleId(id).subscribe((rentals: Rental[]) => {
    console.log(rentals);
    this.rentals = rentals;
  })
  
  }

  trackById(index: number, item: any) {
  return item.id;
}

  addFault() { /* otvoriti dialog i pozvati service.addFault */ }
  deleteFault(fid: string) { /* pozvati service.deleteFault */ }
}
