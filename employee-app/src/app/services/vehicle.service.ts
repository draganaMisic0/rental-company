import { Injectable } from '@angular/core';
import { config_params } from '../../config/config_params';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Vehicle } from '../../models/vehicles-data';

@Injectable({
  providedIn: 'root'
})
export class VehicleService {

  private apiUrl = `${config_params.protocol}://${config_params.address}:${config_params.port}/vehicles`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<Vehicle[]> {
      return this.http.get<Vehicle[]>(this.apiUrl);
    }
  
    getById(id: string): Observable<Vehicle> {
      return this.http.get<Vehicle>(`${this.apiUrl}/${id}`);
    }
}
