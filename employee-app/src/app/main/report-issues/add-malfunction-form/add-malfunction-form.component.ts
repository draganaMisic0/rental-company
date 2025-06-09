import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ReportIssuesComponent } from '../report-issues.component';
import { Malfunction } from '../../../../models/malfunction-data';
import { MaterialModule } from '../../../material/material-imports';
import { Vehicle } from '../../../../models/vehicles-data';
import { VehicleService } from '../../../services/vehicle.service';

@Component({
  selector: 'app-add-malfunction-form',
  imports: [MaterialModule, ReactiveFormsModule],
  templateUrl: './add-malfunction-form.component.html',
  styleUrl: './add-malfunction-form.component.css',
  providers: [VehicleService]
})
export class AddMalfunctionFormComponent {

  isEditMode: boolean = false;
  vehicles: Vehicle[] = [];

  form: FormGroup;

   constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<ReportIssuesComponent>,
    private vehicleService: VehicleService,
    @Inject(MAT_DIALOG_DATA) public data: Malfunction | any | null // Inject the data passed to the dialog
  ) {
    if(data?.type === 'edit')
    {
      this.isEditMode = true;
    }
    
    this.form = this.fb.group({
      description: [this.data?.description || '', Validators.required],
      dateAndTime: [this.data?.dateAndTime || '', Validators.required],
      vehicleId: [this.data?.vehicleId || '', Validators.required],
    });

    if(this.form.get('vehicleId')?.value) //If the vehicleId is already set, disable the option selecting another vehicle
    {
      this.form.get('vehicleId')?.disable();
    }

    this.vehicleService.getAll().subscribe(
      {
        next: (result) => { this.vehicles = result; },
        error: error => { console.error(error); }
      }
    )


  }

  onSubmit() {
     if (this.form.invalid) { return; }
    // keep ID out for create, preserve for edit


    const output = {
      ...this.form.value
    };

    if(output.vehicleId === undefined)
    {
      output.vehicleId = this.data?.vehicleId;
    }

    this.dialogRef.close(output);
  }
  onCancel() {
    this.dialogRef.close();
  }

}
