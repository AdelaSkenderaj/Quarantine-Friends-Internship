import { Component, OnInit } from '@angular/core';
import {User} from "../../model/model";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-banned-user-list',
  templateUrl: './banned-user-list.component.html',
  styleUrls: ['./banned-user-list.component.css']
})
export class BannedUserListComponent implements OnInit {
users:User[] = [];
displayColumns = ['Profile Photo', 'First Name' , 'Last Name', 'Age' , 'Remove Ban'];
  constructor(
    private userService:UserService,
  ) { }

  ngOnInit(): void {
    this.refreshUsers();
  }

  refreshUsers() {
    this.userService.getBannedUsers().subscribe((response) => {
      this.users = response;
      console.log(this.users);
    })
  }

  revokeBan(user:User) {
    this.userService.revokeBan(user.id, user).subscribe((response) => {
      this.refreshUsers();
    })
  }

}
