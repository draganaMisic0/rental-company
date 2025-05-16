import { Injectable } from '@angular/core';
import { config_params } from '../../config/config_params';
import { HttpClient } from '@angular/common/http';
import { Car } from '../../models/car-data';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CarService {
  private apiUrl = `${config_params.protocol}://${config_params.address}:${config_params.port}/vehicles/cars`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<Car[]> {
    return this.http.get<Car[]>(this.apiUrl);
  }

  getById(id: string): Observable<Car> {
    return this.http.get<Car>(`${this.apiUrl}/${id}`);
  }

  create(car: Car): Observable<Car> {
    return this.http.post<Car>(this.apiUrl, car);
  }

  update(id: string, car: Car): Observable<Car> {
    return this.http.put<Car>(`${this.apiUrl}/${id}`, car);
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  deleteAll(): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/all`);
  }
}
