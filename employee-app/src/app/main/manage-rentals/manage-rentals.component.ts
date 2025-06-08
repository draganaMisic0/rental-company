import { Component, OnInit } from '@angular/core';
import { MaterialModule } from '../../material/material-imports';
import { ScrollingModule } from '@angular/cdk/scrolling';
import { DatePipe } from '@angular/common';
import { Rental } from '../../../models/rental-data';
import { RentalService } from '../../services/rental.service';

@Component({
  selector: 'app-manage-rentals',
  imports: [MaterialModule, ScrollingModule, DatePipe],
  templateUrl: './manage-rentals.component.html',
  styleUrl: './manage-rentals.component.css',
  providers: [RentalService]
})
export class ManageRentalsComponent implements OnInit {

  rentals: Rental[] = [];

  constructor(private rentalService: RentalService) {

  }

  ngOnInit(): void {
      this.rentalService.getAll().subscribe((result: Rental[]) => {
          this.rentals = result;
      });
  }
}
