import { Component, inject, OnInit } from '@angular/core';
import { MaterialModule } from '../../material/material-imports';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { map, Observable } from 'rxjs';
import { AsyncPipe } from '@angular/common';

@Component({
  selector: 'app-main-layout',
  imports: [MaterialModule, RouterOutlet, RouterLink, AsyncPipe],
  templateUrl: './main-layout.component.html',
  styleUrl: './main-layout.component.css'
})
export class MainLayoutComponent implements OnInit{

  isMobile$!: Observable<boolean>;  // Detect screen size for mobile view
  username: string = 'John Doe';  // Just for demo purposes
  firstName: string = 'John';
  lastName: string = 'Doe';
  profilePicUrl: string = 'path/to/profile/pic.jpg';  // Provide your profile image URL
  private router!: Router;

  constructor(
    private breakpointObserver: BreakpointObserver,
  ){
    this.router = inject(Router);
  }

  ngOnInit(): void {
      this.isMobile$ = this.breakpointObserver.observe([Breakpoints.XSmall]).pipe(
      map(result => result.matches)
    );
  }

  logout(): void {
    localStorage.removeItem('userData');
    this.router.navigate(['/login']);
  }

}
