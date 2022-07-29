import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Hobby } from '../model/model';

@Injectable({
  providedIn: 'root'
})
export class HobbyService {

  private rootUrl:string = 'http://localhost:8080';

  constructor(private httpClient:HttpClient) { }

  getHobbies(){
    return this.httpClient.get<Hobby[]>(`${this.rootUrl}/hobbies`);
  }
}
