import { Injectable } from '@angular/core';
import { LoginRequest } from '../../models/requests/login-request';
import { catchError, Observable, throwError } from 'rxjs';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { config_params } from '../../config/config_params';
import { EmployeeData } from '../../models/employee-data';


@Injectable({
  providedIn: 'root',
})
export class LoginService {
  
  

  private apiUrl = `${config_params.protocol}://${config_params.address}:${config_params.port}/employees`; // Base URL of your Spring API

  constructor(private http: HttpClient) {}

  
  login(credentials: LoginRequest): Observable<EmployeeData> {
    return this.http.post<EmployeeData>(`${this.apiUrl}/login`, credentials, {responseType: 'json'})
      .pipe(catchError(this.handleError))
  }

  private handleError(error: HttpErrorResponse) {
    if (error.status === 401) {
      return throwError(() => new Error('Invalid username or password.'));
    } else {
      return throwError(() => new Error('An unexpected error occurred.'));
    }
  }
}
