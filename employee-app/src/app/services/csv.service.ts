import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { config_params } from '../../config/config_params';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CsvService {

  
  private apiUrl = `${config_params.protocol}://${config_params.address}:${config_params.port}/csv`;

  constructor(private http: HttpClient) {}

  bulkUploadVehicles(file: File): Observable<string> {
        const formData = new FormData();
        formData.append('file', file);

        return this.http.post<string>(`${this.apiUrl}/bulk-upload`, formData);
    }
}
