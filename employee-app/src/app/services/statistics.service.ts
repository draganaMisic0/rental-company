import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { config_params } from '../../config/config_params';


@Injectable({
  providedIn: 'root'
})
export class StatisticsService {

    private apiUrl = `${config_params.protocol}://${config_params.address}:${config_params.port}/statistics`;

  constructor(private http: HttpClient) {}

  getDailyIncome(startDate: Date, endDate: Date): Observable<{ [date: string]: number }> {
    const start = startDate.toISOString(); // Native ISO 8601 format
    const end = endDate.toISOString();
    return this.http.get<{ [date: string]: number }>(`${this.apiUrl}/income/daily?startDate=${start}&endDate=${end}`);
  }

  getVehicleMalfunctions(): Observable<{ [date: string]: number }> {

    return this.http.get<{ [date: string]: number }>(`${this.apiUrl}/malfunctions/per-vehicle`);
  }

  getIncomePerVehicleType(): Observable<{ [type: string]: number }> {

    return this.http.get<{ [type: string]: number }>(`${this.apiUrl}/income/vehicle-type`);
  }
}
