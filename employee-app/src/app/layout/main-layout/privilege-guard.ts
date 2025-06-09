// src/app/guards/privilege.guard.ts
import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { Privileges } from './privileges';


@Injectable({
  providedIn: 'root',
})
export class PrivilegeGuard implements CanActivate {

  constructor(private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean | UrlTree {


    const userDataJson = localStorage.getItem('userData');
    if (!userDataJson) {
      // No user data, redirect to login
      return this.router.createUrlTree(['/login']);
    }

    const user = JSON.parse(userDataJson);
    if (!user.privileges || !Array.isArray(user.privileges)) {
      // No privileges found, redirect to login
      return this.router.createUrlTree(['/login']);
    }

    // Required privilege is passed via route data
    const requiredPrivilege: Privileges = route.data['requiredPrivilege'];

    // Check if user has the required privilege
    if (user.privileges.includes(requiredPrivilege)) {
      return true;
    }

    // User lacks privilege: redirect fallback to manage-users page
    return this.router.createUrlTree(['/manage-users']);
  }
}