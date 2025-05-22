import { Injectable } from '@angular/core';
import { config_params } from '../../config/config_params';
import { HttpClient } from '@angular/common/http';
import { Malfunction } from '../../models/malfunction-data';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MalfunctionService {
private apiUrl = `${config_params.protocol}://${config_params.address}:${config_params.port}/malfunctions`; // Base URL of your Spring API

  constructor(private http: HttpClient) {}

    getAll(): Observable<Malfunction[]> {
      return this.http.get<Malfunction[]>(this.apiUrl,  {responseType: 'json'});
    }
  
    get(id: number): Observable<Malfunction> {
      return this.http.get<Malfunction>(`${this.apiUrl}/${id}`, {responseType: 'json'});
    }

    getAllByVehicleId(id: string): Observable<Malfunction[]> {
      return this.http.get<Malfunction[]>(`${this.apiUrl}/vehicle/${id}`,  {responseType: 'json'});
    }

    add(malfunction: Malfunction): Observable<Malfunction> {
        return this.http.post<Malfunction>(this.apiUrl, malfunction,  {responseType: 'json'});
      }
    
    update(id: number, malfunction: Malfunction): Observable<Malfunction> {
        return this.http.put<Malfunction>(`${this.apiUrl}/${id}`, malfunction,  {responseType: 'json'});
      }
    
    delete(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
      }
}
