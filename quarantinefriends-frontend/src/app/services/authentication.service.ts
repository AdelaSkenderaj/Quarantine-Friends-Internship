import { ActivatedRoute, Params } from '@angular/router';
import { UserService } from './user.service';
import { Injectable, OnInit } from '@angular/core';
import { User } from '../model/model';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService implements OnInit {
  user: User;
  id: number = -1;
  constructor(
    private UserService: UserService,
    private route: ActivatedRoute
  ) {}
  ngOnInit(): void {
    this.route.params.subscribe((params: Params) => {
      this.id = +params['id1'];
      console.log('Id inside subscribe' + this.id);
    });
  }
  isAdminLoggedIn(): boolean {
    console.log('before test');
    let user: User = JSON.parse(localStorage.getItem('user'));
    if (user.role.name === 'ROLE_ADMIN') {
      console.log('inside role_admin');
      return true;
      console.log('after true');
    }
    return false;
  }

  isUserLoggedIn(): boolean {
    let user: User = JSON.parse(localStorage.getItem('user'));
    if (user) {
      return true;
    }
    return false;
  }

  isUserAllowedToUpdate(): boolean {
    console.log('Id on start of method ' + this.id);
    this.id = +this.route.snapshot.params['id'];
    let user: User = JSON.parse(localStorage.getItem('user'));

    console.log('inside auth');
    console.log('The id: ' + this.id);
    if (user.id === this.id || this.isAdminLoggedIn()) {
      return true;
    }
    return false;
  }
}
