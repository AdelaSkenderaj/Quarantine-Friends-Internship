import { Component, OnInit } from '@angular/core';
import { MatchSuggestion, Report, User } from 'src/app/model/model';
import { MatchService } from 'src/app/services/match.service';
import { UserService } from 'src/app/services/user.service';
import { MatchRequest } from 'src/app/model/model';
import { ReportService } from 'src/app/services/report.service';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {
  
  matchSuggestions: MatchSuggestion[] = [];
  user:User;

  constructor(
    private userService :UserService,
    private matchService : MatchService,
    private reportService:ReportService,
  ) { }

  ngOnInit(): void {
    this.refreshSuggestions();
  }

  refreshSuggestions() {
    this.userService.userLoggedIn.subscribe(response => {
      this.user = response;
      this.matchService.getMatches(this.user.id).subscribe((response: MatchSuggestion[]) => {
        this.matchSuggestions=response;
        this.matchSuggestions.sort((a : MatchSuggestion, b:MatchSuggestion) => a.matchingPercentage < b.matchingPercentage ? 1 : -1);
      });
    });
  }

  addMatchRequest(sendUser:User) {
    let request:MatchRequest = new MatchRequest();
    request.fromUser = this.user;
    request.toUser = sendUser;
    this.matchService.addMatchRequest(request).subscribe(response => {
      this.refreshSuggestions();
    });

  }

  blockUser(id: number) {
      this.userService.blockUser(id, this.user).subscribe(response => {
        this.refreshSuggestions();
      });
  }

  reportUser(user:User) {
    let report = new Report();
    report.user = user;
    report.date = new Date();
    this.reportService.reportUser(report).subscribe();
  }
}
