import { Component, Inject, OnInit } from '@angular/core';
import { Scooter } from '../../../../models/scooter-data';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Car } from '../../../../models/car-data';
import { Bicycle } from '../../../../models/bicycle-data';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../../../material/material-imports';
import { ManufacturerService } from '../../../services/manufacturer.service';
import { Manufacturer } from '../../../../models/manufacturer-data';

@Component({
  selector: 'app-vehicle-form-dialog',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatDialogModule,
    MaterialModule
  ],
  templateUrl: './vehicle-form-dialog.component.html',
  styleUrls: ['./vehicle-form-dialog.component.css'],
  providers: [ManufacturerService]
})
export class VehicleFormDialogComponent implements OnInit {
  form!: FormGroup;
  title!: string;
  manufacturers: Manufacturer[] = [];

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<VehicleFormDialogComponent>,
    private manufacturerService: ManufacturerService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {

    this.title = `Add New ${this.data.type.slice(0, -1).toUpperCase()}`;
        // base fields
    this.form = this.fb.group({
      model: [this.data.vehicle?.model || '', Validators.required],
      purchasePrice: [this.data.vehicle?.purchasePrice || '', [Validators.required, Validators.min(0)]],
      rentalPrice: [this.data.vehicle?.rentalPrice || '', [Validators.required, Validators.min(0)]],
      manufacturerId: [this.data.vehicle?.manufacturerId || null, Validators.required]
    });

    this.manufacturerService.getAll().subscribe(list => {
      this.manufacturers = list;
    });
  }

  ngOnInit() {
    
    // type-specific
    switch (this.data.type) {
      case 'bicycles':
        this.form.addControl('range', this.fb.control(
          (this.data.vehicle as Bicycle)?.range || '', Validators.required
        ));
        break;

      case 'cars':
        this.form.addControl('purchaseDate', this.fb.control(
          (this.data.vehicle as Car)?.purchaseDate || '', Validators.required
        ));
        this.form.addControl('description', this.fb.control(
          (this.data.vehicle as Car)?.description || '', Validators.required
        ));
        break;

      case 'scooters':
        this.form.addControl('maxSpeed', this.fb.control(
          (this.data.vehicle as Scooter)?.maxSpeed || '', Validators.required
        ));
        break;
    }
  }

  submit() {
    if (this.form.invalid) { return; }
    // keep ID out for create, preserve for edit
    const output = {
      ...this.data.vehicle,
      ...this.form.value};


    this.dialogRef.close(output);
  }

  cancel() {
    this.dialogRef.close();
  }
}
