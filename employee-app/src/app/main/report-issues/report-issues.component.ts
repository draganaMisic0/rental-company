import { Component, OnInit } from '@angular/core';
import { Malfunction } from '../../../models/malfunction-data';
import { MatDialog } from '@angular/material/dialog';
import { MalfunctionService } from '../../services/malfunction.service';
import { MalfunctionComponentsModule } from '../../custom-components/malfunction-card/malfunction-card-module';
import { MaterialModule } from '../../material/material-imports';

@Component({
  selector: 'app-report-issues',
  imports: [MalfunctionComponentsModule, MaterialModule],
  templateUrl: './report-issues.component.html',
  styleUrl: './report-issues.component.css',
  providers: [MalfunctionService]
})
export class ReportIssuesComponent implements OnInit{

  malfunctions!: Malfunction[];

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
          },
          error: (error) => {console.log(error);}
        }
      )
    }

  openAddDialog(){

  }

  onEditMalfunction(id: number){
    console.log(`EDIT (ID): ${id}`);
  }

  onDeleteMalfunction(id: number){ 
    console.log(`DELETE (ID): ${id}`);
  }
}
