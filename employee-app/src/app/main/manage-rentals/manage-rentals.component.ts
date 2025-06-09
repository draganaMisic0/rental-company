import { Component, OnInit } from '@angular/core';
import { MaterialModule } from '../../material/material-imports';
import { ScrollingModule } from '@angular/cdk/scrolling';
import { DatePipe } from '@angular/common';
import { Rental } from '../../../models/rental-data';
import { RentalService } from '../../services/rental.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-manage-rentals',
  imports: [MaterialModule, ScrollingModule, DatePipe, FormsModule],
  templateUrl: './manage-rentals.component.html',
  styleUrl: './manage-rentals.component.css',
  providers: [RentalService]
})
export class ManageRentalsComponent implements OnInit {

  rentals: Rental[] = [];
  filteredRentals: Rental[] = [];
  groupedRentals: Rental[][] = [];
  filterQuery: string = '';

  constructor(private rentalService: RentalService) {

  }

  ngOnInit(): void {
  this.rentalService.getAll().subscribe((result: Rental[]) => {
    this.rentals = result;
    this.filteredRentals = this.rentals;
    this.updateGroupedRentals();
    window.addEventListener('resize', this.updateGroupedRentals.bind(this));
  });
}

applyFilter(): void {
    const query = this.filterQuery.trim().toLowerCase();
    if (!query) {
      this.filteredRentals = [...this.rentals];
    } else {
      this.filteredRentals = this.rentals.filter(rental =>
        rental.vehicleModel.toLowerCase().includes(query) ||
        rental.clientName.toLowerCase().includes(query) ||
        rental.vehicleId.toString().includes(query) ||
        rental.clientId.toString().includes(query)
      );
    }
    this.updateGroupedRentals();
  }

 private getColumns(): number {
    const width = window.innerWidth;
    if (width >= 1200) return 3;
    if (width >= 768) return 2;
    return 1;
  }

  private updateGroupedRentals(): void {
    const columns = this.getColumns();
    const groups: Rental[][] = [];
    for (let i = 0; i < this.filteredRentals.length; i += columns) {
      groups.push(this.filteredRentals.slice(i, i + columns));
    }
    this.groupedRentals = groups;
  }
}
