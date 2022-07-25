//import { AlertComponent } from './../alert/alert.component';
import { User } from './../../model/model';
import { AuthService } from './../../services/auth.service';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { UserService } from './../../services/user.service';
import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  constructor(
    private router: Router,
    private userService: UserService,
    private authService: AuthService,
    private _snackBar: MatSnackBar
  ) {}
  username: string;
  password: string;
  invalidLogin: boolean;
  hide = true;
  ngOnInit(): void {}

  handleLogin() {
    this.authService
      .login(this.username, this.password)
      .subscribe((response: HttpResponse<User>) => {
        localStorage.setItem('user', JSON.stringify(response.body['user']));
        localStorage.setItem('token', response.body['token']);
        this.userService.userLoggedIn.next(response.body['user']);
        //this.openSnackBar();
        this.router.navigate(['/homepage']);
      });
  }


//   openSnackBar() {
//     this._snackBar.openFromComponent(AlertComponent, {
//       duration: 2000,
//     });
//   }
 }
