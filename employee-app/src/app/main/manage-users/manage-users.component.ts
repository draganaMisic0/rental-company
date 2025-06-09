import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { EmployeeService } from '../../services/employee.service';
import { ClientService } from '../../services/client.service';
import { EmployeeData } from '../../../models/employee-data';
import { ClientData } from '../../../models/client-data';
import { EmployeeDialogComponent } from './employee-dialog/employee-dialog.component';
import { MatDialogRef } from '@angular/material/dialog';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Inject } from '@angular/core';
import { MaterialModule } from '../../material/material-imports';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { Privileges } from '../../layout/main-layout/privileges';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-manage-users',
  templateUrl: './manage-users.component.html',
  styleUrls: ['./manage-users.component.css'],
  imports: [MaterialModule, MatTableModule, MatPaginatorModule, MatSortModule, FormsModule]
})
export class ManageUsersComponent implements OnInit {

  employeeDataSource!: MatTableDataSource<EmployeeData>;
  clientDataSource!: MatTableDataSource<ClientData>;

  displayedEmployeeColumns: string[] = ['username', 'name', 'role', 'actions'];
  displayedClientColumns: string[] = ['username', 'name', 'email', 'phone', 'idNumber', 'passportNumber', 'citizenship', 'active'];

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  @ViewChild('employeeSort') employeeSort!: MatSort;
  @ViewChild('employeePaginator') employeePaginator!: MatPaginator;

  @ViewChild('clientSort') clientSort!: MatSort;
  @ViewChild('clientPaginator') clientPaginator!: MatPaginator;

  employeeSearchTerm: string = '';
  clientSearchTerm: string = '';

  Privileges = Privileges;
  userPrivileges: string[] = [];

  constructor(
    private employeeService: EmployeeService,
    private clientService: ClientService,
    private dialog: MatDialog
  ) {

    const storedUser = localStorage.getItem('userData');
    if (storedUser) {
      const parsedUser = JSON.parse(storedUser);
      this.userPrivileges = parsedUser.privileges || [];
    }
  }

  ngOnInit(): void {
    this.loadEmployees();
    this.loadClients();
  }

  ngAfterViewInit(): void {
  if (this.employeeDataSource) {
    this.employeeDataSource.sort = this.employeeSort;
    this.employeeDataSource.paginator = this.employeePaginator;
  }

  if (this.clientDataSource) {
    this.clientDataSource.sort = this.clientSort;
    this.clientDataSource.paginator = this.clientPaginator;
  }
}

  hasPrivilege(privilege: Privileges): boolean {
    return this.userPrivileges.includes(privilege);
  }

  loadEmployees(): void {
  this.employeeService.getAll().subscribe((data: EmployeeData[]) => {
    this.employeeDataSource = new MatTableDataSource(data);
    this.employeeDataSource.sort = this.employeeSort;
    this.employeeDataSource.paginator = this.employeePaginator;
    this.employeeDataSource.filterPredicate = (data: EmployeeData, filter: string) => {
      const dataStr = `${data.username} ${data.firstName} ${data.lastName} ${data.role}`.toLowerCase();
      return dataStr.includes(filter);
    };
  });
}

  
loadClients(): void {
  this.clientService.getAll().subscribe((data: ClientData[]) => {
    this.clientDataSource = new MatTableDataSource(data);
    this.clientDataSource.sort = this.clientSort;
    this.clientDataSource.paginator = this.clientPaginator;
    this.clientDataSource.filterPredicate = (data: ClientData, filter: string) => {
      const dataStr = `${data.username} ${data.firstName} ${data.lastName} ${data.email} ${data.phone} ${data.idNumber} ${data.passportNumber} ${data.citizenship}`.toLowerCase();
      return dataStr.includes(filter);
    };
  });
}

  openEmployeeDialog(): void {
    const dialogRef = this.dialog.open(EmployeeDialogComponent, {
      width: '400px',
      data: { type: 'employee' },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.loadEmployees();
      }
    });
  }

  editEmployee(employee: EmployeeData): void {
    const dialogRef = this.dialog.open(EmployeeDialogComponent, {
      width: '400px',
      data: { type: 'edit', employee },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.loadEmployees();
      }
    });
  }

  deleteEmployee(employee: EmployeeData): void {
    this.employeeService.delete(employee.id).subscribe(() => {
      this.loadEmployees();
    });
  }

  toggleClientStatus(client: ClientData): void {
    const newStatus = !(client.active);
    this.clientService.updateStatus(client.id, newStatus).subscribe(() => {
      this.loadClients();
    });
  }

  deleteClient(id: number) {
    this.clientService.delete(id).subscribe(() => {
      this.loadClients();
    });
  };

  editClient(id: number) {
    throw new Error('Method not implemented.');
  }

  applyEmployeeFilter(event: Event): void {
   const filterValue = (event.target as HTMLInputElement).value;
    this.employeeDataSource.filter = filterValue.trim().toLowerCase();
  }

  applyClientFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value;
    this.clientDataSource.filter = filterValue.trim().toLowerCase();
  }
}
