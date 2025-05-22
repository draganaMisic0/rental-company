import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../../material/material-imports';
import { MalfunctionCardComponent } from './malfunction-card.component';
// if you're using Material

@NgModule({
  imports: [CommonModule, MaterialModule, MalfunctionCardComponent],
  exports: [MalfunctionCardComponent]
})
export class MalfunctionComponentsModule {}