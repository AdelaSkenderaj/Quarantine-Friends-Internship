import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/model/model';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  user:User;
  constructor(
    private userService:UserService,
    private router:Router,
  ) { }

  ngOnInit(): void {
    this.userService.userLoggedIn.subscribe((response) => {
      this.user = response;
      if(this.user.role.name == 'ROLE_ADMIN') {
        this.router.navigate(['/dashboard']);
      }
      else {
        this.router.navigate(['/homepage']);
      }
    })
  }

}
