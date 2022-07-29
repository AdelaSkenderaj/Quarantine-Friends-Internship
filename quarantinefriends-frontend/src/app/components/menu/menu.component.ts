import { Router } from '@angular/router';
import { User, Role } from '../../model/model';
import { UserService } from './../../services/user.service';
import { Component, OnInit } from '@angular/core';
@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css'],
})
export class MenuComponent implements OnInit {
  userLoggedIn: User;
  localStorage = localStorage;
  eventSource: EventSource;

  constructor(private userService: UserService, private router: Router) {}

  ngOnInit(): void {
    this.userService.userLoggedIn.next(
      JSON.parse(this.localStorage.getItem('user'))
    );
    this.userService.userLoggedIn.subscribe((response) => {
      this.userLoggedIn = response;
    })
    console.log(this.userLoggedIn);
    if(!this.userLoggedIn) {
      this.router.navigate(['/login']);
    }
    
  }


  logout() {
    this.localStorage.clear();
    this.router.navigate(['/login']);
    this.userService.userLoggedIn.next(null);
    this.userService.userRole.next(null);
  }
  onClickProfile() {
    this.router.navigateByUrl(`/edit/${this.userLoggedIn.id}`);
  }
}
