import { Component, Inject } from '@angular/core';
import { MaterialModule } from '../../../material/material-imports';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-edit-rental-price-dialog',
  imports: [MaterialModule, FormsModule],
  templateUrl: './edit-rental-price-dialog.component.html',
  styleUrl: './edit-rental-price-dialog.component.css'
})
export class EditRentalPriceDialogComponent {

  constructor(
    public dialogRef: MatDialogRef<EditRentalPriceDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { price: number }
  ) {}

   save() {
    this.dialogRef.close(this.data.price);
  }
}
