import { Injectable } from '@angular/core';
import { config_params } from '../../config/config_params';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ImagesService {

  private apiUrl = `${config_params.protocol}://${config_params.address}:${config_params.port}/images`;

  constructor(private http: HttpClient) {}

  uploadImage(type: string, id: string, file: File): Observable<string> {
    const formData = new FormData();
    formData.append('type', type);
    formData.append('id', id);
    formData.append('file', file);

    return this.http.post(`${this.apiUrl}/upload`, formData, { responseType: 'text' });
  }

  getVehicleImageUrl(vehicleId: string): string {
   return `${this.apiUrl}/vehicle/id/${vehicleId}`;
  }

  deleteVehicleImage(vehicleId: string): Observable<void> {
  return this.http.delete<void>(`${this.apiUrl}/vehicle/${vehicleId}`);
}

}
