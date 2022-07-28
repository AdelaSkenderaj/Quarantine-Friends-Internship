import { Component, OnInit } from '@angular/core';
import {UserService} from "../../services/user.service";
import {User} from "../../model/model";
import {SelectionModel} from "@angular/cdk/collections";

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {
  users:User[] = [];
  displayColumns = ['Profile Photo', 'First Name', 'Last Name', 'Age', 'Terminate Account'];

  constructor(
    private userService: UserService,
  ) { }

  ngOnInit(): void {
    this.refreshUsers();
  }

  refreshUsers() {
    this.userService.getEnabledUsers().subscribe((response) => {
      this.users = response;
    })
  }

  banUser(user:User ) {
    user.accountTerminated = true;
    this.userService.terminateAccount(user.id, user).subscribe((response) => {
      this.refreshUsers();
    })

  }


}
