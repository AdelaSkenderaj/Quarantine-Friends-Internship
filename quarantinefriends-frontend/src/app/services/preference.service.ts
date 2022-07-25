import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Preference } from '../model/model';

@Injectable({
  providedIn: 'root'
})
export class PreferenceService {

  private rootUrl:string = 'http://localhost:8080';

  constructor(private httpClient:HttpClient) { }

  getPreferences(){
    return this.httpClient.get<Preference[]>(`${this.rootUrl}/preferences`);
  }
}
