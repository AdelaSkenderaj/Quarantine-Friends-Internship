import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/model/model';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-friend-list',
  templateUrl: './friend-list.component.html',
  styleUrls: ['./friend-list.component.css']
})
export class FriendListComponent implements OnInit {

  user:User;
  friends: User[] = [];
  constructor(
    private userService:UserService,
  ) { }

  ngOnInit(): void {
    this.userService.userLoggedIn.subscribe((response) => {
      this.user = response;
      this.refreshFriends();
    })
  }

  refreshFriends() {
    this.userService.getFriendsByUserId(this.user.id).subscribe((response) => {
      this.friends = response;
    })
  }
}
