import { Component, OnInit } from '@angular/core';
import { MaterialModule } from '../../material/material-imports';
import { Router, RouterOutlet } from '@angular/router';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { map, Observable } from 'rxjs';

@Component({
  selector: 'app-main-layout',
  imports: [MaterialModule, RouterOutlet, ],
  templateUrl: './main-layout.component.html',
  styleUrl: './main-layout.component.css'
})
export class MainLayoutComponent implements OnInit{

  isMobile!: Observable<boolean>;  // Detect screen size for mobile view

  constructor(
    private breakpointObserver: BreakpointObserver
  ){}

  ngOnInit(): void {
      this.isMobile = this.breakpointObserver.observe([Breakpoints.XSmall]).pipe(
      map(result => result.matches)
    );
  }

  logout(): void {
    localStorage.removeItem('userData');
  }

}
