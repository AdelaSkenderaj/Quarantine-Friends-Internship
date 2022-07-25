import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/model/model';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent implements OnInit {
  userLoggedIn: User;
  localStorage = localStorage;
  constructor(private userService: UserService, private router: Router) { }
  
  ngOnInit(): void {
    this.userService.userLoggedIn.next(
      JSON.parse(this.localStorage.getItem('user'))
    );
  }

}
