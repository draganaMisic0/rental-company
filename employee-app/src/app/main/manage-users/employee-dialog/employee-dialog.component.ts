import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { EmployeeService } from '../../../services/employee.service';
import { EmployeeData } from '../../../../models/employee-data';
import { MaterialModule } from '../../../material/material-imports';
import { TitleCasePipe } from '@angular/common';

@Component({
  selector: 'app-employee-dialog',
  templateUrl: './employee-dialog.component.html',
  styleUrls: ['./employee-dialog.component.css'],
  imports: [MaterialModule, ReactiveFormsModule, TitleCasePipe]
})
export class EmployeeDialogComponent {
  form: FormGroup;
  isEditMode: boolean;

  roles: string[] = ['admin', 'operator', 'manager'];

  constructor(
    public dialogRef: MatDialogRef<EmployeeDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private fb: FormBuilder,
    private employeeService: EmployeeService
  ) {
    this.isEditMode = data.type === 'edit';
    this.form = this.fb.group({
      firstName: [data.employee?.firstName || '', Validators.required],
      lastName: [data.employee?.lastName || '', Validators.required],
      username: [data.employee?.username || '', Validators.required],
      role: [data.employee?.role || '', Validators.required],
      password: ['', this.isEditMode ? [] : Validators.required],
    });
  }

  onSubmit(): void {
    if (this.form.valid) {
      const employeeData = this.form.value;
      if (this.isEditMode) {
        this.employeeService.update(this.data.employee.id, employeeData).subscribe(() => {
          this.dialogRef.close(true);
        });
      } else {
        this.employeeService.add(employeeData).subscribe(() => {
          this.dialogRef.close(true);
        });
      }
    }
  }

  onCancel(): void {
    this.dialogRef.close();
  }
}