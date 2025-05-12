import { Routes } from '@angular/router';
import { LoginPageComponent } from './login/login-page/login-page.component';

export const routes: Routes = [
    {   path: 'login-page', component: LoginPageComponent},
    {   path: '',   redirectTo: '/login-page', pathMatch: 'full'}
];
