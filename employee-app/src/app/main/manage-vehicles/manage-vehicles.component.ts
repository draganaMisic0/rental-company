import { Component, OnInit } from '@angular/core';
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

@Component({
  selector: 'app-vehicles-management',
  templateUrl: './manage-vehicles.component.html',
  styleUrls: ['./manage-vehicles.component.css'],
  imports: [MaterialModule, MatTableModule, MatPaginatorModule, MatSortModule, CommonModule, FormsModule],
  providers: [BicycleService, CarService, ScooterService]
})
export class ManageVehiclesComponent implements OnInit {
  // which type is selected
  selectedType: 'bicycles' | 'cars' | 'scooters' = 'cars';

  // three data sources
  dataSourceBicycles = new MatTableDataSource<any>([]);
  dataSourceCars     = new MatTableDataSource<any>([]);
  dataSourceScooters = new MatTableDataSource<any>([]);

  // three column definitions
  bicycleColumns  = ['model', 'purchasePrice', 'range', 'actions'];
  carColumns      = ['model', 'purchasePrice', 'purchaseDate', 'description', 'actions'];
  scooterColumns  = ['model', 'purchasePrice', 'maxSpeed', 'actions'];

  constructor(
    private bicycleService: BicycleService,
    private carService:     CarService,
    private scooterService: ScooterService,
    private dialog: MatDialog
  ) {}

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
            console.log("Result");
            console.log(result);
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
            //this.manufacturerService.add(result).subscribe(() => this.loadManufacturers());
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

  onFileSelected(evt: any) {
    // same CSV-upload logic, dispatch to the right bulk-upload endpoint
  }
}
