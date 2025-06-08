import { Injectable } from '@angular/core';
import { config_params } from '../../config/config_params';
import { HttpClient } from '@angular/common/http';
import { Manufacturer } from '../../models/manufacturer-data';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ManufacturerService {
  private apiUrl = `${config_params.protocol}://${config_params.address}:${config_params.port}/manufacturers`; // Base URL of your Spring API

  constructor(private http: HttpClient) {}

  getAll(): Observable<Manufacturer[]> {
    return this.http.get<Manufacturer[]>(this.apiUrl,  {responseType: 'json'});
  }

  get(id: number): Observable<Manufacturer> {
    return this.http.get<Manufacturer>(`${this.apiUrl}/${id}`, {responseType: 'json'});
  }

  add(manufacturer: Manufacturer): Observable<Manufacturer> {
    return this.http.post<Manufacturer>(this.apiUrl, manufacturer,  {responseType: 'json'});
  }

  update(id: number, manufacturer: Manufacturer): Observable<Manufacturer> {
    return this.http.put<Manufacturer>(`${this.apiUrl}/${id}`, manufacturer,  {responseType: 'json'});
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
