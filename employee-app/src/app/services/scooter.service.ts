import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Scooter } from '../../models/scooter-data';
import { Observable } from 'rxjs';
import { config_params } from '../../config/config_params';

@Injectable({
  providedIn: 'root'
})
export class ScooterService {
  private apiUrl = `${config_params.protocol}://${config_params.address}:${config_params.port}/vehicles/scooters`;

  

  constructor(private http: HttpClient) {}

  getAll(): Observable<Scooter[]> {
    return this.http.get<Scooter[]>(this.apiUrl);
  }

  getById(id: string): Observable<Scooter> {
    return this.http.get<Scooter>(`${this.apiUrl}/${id}`);
  }

  create(scooter: Scooter): Observable<Scooter> {
    return this.http.post<Scooter>(this.apiUrl, scooter);
  }

  update(scooter: Scooter): Observable<Scooter> {
    return this.http.put<Scooter>(this.apiUrl, scooter);
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  deleteAll(): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/all`);
  }
}
