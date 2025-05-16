import { Component, OnInit } from '@angular/core';
import { MaterialModule } from '../../../material/material-imports';
import { MatTabsModule } from '@angular/material/tabs';
import { ActivatedRoute } from '@angular/router';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';

@Component({
  selector: 'app-vehicle-details',
  imports: [MaterialModule, MatTabsModule, MatTableModule, MatPaginatorModule, MatSortModule],
  templateUrl: './vehicle-details.component.html',
  styleUrl: './vehicle-details.component.css'
})
export class VehicleDetailComponent implements OnInit {
  vehicle: any;
  faults: any[] = [];
  rentals: any[] = [];

  constructor(
    private route: ActivatedRoute,
    //private service: VehicleService
  ) {}

  ngOnInit() {
    const { type, id } = this.route.snapshot.params;
  //  this.service.get(type, id).subscribe(v => this.vehicle = v);
  //  this.service.getFaults(type, id).subscribe(f => this.faults = f);
  //  this.service.getRentals(type, id).subscribe(r => this.rentals = r);
  }

  addFault() { /* otvoriti dialog i pozvati service.addFault */ }
  deleteFault(fid: string) { /* pozvati service.deleteFault */ }
}
