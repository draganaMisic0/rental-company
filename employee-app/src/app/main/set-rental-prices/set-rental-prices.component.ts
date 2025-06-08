import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { Vehicle } from '../../../models/vehicles-data';
import { EditRentalPriceDialogComponent } from './edit-rental-price-dialog/edit-rental-price-dialog.component';
import { VehicleService } from '../../services/vehicle.service';
import { MatDialog } from '@angular/material/dialog';
import { MaterialModule } from '../../material/material-imports';
import { MatTableModule } from '@angular/material/table';

@Component({
  selector: 'app-set-rental-prices',
  imports: [MaterialModule, MatTableModule],
  templateUrl: './set-rental-prices.component.html',
  styleUrl: './set-rental-prices.component.css'
})
export class SetRentalPricesComponent implements OnInit {
  vehicles: Vehicle[] = [];
  pagedVehicles: Vehicle[] = [];
  pageSize = 5;
  currentPage = 0;

  constructor(
    private vehicleService: VehicleService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.loadVehicles();
  }

  loadVehicles() {
    this.vehicleService.getAll().subscribe({
      next: (vehicles) => {
        this.vehicles = vehicles;
        this.updatePagedVehicles();
      },
      error: (err) => console.error(err)
    });
  }

  updatePagedVehicles() {
    const start = this.currentPage * this.pageSize;
    const end = start + this.pageSize;
    this.pagedVehicles = this.vehicles.slice(start, end);
  }

  onPageChange(event: PageEvent) {
    this.pageSize = event.pageSize;
    this.currentPage = event.pageIndex;
    this.updatePagedVehicles();
  }

  editPrice(vehicle: Vehicle) {
    const dialogRef = this.dialog.open(EditRentalPriceDialogComponent, {
      width: '350px',
      data: { price: vehicle.rentalPrice }
    });

    dialogRef.afterClosed().subscribe((newPrice: number) => {
      if (newPrice !== undefined && newPrice !== vehicle.rentalPrice) {
        this.vehicleService.updateRentalPrice(vehicle.id, newPrice).subscribe({
          next: () => {
            vehicle.rentalPrice = newPrice;
            this.updatePagedVehicles();
          },
          error: (err) => console.error(err)
        });
      }
    });
  }
}
