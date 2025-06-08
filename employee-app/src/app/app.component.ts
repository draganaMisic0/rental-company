import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MaterialModule } from './material/material-imports';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, MaterialModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'employee-app';
}
