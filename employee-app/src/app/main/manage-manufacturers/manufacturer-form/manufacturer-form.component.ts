import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MaterialModule } from '../../../material/material-imports';
import { Manufacturer } from '../../../../models/manufacturer-data';

@Component({
  selector: 'app-manufacturer-form',
  imports: [MaterialModule, ReactiveFormsModule],
  templateUrl: './manufacturer-form.component.html',
  styleUrl: './manufacturer-form.component.css',
  providers: []
})
export class ManufacturerFormComponent {
  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<ManufacturerFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Manufacturer | null 
  ) {
    this.form = this.fb.group({
      name: [this.data?.name || '', Validators.required],
      country: [this.data?.country || '', Validators.required],
      address: [this.data?.address || '', Validators.required],
      phone: [this.data?.phone || '', Validators.required],
      fax: [this.data?.fax || '', Validators.required],
      email: [this.data?.email || '', [Validators.required, Validators.email]],
    });
  }

  submit() {
    if (this.form.valid) {
      this.dialogRef.close(this.form.value);
    }
  }

  cancel() {
    this.dialogRef.close();
  }
}
