import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../../material/material-imports';
import { MalfunctionCardComponent } from './malfunction-card.component';


@NgModule({
  imports: [CommonModule, MaterialModule, MalfunctionCardComponent],
  exports: [MalfunctionCardComponent]
})
export class MalfunctionComponentsModule {}