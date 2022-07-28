import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Statistics } from '../model/statistics';

@Injectable({
  providedIn: 'root'
})
export class StatisticsService {

  constructor(
    private httpClient: HttpClient,
  ) { }

  getStatistics(): Observable<Statistics> {
    return this.httpClient.get<Statistics>(`http://localhost:8080/statistics`);
  }
}
