import { Component, OnInit } from '@angular/core';
import { MatchRequest, User } from 'src/app/model/model';
import { MatchService } from 'src/app/services/match.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-requests',
  templateUrl: './requests.component.html',
  styleUrls: ['./requests.component.css']
})
export class RequestsComponent implements OnInit {

  receivedRequests:MatchRequest[]= [];
  sentRequests: MatchRequest[] = [];
  user:User;

  constructor(
    private userService:UserService,
    private matchService:MatchService,
  ) { }

  ngOnInit(): void {
    this.userService.userLoggedIn.subscribe((response) => {
      this.user = response;
      this.refreshReceivedRequests();
      this.refreshSentRequests();
    })
  }

  refreshReceivedRequests() {
    this.matchService.getRequestsForUser(this.user?.id).subscribe((response) => {
      this.receivedRequests = response;
    })

  }

  refreshSentRequests() {
    this.matchService.getRequestsFromUser(this.user?.id).subscribe((response) => {
      this.sentRequests = response;
    })

  }

  acceptRequest(id:number) {
    this.matchService.acceptMatchRequest(id).subscribe((response) => {
      this.refreshReceivedRequests();
    })
  }

  deleteRequest(id:number) {
    this.matchService.deleteMatchRequest(id).subscribe((response) => {
      this.refreshReceivedRequests();
      this.refreshSentRequests();
    })
  }

}
