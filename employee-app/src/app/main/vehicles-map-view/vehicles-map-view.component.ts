import { Component, ViewChild } from '@angular/core';
import { Rental } from '../../../models/rental-data';
import { GoogleMapsModule, MapAdvancedMarker, MapInfoWindow } from '@angular/google-maps';
import { RentalService } from '../../services/rental.service';
import { MaterialModule } from '../../material/material-imports';

@Component({
  selector: 'app-vehicles-map-view',
  imports: [GoogleMapsModule, MaterialModule],
  templateUrl: './vehicles-map-view.component.html',
  styleUrl: './vehicles-map-view.component.css',
  providers: [RentalService]
})
export class VehiclesMapViewComponent {
  
   title = "angular-google-maps";
  options: google.maps.MapOptions = {
    center: { lat: 44.78007051571545, lng: 17.208937002303145 },
    zoom: 12,
    mapId: "MY_MAP_ID",
  };
  rentals: Rental[] = [];
  markers: any[] = [];

  @ViewChild(MapInfoWindow) infoWindow!: MapInfoWindow;

  constructor(private rentalService: RentalService){
  
  }

  ngOnInit() {

    this.rentalService.getLatestPerVehicle().subscribe(
      {
        next: (result: Rental[]) => {
          this.rentals = result;
          
          this.markers = this.extractMarkersFromRentals(this.rentals);

        },
        error: (error: any) => {console.error(error); this.rentals = []}
      }
    );

    
  }

  onMarkerClick(marker: MapAdvancedMarker) {
    this.infoWindow.open(marker, true, marker.advancedMarker.title);
  }

  extractMarkersFromRentals(rentals: Rental[]): any[] {
    let locationsToReturn: any[] = [];
    rentals.forEach((rental: Rental) => {
      let tempLocation = {
          lat: rental.endLat, 
          lng: rental.endLon, 
          dateAndTime: rental.dateAndTime, 
          vehicleModel: rental.vehicleModel, 
        };

      
      locationsToReturn.push(tempLocation);
    });
    return locationsToReturn;
  }
}




