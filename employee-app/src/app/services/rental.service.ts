import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { config_params } from '../../config/config_params';
import { Rental } from '../../models/rental-data';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RentalService {

  private apiUrl = `${config_params.protocol}://${config_params.address}:${config_params.port}/rentals`; // Base URL of your Spring API

  constructor(private http: HttpClient) { }

    getAll(): Observable<Rental[]> {
      return this.http.get<Rental[]>(this.apiUrl);
    }

    get(id: number): Observable<Rental> {
      return this.http.get<Rental>(`${this.apiUrl}/${id}`);
    }

    getByVehicleId(id: string): Observable<Rental[]> {
      return this.http.get<Rental[]>(`${this.apiUrl}/by_veh/${id}`);
    }


}
