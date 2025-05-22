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

@Component({
  selector: 'app-vehicle-details',
  imports: [MaterialModule, MatTabsModule, MatTableModule, MatPaginatorModule, MatSortModule],
  templateUrl: './vehicle-details.component.html',
  styleUrl: './vehicle-details.component.css',
  providers: [BicycleService, CarService, ScooterService]
})
export class VehicleDetailComponent implements OnInit {
  vehicle: any;
  faults: any[] = [];
  rentals: any[] = [];

  constructor(
    private route: ActivatedRoute,
    //private service: VehicleService
  ) {
    
    const id = this.route.snapshot.paramMap.get('id');
    console.log(id);
 
  }

  ngOnInit() {
    const { type, id } = this.route.snapshot.params;
    console.log("Type & Id:");
    console.log(type);
    console.log(id);
  
  }

  addFault() { /* otvoriti dialog i pozvati service.addFault */ }
  deleteFault(fid: string) { /* pozvati service.deleteFault */ }
}
