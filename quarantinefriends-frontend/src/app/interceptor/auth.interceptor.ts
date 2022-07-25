import { AuthService } from './../services/auth.service';
import { UserService } from './../services/user.service';
import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(
    private userService: UserService,
    private authService: AuthService
  ) {}

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    if (request.url.includes('login')) {
      return next.handle(request);
    }

    this.authService.loadToken();
    const token = this.authService.getToken();
    let clonedRequest = request.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`,
      },
    });
    return next.handle(clonedRequest);
  }
}
