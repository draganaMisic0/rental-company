import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivate, GuardResult, MaybeAsync, Router, RouterStateSnapshot } from "@angular/router";

@Injectable({
    providedIn: 'root'
})
export class AuthGuard implements CanActivate{
    constructor(
        private router: Router
    ){};

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
        let data: string | null = localStorage.getItem('userData');

        if(data == null)
        {
            this.router.navigate(['/login']);
            return false;
        }
        else{
            return true;
        }
    }
}