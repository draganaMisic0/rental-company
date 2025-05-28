import { Component, OnInit, ViewChild } from '@angular/core';
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
import { CommonModule, CurrencyPipe, DatePipe } from '@angular/common';
import { ScrollingModule } from '@angular/cdk/scrolling';
import { MatDialog } from '@angular/material/dialog';
import { AddMalfunctionFormComponent } from '../../report-issues/add-malfunction-form/add-malfunction-form.component';
import { Vehicle } from '../../../../models/vehicles-data';
import { Car } from '../../../../models/car-data';
import { Bicycle } from '../../../../models/bicycle-data';
import { Scooter } from '../../../../models/scooter-data';
import { ImagesService } from '../../../services/images.service';

@Component({
  selector: 'app-vehicle-details',
  imports: [CommonModule ,MaterialModule, MatTabsModule, MatTableModule, MatPaginatorModule, MatSortModule, ScrollingModule, DatePipe, CurrencyPipe],
  templateUrl: './vehicle-details.component.html',
  styleUrl: './vehicle-details.component.css',
  providers: [BicycleService, CarService, ScooterService, MalfunctionService, RentalService, ImagesService]
})
export class VehicleDetailComponent implements OnInit {

  vehicle!: any;
  faults: Malfunction[] = [];
  rentals: Rental[] = [];

  @ViewChild('fileInput') fileInput: any;

  file: File | null = null;

  

displayedColumns: string[] = ['clientName', 'dateAndTime', 'duration', 'totalPrice', 'vehicleModel'];
vehicleType: 'car' | 'bicycle' | 'scooter' = 'car';

  constructor(
    private route: ActivatedRoute,
    private carService: CarService,
    private bicycleService: BicycleService,
    private scooterService: ScooterService,
    private malfunctionService: MalfunctionService,
    private rentalService: RentalService,
    public imageService: ImagesService,
    private dialog: MatDialog
  ) {
    
    const id = this.route.snapshot.paramMap.get('id');
 
  }

  ngOnInit() {
    const { type, id } = this.route.snapshot.params;
    
    if (!id || !type) return;

    this.vehicleType = type as 'car' | 'bicycle' | 'scooter';

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
    this.faults = malfunctions;
  });

  this.rentalService.getByVehicleId(id).subscribe((rentals: Rental[]) => {
    this.rentals = rentals;
  })
  
  }

  trackById(index: number, item: any) {
  return item.id;
}

  addFault() {
   
    const dialogRef = this.dialog.open(AddMalfunctionFormComponent, {
                width: '550px',
                data: {vehicleId: this.vehicle.id}
              });
          
            dialogRef.afterClosed().subscribe(
              {
                next: (result: Malfunction) => {
                  this.malfunctionService.add({...result}).subscribe(
                    {
                      next: () => {
                          this.malfunctionService.getAllByVehicleId(result.vehicleId).subscribe((malfunctions: Malfunction[]) => {
                            this.faults = malfunctions;
                          });
                      },
                      error: error => console.error(error)
                    }
                  );
                },
                error: (error) => {console.error(error)}
              }
            );
  }

  
  deleteFault(id: number) { 
    this.malfunctionService.delete(id).subscribe(
      () => {
          this.malfunctionService.getAllByVehicleId(this.vehicle.id).subscribe((malfunctions: Malfunction[]) => {
          this.faults = malfunctions;
        });
      }
    );
   }

   onPhotoSelected(event: any) {
    console.log(event);
   }

  deletePhoto(vehicleId: string) {
    this.imageService.deleteVehicleImage(vehicleId).subscribe({
      next: () => {
        this.vehicle.photoUrl = null; // Update local model
        this.file = null;
      },
      error: (err) => console.error('Failed to delete image:', err)
    });
  }

  onImageError(event: Event): void {
        const target = event.target as HTMLImageElement;
        target.src = '/assets/no-photo.png';
    }

  onClickFileInputButton(): void {
    this.fileInput.nativeElement.click();
  }

  onChangeFileInput(): void {
  const files: { [key: string]: File } = this.fileInput.nativeElement.files;
  this.file = files[0];


  if (this.file) {
    const vehicleId = this.vehicle.id;
    this.imageService.uploadImage('vehicle', vehicleId, this.file).subscribe({
      next: (imageUrl: string) => {

        this.vehicle.photoUrl = imageUrl;
        this.file = null;
        this.fileInput.nativeElement.value = ''; // Reset file input
      },
      error: (err) => {
        console.error("Upload failed:", err);
      }
    });
  }
}
}
