import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';

export interface PointResponse {
  id: number;
  x: number;
  y: number;
  r: number;
  hitStatus: boolean;
  date: string | number[];
}

interface PointRequest {
  x: number;
  y: number;
  r: number;
}

@Injectable({
  providedIn: 'root'
})
export class PointService {
  constructor(private http: HttpClient) {}

  addPoint(x: number, y: number, r: number) {
    const body: PointRequest = { x, y, r };
    return this.http.post<PointResponse>(`${environment.apiUrl}/main/addPoint`, body);
  }

  getAllPoints() {
    return this.http.get<PointResponse[]>(`${environment.apiUrl}/main/getAll`);
  }
}
