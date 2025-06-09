import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { Manufacturer } from '../../../models/manufacturer-data';
import { ManufacturerService } from '../../services/manufacturer.service';
import { MatDialog } from '@angular/material/dialog';
import { ManufacturerFormComponent } from './manufacturer-form/manufacturer-form.component';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MaterialModule } from '../../material/material-imports';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-manage-manufacturers',
  templateUrl: './manage-manufacturers.component.html',
  imports: [MaterialModule, MatTableModule, MatPaginatorModule, MatSortModule, FormsModule],
  providers: [ManufacturerService]
})
export class ManageManufacturersComponent implements OnInit, AfterViewInit {

  displayedColumns: string[] = ['name', 'country', 'address', 'phone', 'fax', 'email', 'actions'];
  dataSource: MatTableDataSource<Manufacturer> = new MatTableDataSource<Manufacturer>();
  filterText: string = '';

  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(
    private manufacturerService: ManufacturerService,
    private dialog: MatDialog
  ) {}

  ngOnInit() {
    this.loadManufacturers();
  }

  ngAfterViewInit() {
    
  }

  loadManufacturers(): void {
  this.manufacturerService.getAll().subscribe((manufacturers: Manufacturer[]) => {
    this.dataSource = new MatTableDataSource(manufacturers);
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  });
}

  deleteManufacturer(id: number): void {
    this.manufacturerService.delete(id).subscribe(() => this.loadManufacturers());
  }

  openAddDialog() {
    const dialogRef = this.dialog.open(ManufacturerFormComponent, {
      width: '400px',
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.manufacturerService.add(result).subscribe(() => this.loadManufacturers());
      }
    });
  }

  openEditDialog(manufacturer: Manufacturer) {
    const dialogRef = this.dialog.open(ManufacturerFormComponent, {
      width: '25vw',
      data: manufacturer
    });

    dialogRef.afterClosed().subscribe((result: Manufacturer) => {
      if (result) {
        this.manufacturerService.update(manufacturer.id!, result).subscribe(() => this.loadManufacturers());
      }
    });
  }

  applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value.trim().toLowerCase();
    this.dataSource.filter = filterValue;
  }
}
