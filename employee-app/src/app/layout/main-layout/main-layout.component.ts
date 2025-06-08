import { Component, inject, OnInit } from '@angular/core';
import { MaterialModule } from '../../material/material-imports';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { map, Observable } from 'rxjs';
import { AsyncPipe } from '@angular/common';
import { Privileges } from './privileges';

@Component({
  selector: 'app-main-layout',
  imports: [MaterialModule, RouterOutlet, RouterLink],
  templateUrl: './main-layout.component.html',
  styleUrl: './main-layout.component.css'
})
export class MainLayoutComponent implements OnInit{

  isMobile$!: Observable<boolean>;  // Detect screen size for mobile view
  profilePicUrl: string = 'path/to/profile/pic.jpg';  // Provide your profile image URL
  private router!: Router;

   isSmallScreen = false;
   isSidenavOpen = true;

  user = {
    firstName: 'Ano',
    lastName: 'Nymous',
    username: "anon",
    profilePictureUrl: '/assets/avatar-default-icon-800.png',
    role: "admin",
    privileges: ["logout"]
  };

  public Privileges = Privileges;



  constructor(
    private breakpointObserver: BreakpointObserver,
  ){
    this.router = inject(Router);
    
    
    if(localStorage.getItem('userData') != null)
    {
      this.user = JSON.parse(localStorage.getItem('userData')!);
      this.user.profilePictureUrl = '/assets/avatar-default-icon-800.png';
      
    }
  }

  ngOnInit(): void {
    this.breakpointObserver.observe([Breakpoints.Handset]).subscribe(res => {
      this.isSmallScreen = res.matches;
      this.isSidenavOpen = !res.matches;
    });
  }

    toggleSidenav() {
    this.isSidenavOpen = !this.isSidenavOpen;
  }

  logout(): void {
    localStorage.removeItem('userData');
    this.router.navigate(['/login']);
  }

}
