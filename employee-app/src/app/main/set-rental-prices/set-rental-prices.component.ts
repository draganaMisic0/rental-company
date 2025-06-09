import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { Vehicle } from '../../../models/vehicles-data';
import { EditRentalPriceDialogComponent } from './edit-rental-price-dialog/edit-rental-price-dialog.component';
import { VehicleService } from '../../services/vehicle.service';
import { MatDialog } from '@angular/material/dialog';
import { MaterialModule } from '../../material/material-imports';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { FormsModule } from '@angular/forms';
import { MatSort } from '@angular/material/sort';

@Component({
  selector: 'app-set-rental-prices',
  imports: [MaterialModule, MatTableModule, FormsModule],
  templateUrl: './set-rental-prices.component.html',
  styleUrl: './set-rental-prices.component.css'
})
export class SetRentalPricesComponent implements OnInit, AfterViewInit {
  vehicles: Vehicle[] = [];
  pagedVehicles: Vehicle[] = [];
  pageSize = 5;
  currentPage = 0;

  displayedColumns: string[] = ['model', 'id', 'price', 'edit'];
  dataSource = new MatTableDataSource<Vehicle>();
  filterText = '';

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private vehicleService: VehicleService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.loadVehicles();
  }

  
  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

   loadVehicles() {
    this.vehicleService.getAll().subscribe({
      next: (vehicles) => {
        this.dataSource.data = vehicles;
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      },
      error: (err) => console.error(err)
    });
  }

  applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value.trim().toLowerCase();
    this.dataSource.filter = filterValue;
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
            this.dataSource.data = [...this.dataSource.data]; // Refresh table
          },
          error: (err) => console.error(err)
        });
      }
    });
  }
}
