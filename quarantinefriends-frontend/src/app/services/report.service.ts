import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Report } from '../model/model';
@Injectable({
  providedIn: 'root'
})
export class ReportService {

  constructor(
    private httpClient: HttpClient
  ) { }

  reportUser(report:Report) {
    return this.httpClient.post(`http://localhost:8080/reports`, report);
  }
}
