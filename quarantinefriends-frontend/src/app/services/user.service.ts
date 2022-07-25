import {  User, Role } from './../model/model';
import { EventEmitter, Injectable, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { BehaviorSubject, map, Observable, Subject } from 'rxjs';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root',
})
export class UserService implements OnInit {
  user = new EventEmitter<User>();
  userRole = new EventEmitter<Role>();
  private token: string;
  private jwtHelper = new JwtHelperService();
  eventSource: Subject<EventSource> = new BehaviorSubject<EventSource>(
    undefined
  );
  userLoggedIn: Subject<User> = new BehaviorSubject<User>(
    JSON.parse(localStorage.getItem('user'))
  );
  loggedInUsername: string;
  theUserLoggedIn: Subject<User> = new BehaviorSubject<User>(null);

  constructor(private httpClient: HttpClient, private router: Router) {}
  ngOnInit(): void {
     this.userLoggedIn.next(JSON.parse(localStorage.getItem('user')));
  }

  getUsers(): Observable<User[]> {
    return this.httpClient.get<User[]>(`http://localhost:8080/users`);
  }

  addUser(user: User) {
    return this.httpClient.post('http://localhost:8080/register', user);
  }

//   deleteUser(id: number) {
//     return this.httpClient.delete(`http://localhost:8080/api/delete/${id}`);
//   }

//   updateUser(user: User): Observable<HttpResponse<User>> {
//     return this.httpClient.put<HttpResponse<User>>(
//       `${this.userEndPoint}/${user.id}`,
//       user
//     );
//   }

  getUserById(id: number): Observable<User> {
    return this.httpClient.get<User>(`http://localhost:8080/user/${id}`);
  }

  login(username: string, password: string) {
    return this.httpClient.post<User>('http://localhost:8080/login', {
      username: username,
      password: password,
    });
  }
  saveToken(token: string) {
    this.token = token;
    localStorage.setItem('token', token);
  }

  public isLoggedIn(): boolean {
    this.token = localStorage.getItem('token');
    if (this.token != null && this.token !== '') {
      if (this.jwtHelper.decodeToken(this.token).sub != null || '') {
        if (!this.jwtHelper.isTokenExpired(this.token)) {
          this.loggedInUsername = this.jwtHelper.decodeToken(this.token).sub;
          return true;
        }
      }
    } else {
      this.logout();
      return false;
    }
  }

  logout() {
    localStorage.removeItem('token');
  }


  public resetPassword(id: number, password: string) {
    return this.httpClient.put(
      `http://localhost:8080/reset-password/${id}`, password);
  }
//   uploadFile(id: number, file: File) {
//     const fd = new FormData();
//     fd.append('image', file, file.name);
//     return this.httpClient.post(
//       `http://localhost:8080/api/${id}/upload-image`,
//       file
//     );
//   }

  getEventSource() {
    return this.eventSource;
  }
}
