import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { User } from 'src/app/model/model';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  user:User;
  loggedInUser:User;
  areFriends:boolean;
  friendRequestSent:boolean;
  id:number;
  constructor(
    private activatedRoute: ActivatedRoute,
    private userService: UserService,
  ) { }

  ngOnInit(): void {
    this.userService.userLoggedIn.subscribe((response)=> {
      this.loggedInUser = response;
      this.loadUserDetails();
    })
    
  }

  loadUserDetails() {
    this.activatedRoute.paramMap.subscribe((paramMap) => {
      this.id =+ paramMap.get('id');
      this.userService.getUserById(this.id).subscribe((response) => {
        this.user = response;
      })
    })
  }

  

}
