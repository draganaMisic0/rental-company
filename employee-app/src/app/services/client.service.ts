import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ClientData } from '../../models/client-data';
import { HttpClient } from '@angular/common/http';
import { config_params } from '../../config/config_params';





@Injectable({
  providedIn: 'root'
})
export class ClientService {
  
  
  private apiUrl = `${config_params.protocol}://${config_params.address}:${config_params.port}/clients`;

  constructor(private http: HttpClient) {}



  getAll(): Observable<ClientData[]> {
    return this.http.get<ClientData[]>(this.apiUrl);
  }

  add(client: ClientData): Observable<ClientData> {
    return this.http.post<ClientData>(this.apiUrl, client);
  }

  updateStatus(id: number, newStatus: boolean): Observable<ClientData> {
    const requestPayload = { newStatus };
    return this.http.put<ClientData>(`${this.apiUrl}/change_status/${id}`, requestPayload);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
