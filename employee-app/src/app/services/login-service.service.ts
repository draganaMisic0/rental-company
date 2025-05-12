import { Injectable } from '@angular/core';
import { LoginRequest } from '../login/login-page/login-request';
import { catchError, Observable, throwError } from 'rxjs';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  private apiUrl = 'http://localhost:8080/employees'; // Base URL of your Spring API

  constructor(private http: HttpClient) {}

  login(credentials: LoginRequest): Observable<string> {
    return this.http.post(`${this.apiUrl}/login`, credentials, { responseType: 'text' })
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
