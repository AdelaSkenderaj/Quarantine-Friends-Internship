import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Message } from '../model/model';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  constructor(
    private httpClient: HttpClient,
  ) { }

  getMessagesForFriends(friendOneId: number, friendTwoId:number): Observable<Message[]> {
    return this.httpClient.get<Message[]>(`http://localhost:8080/messages/${friendOneId}/ ${friendTwoId}`);
  }

  addMessage(message:Message) {
    return this.httpClient.post(`http://localhost:8080/messages`, message);
  }
}
