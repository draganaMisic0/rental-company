import { Routes } from '@angular/router';
import { LoginPageComponent } from './login/login-page/login-page.component';
import { AppComponent } from './app.component';
import { ManageVehiclesComponent } from './main/manage-vehicles/manage-vehicles.component';
import { ManageUsersComponent } from './main/manage-users/manage-users.component';
import { ManageManufacturersComponent } from './main/manage-manufacturers/manage-manufacturers.component';
import { MainLayoutComponent } from './layout/main-layout/main-layout.component';
import { ManageRentalsComponent } from './main/manage-rentals/manage-rentals.component';
import { StatisticsComponent } from './main/statistics/statistics.component';
import { ReportIssuesComponent } from './main/report-issues/report-issues.component';
import { SetRentalPricesComponent } from './main/set-rental-prices/set-rental-prices.component';
import { VehiclesMapViewComponent } from './main/vehicles-map-view/vehicles-map-view.component';

export const routes: Routes = [
    { path: 'login', component: LoginPageComponent },
    // Main layout route
  {
    path: '',
    component: MainLayoutComponent,
    canActivate: [],  // Optional guard to ensure the user is logged in
    children: [
      { path: 'manage-vehicles', component: ManageVehiclesComponent },
      { path: 'manage-users', component: ManageUsersComponent },
      { path: 'manage-manufacturers', component: ManageManufacturersComponent },
      { path: 'manage-rentals', component: ManageRentalsComponent },
      { path: 'statistics', component: StatisticsComponent },
      { path: 'report-issues', component: ReportIssuesComponent },
      { path: 'set-rental-prices', component: SetRentalPricesComponent },
      { path: 'vehicles-map-view', component: VehiclesMapViewComponent },
      
      // other routes for authenticated pages...
      { path: '', redirectTo: '/manage-vehicles', pathMatch: 'full' }
    ]
  },

  // Redirect to login if no route matches
  { path: '**', redirectTo: '/login' }
];
