import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { MatchSuggestion } from '../model/model';
import { User } from '../model/model';
import { MatchRequest } from '../model/model';

@Injectable({
  providedIn: 'root'
})
export class MatchService {

  constructor(private httpClient: HttpClient) { }

  getMatches(id: number): Observable<MatchSuggestion[]> {
    return this.httpClient.get<MatchSuggestion[]>(`http://localhost:8080/matches/${id}`);
  }

  addMatchRequest(matchRequest: MatchRequest) {
    return this.httpClient.post(`http://localhost:8080/match-request`, matchRequest);
  }

  deleteMatchRequest(id:number) {
    return this.httpClient.delete(`http://localhost:8080/match-request-remove/${id}`);
  }

  acceptMatchRequest(id:number) {
    return this.httpClient.delete(`http://localhost:8080/match-request-accept/${id}`);
  }

  getRequestsForUser(id:number): Observable<MatchRequest[]> {
    return this.httpClient.get<MatchRequest[]>(`http://localhost:8080/match-request-to/${id}`);
  }

  getRequestsFromUser(id:number): Observable<MatchRequest[]> {
    return this.httpClient.get<MatchRequest[]>(`http://localhost:8080/match-request-from/${id}`);
  }

  unmatch(friendId:number, user:User) {
    return this.httpClient.put(`http://localhost:8080/unmatch/${friendId}`, user);
  }
}
