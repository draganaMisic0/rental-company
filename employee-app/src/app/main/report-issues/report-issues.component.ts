import { Component, OnInit } from '@angular/core';
import { Malfunction } from '../../../models/malfunction-data';
import { MatDialog } from '@angular/material/dialog';
import { MalfunctionService } from '../../services/malfunction.service';
import { MalfunctionComponentsModule } from '../../custom-components/malfunction-card/malfunction-card-module';
import { MaterialModule } from '../../material/material-imports';
import { AddMalfunctionFormComponent } from './add-malfunction-form/add-malfunction-form.component';
import { PageEvent } from '@angular/material/paginator';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-report-issues',
  imports: [MalfunctionComponentsModule, MaterialModule, FormsModule],
  templateUrl: './report-issues.component.html',
  styleUrl: './report-issues.component.css',
  providers: [MalfunctionService]
})
export class ReportIssuesComponent implements OnInit{

  malfunctions: Malfunction[] = [];
  pagedMalfunctions: Malfunction[] = [];
  filteredMalfunctions: Malfunction[] = [];
  pageSize: number = 5;
  currentPage: number = 0;

  filterText: string = '';

  constructor(
    private malfunctionService: MalfunctionService,
    private dialog: MatDialog
  ) {

  }
  

  ngOnInit() { 
      this.loadMalfunctions();
    }

    loadMalfunctions() {
      this.malfunctionService.getAll().subscribe(
        {
          next: (malfunctions: Malfunction[]) => {
            this.malfunctions = malfunctions;
            this.updatePagedMalfunctions();
          },
          error: (error) => {console.log(error);}
        }
      )
    }

  openAddDialog(){
    const dialogRef = this.dialog.open(AddMalfunctionFormComponent, {
              width: '550px',
            });
        
          dialogRef.afterClosed().subscribe(
            {
              next: (result) => {
                this.malfunctionService.add({...result}).subscribe(
                  {
                    next: () => this.loadMalfunctions(),
                    error: error => console.error(error)
                  }
                );
              },
              error: (error) => {console.error(error)}
            }
          );
      }
  

          
  onPageChange(event: PageEvent) {
    this.pageSize = event.pageSize;
    this.currentPage = event.pageIndex;
    this.updatePagedMalfunctions();
  }

  updatePagedMalfunctions() {
    // Filter
    const lowerText = this.filterText.trim().toLowerCase();
    this.filteredMalfunctions = this.malfunctions.filter(m =>
      m.description?.toLowerCase().includes(lowerText)
    );

    // Paginate
    const startIndex = this.currentPage * this.pageSize;
    const endIndex = startIndex + this.pageSize;
    this.pagedMalfunctions = this.filteredMalfunctions.slice(startIndex, endIndex);
  }

  clearFilter() {
    this.filterText = '';
    this.updatePagedMalfunctions();
  }
  
  onEditMalfunction(id: number){
    console.log(`EDIT (ID): ${id}`);
  }

  onDeleteMalfunction(id: number){ 
    console.log(`DELETE (ID): ${id}`);
  }
  
}
