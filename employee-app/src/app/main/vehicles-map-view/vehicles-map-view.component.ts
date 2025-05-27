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

  auLocations: any[] = [
    { lat: -31.56391, lng: 147.154312 },
    { lat: -33.718234, lng: 150.363181 },
    { lat: -33.727111, lng: 150.371124 },
    { lat: -33.848588, lng: 151.209834 },
    { lat: -33.851702, lng: 151.216968 },
    { lat: -34.671264, lng: 150.863657 },
    { lat: -35.304724, lng: 148.662905 },
    { lat: -37.75, lng: 145.116667 },
    { lat: -37.759859, lng: 145.128708 },
    { lat: -37.765015, lng: 145.133858 },
    { lat: -37.770104, lng: 145.143299 },
    { lat: -37.7737, lng: 145.145187 },
    { lat: -37.774785, lng: 145.137978 },
    { lat: -37.819616, lng: 144.968119 },
    { lat: -38.330766, lng: 144.695692 },
    { lat: -42.734358, lng: 147.439506 },
    { lat: -42.734358, lng: 147.501315 },
    { lat: -42.735258, lng: 147.438 },
  ];
  @ViewChild(MapInfoWindow) infoWindow!: MapInfoWindow;

  constructor(private rentalService: RentalService){
  
  }

  ngOnInit() {

    const beachFlag =
      "https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png";
    this.auLocations.forEach((location) => {
      let imgTag = document.createElement("img");
      imgTag.src = beachFlag;
      location.content = imgTag;
    });

    this.rentalService.getAll().subscribe((rentals: Rental[]) => {
      this.rentals = rentals;
      this.markers = this.extractMarkersFromRentals(rentals);
    })
  }

  onMarkerClick(marker: MapAdvancedMarker) {
    this.infoWindow.open(marker, true, marker.advancedMarker.title);
  }

  extractMarkersFromRentals(rentals: Rental[]): any[] {

    rentals.forEach((rental: Rental) => {

    });
    return [];
  }
}




