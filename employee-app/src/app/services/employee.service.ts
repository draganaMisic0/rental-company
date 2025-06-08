import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { EmployeeData } from '../../models/employee-data';
import { Observable } from 'rxjs';
import { config_params } from '../../config/config_params';
import { AddEmployeeRequest } from '../../models/requests/add-employee-request';


@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
  private apiUrl = `${config_params.protocol}://${config_params.address}:${config_params.port}/employees`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<EmployeeData[]> {
    return this.http.get<EmployeeData[]>(this.apiUrl);
  }

  add(employee: AddEmployeeRequest): Observable<EmployeeData> {
    
    return this.http.post<EmployeeData>(`${this.apiUrl}/register`, employee);
  }

  update(id: number, employee: EmployeeData): Observable<EmployeeData> {
    return this.http.put<EmployeeData>(`${this.apiUrl}/${id}`, employee, {responseType: 'json'});
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
