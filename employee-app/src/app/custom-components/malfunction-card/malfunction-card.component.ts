import { Component, EventEmitter, Input, Output } from '@angular/core';

import { Malfunction } from '../../../models/malfunction-data';
import { MaterialModule } from '../../material/material-imports';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-malfunction-card',
  imports: [MaterialModule, DatePipe],
  templateUrl: './malfunction-card.component.html',
  styleUrl: './malfunction-card.component.css'
})
export class MalfunctionCardComponent {
    @Input() malfunction!: Malfunction;
    @Output() edit = new EventEmitter<number>();
    @Output() delete = new EventEmitter<number>();

    constructor() {}

    onEdit() : void {
      this.edit.emit(this.malfunction.id);
    }

    onDelete(): void {
      this.delete.emit(this.malfunction.id);
    }

     get hasEditListener(): boolean {
        return this.edit?.observed;
    }

    get hasDeleteListener(): boolean {
        return this.delete?.observed;
    }
}
