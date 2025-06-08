import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Bicycle } from '../../models/bicycle-data';
import { Observable } from 'rxjs';
import { config_params } from '../../config/config_params';

@Injectable({
  providedIn: 'root'
})
export class BicycleService {
  private apiUrl = `${config_params.protocol}://${config_params.address}:${config_params.port}/vehicles/bicycles`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<Bicycle[]> {
    return this.http.get<Bicycle[]>(this.apiUrl);
  }

  getById(id: string): Observable<Bicycle> {
    return this.http.get<Bicycle>(`${this.apiUrl}/${id}`);
  }

  create(bike: Bicycle): Observable<Bicycle> {
    return this.http.post<Bicycle>(this.apiUrl, bike);
  }

  update(bike: Bicycle): Observable<Bicycle> {
    return this.http.put<Bicycle>(this.apiUrl, bike);
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  deleteAll(): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/all`);
  }
}
