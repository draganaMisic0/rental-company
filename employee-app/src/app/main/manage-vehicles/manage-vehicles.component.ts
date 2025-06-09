import { Component, inject, OnInit } from '@angular/core';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MaterialModule } from '../../material/material-imports';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { BicycleService } from '../../services/bicycle.service';
import { CarService } from '../../services/car.service';
import { ScooterService } from '../../services/scooter.service';
import { CommonModule } from '@angular/common';
import { FormsModule, NgModel } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { VehicleFormDialogComponent } from './vehicle-form-dialog/vehicle-form-dialog.component';
import { Router } from '@angular/router';
import { VehicleService } from '../../services/vehicle.service';
import { CsvService } from '../../services/csv.service';

@Component({
  selector: 'app-vehicles-management',
  templateUrl: './manage-vehicles.component.html',
  styleUrls: ['./manage-vehicles.component.css'],
  imports: [MaterialModule, MatTableModule, MatPaginatorModule, MatSortModule, CommonModule, FormsModule],
  providers: [BicycleService, CarService, ScooterService, CsvService]
})
export class ManageVehiclesComponent implements OnInit {
  router!: Router;
  
  selectedType: 'bicycles' | 'cars' | 'scooters' = 'cars';

  dataSourceBicycles = new MatTableDataSource<any>([]);
  dataSourceCars     = new MatTableDataSource<any>([]);
  dataSourceScooters = new MatTableDataSource<any>([]);

  bicycleColumns  = ['model', 'purchasePrice', 'range', 'actions'];
  carColumns      = ['model', 'purchasePrice', 'purchaseDate', 'description', 'actions'];
  scooterColumns  = ['model', 'purchasePrice', 'maxSpeed', 'actions'];

  constructor(
    private bicycleService: BicycleService,
    private carService:     CarService,
    private scooterService: ScooterService,
    private csvService: CsvService,
    private dialog: MatDialog,
    
  ) {
    this.router = inject(Router);
  }

  ngOnInit() {
    this.loadTable();
  }

  loadTable() {
    switch(this.selectedType) {
      case 'bicycles':
        this.bicycleService.getAll().subscribe(list =>
          this.dataSourceBicycles.data = list
        );
        break;

      case 'cars':
        this.carService.getAll().subscribe(list =>
          this.dataSourceCars.data = list
        );
        break;

      case 'scooters':
        this.scooterService.getAll().subscribe(list =>
          this.dataSourceScooters.data = list
        );
        break;
    }
  }

  onAdd() {
     const dialogRef = this.dialog.open(VehicleFormDialogComponent, {
          width: '400px',
          data: { type: this.selectedType}
        });
    
        dialogRef.afterClosed().subscribe(result => {
          if (result) {
            switch(this.selectedType)
            {
              case "bicycles":
                this.bicycleService.create({...result}).subscribe(
                  {
                    next: () => this.loadTable(),
                    error: error => {console.error(error);}
                  }
                  );
                break;
              case "cars":
                this.carService.create({...result}).subscribe(
                  {
                    next: () => this.loadTable(),
                    error: error => {console.error(error);}
                  }
                  );
                break;
              case "scooters":
                this.scooterService.create({...result}).subscribe(
                  {
                    next: () => this.loadTable(),
                    error: error => {console.error(error);}
                  }
                  );
                break;
            }
           
          }
        });
  }

  onDelete(id: string) {
    let obs$;
    if      (this.selectedType === 'bicycles') obs$ = this.bicycleService.delete(id);
    else if (this.selectedType === 'cars')     obs$ = this.carService.delete(id);
    else if (this.selectedType === 'scooters') obs$ = this.scooterService.delete(id);
    else throw new Error(`Unknown type selected. ${this.selectedType}`);

    obs$.subscribe(() => this.loadTable());
  }

  onRowClick(row: any) {
    // navigate to /vehicles/:type/:id
  }



  goToVehicleDetails(vehicle: any) {
    this.router.navigate(['/vehicles/details/', this.selectedType, vehicle.id]);
  }

  selectedFile: File | null = null;
  uploadSuccess = false;
  uploadError = '';

  onFileSelected(event: Event): void {
    const target = event.target as HTMLInputElement;
    this.selectedFile = target.files?.[0] || null;
    this.uploadSuccess = false;
    this.uploadError = '';
  }

  onUpload(): void {
    if (!this.selectedFile) return;

    this.csvService.bulkUploadVehicles(this.selectedFile).subscribe({
      next: (message) => {
        this.uploadSuccess = true;
        this.uploadError = '';
        this.loadTable();
      },
      error: (err) => {
        console.error(err);
        this.uploadError = "Upload failed:\n" + err.error.error;
        //this.uploadError = 'Upload failed. Please check the file and try again.';
      }
    });

    
  }
}
