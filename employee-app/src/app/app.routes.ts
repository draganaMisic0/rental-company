import { Routes } from '@angular/router';
import { LoginPageComponent } from './login/login-page/login-page.component';
import { AppComponent } from './app.component';
import { ManageUsersComponent } from './main/manage-users/manage-users.component';
import { ManageManufacturersComponent } from './main/manage-manufacturers/manage-manufacturers.component';
import { MainLayoutComponent } from './layout/main-layout/main-layout.component';
import { ManageRentalsComponent } from './main/manage-rentals/manage-rentals.component';
import { StatisticsComponent } from './main/statistics/statistics.component';
import { ReportIssuesComponent } from './main/report-issues/report-issues.component';
import { SetRentalPricesComponent } from './main/set-rental-prices/set-rental-prices.component';
import { VehiclesMapViewComponent } from './main/vehicles-map-view/vehicles-map-view.component';
import { ManageVehiclesComponent } from './main/manage-vehicles/manage-vehicles.component';
import { VehicleDetailComponent } from './main/manage-vehicles/vehicle-details/vehicle-details.component';
import { NotFoundComponent } from './main/not-found/not-found.component';
import { PrivilegeGuard } from './layout/main-layout/privilege-guard';
import { Privileges } from './layout/main-layout/privileges';

export const routes: Routes = [
    { path: 'login', component: LoginPageComponent },
    // Main layout route
  {
    path: '',
    component: MainLayoutComponent,
    canActivate: [],  // Optional guard to ensure the user is logged in
    children: [
      { path: 'manage-vehicles', component: ManageVehiclesComponent,
        canActivate: [PrivilegeGuard],
        data: { requiredPrivilege: Privileges.MANAGE_VEHICLES }
      },
      {
        path: 'vehicles/details/:type/:id',
        component: VehicleDetailComponent,
        canActivate: [PrivilegeGuard],
        data: { requiredPrivilege: Privileges.MANAGE_VEHICLES }
      },
       {
        path: 'manage-users',
        component: ManageUsersComponent,
        canActivate: [PrivilegeGuard],
        data: { requiredPrivilege: Privileges.MANAGE_USERS }
      },
      {
        path: 'manage-manufacturers',
        component: ManageManufacturersComponent,
        canActivate: [PrivilegeGuard],
        data: { requiredPrivilege: Privileges.MANAGE_MANUFACTURERS }
      },
       {
        path: 'manage-rentals',
        component: ManageRentalsComponent,
        canActivate: [PrivilegeGuard],
        data: { requiredPrivilege: Privileges.MANAGE_RENTALS }
      },
      {
        path: 'statistics',
        component: StatisticsComponent,
        canActivate: [PrivilegeGuard],
        data: { requiredPrivilege: Privileges.VIEW_STATISTICS }
      },
      {
        path: 'report-issues',
        component: ReportIssuesComponent,
        canActivate: [PrivilegeGuard],
        data: { requiredPrivilege: Privileges.REPORT_ISSUES }
      },
      {
        path: 'set-rental-prices',
        component: SetRentalPricesComponent,
        canActivate: [PrivilegeGuard],
        data: { requiredPrivilege: Privileges.SET_RENTAL_PRICES }
      },
      {
        path: 'vehicles-map-view',
        component: VehiclesMapViewComponent,
        canActivate: [PrivilegeGuard],
        data: { requiredPrivilege: Privileges.VIEW_VEHICLE_MAP }
      },
      { path: 'not-found', component: NotFoundComponent},
      
      // other routes for authenticated pages...
      { path: '', redirectTo: '/manage-vehicles', pathMatch: 'full' }
    ]
  },

  // Redirect to login if no route matches
  { path: '**', redirectTo: '/login' }
];
