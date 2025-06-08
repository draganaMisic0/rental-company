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
  groupedRentals: Rental[][] = [];

  constructor(private rentalService: RentalService) {

  }

  ngOnInit(): void {
  this.rentalService.getAll().subscribe((result: Rental[]) => {
    this.rentals = result;
    this.updateGroupedRentals();
    window.addEventListener('resize', this.updateGroupedRentals.bind(this));
  });
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
  for (let i = 0; i < this.rentals.length; i += columns) {
    groups.push(this.rentals.slice(i, i + columns));
  }
  this.groupedRentals = groups;
}
}
