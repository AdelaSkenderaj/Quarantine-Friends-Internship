import { Observable } from 'rxjs';
import { User } from './../model/model';
import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpEvent } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class UserModelService {
  hostUrl: string = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  public getUsers(): Observable<User[] | HttpErrorResponse> {
    return this.http.get<User[]>(`${this.hostUrl}/users`);
  }

  public addUser(
    formData: FormData
  ): Observable<User | HttpErrorResponse> {
    return this.http.post<User>(`${this.hostUrl}/user/register`, formData);
  }

  public updateUser(
    formData: FormData
  ): Observable<User | HttpErrorResponse> {
    return this.http.post<User>(`${this.hostUrl}/user/update`, formData);
  }

  public resetPassword(email: string): Observable<any | HttpErrorResponse> {
    return this.http.get(`${this.hostUrl}/user/resetpassword/${email}`);
  }

  public updateProfileImage(
    formData: FormData
  ): Observable<HttpEvent<User>> {
    return this.http.post<User>(
      `${this.hostUrl}/user/updateProfileImage`,
      formData,
      { reportProgress: true, observe: 'events' }
    );
  }

  public deleteUser(userId: number): Observable<any> {
    return this.http.delete<User>(`${this.hostUrl}/delete/${userId}`);
  }

  public addUsersToLocalCache(users: User[]): void {
    localStorage.setItem('users', JSON.stringify(users));
  }

  public getUserFromLocalCache(): User[] {
    if (localStorage.getItem('users')) {
      return JSON.parse(localStorage.getItem('users'));
    }
    return null;
  }

  createUserFormData(
    loggedInUsername: string,
    user: User,
    profileImage: File
  ): FormData {
    const formData = new FormData();
    formData.append('currentUsername', loggedInUsername);
    formData.append('firstName', user.firstName);
    formData.append('lastName', user.lastName);
    formData.append('username', user.username);
    formData.append('email', user.email);
    formData.append('role', user.role.name);
    formData.append('profileImage', profileImage);

    return formData;
  }
}
