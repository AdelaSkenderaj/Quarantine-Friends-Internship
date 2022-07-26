import { Component, OnInit } from '@angular/core';
import { Report, User } from 'src/app/model/model';
import { ReportService } from 'src/app/services/report.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-blocked-users',
  templateUrl: './blocked-users.component.html',
  styleUrls: ['./blocked-users.component.css']
})
export class BlockedUsersComponent implements OnInit {
  user:User;
  blockedUsers:User[] = [];
  loading:boolean = false;
  displayColumns = ['Photo', 'First Name', 'Last Name', 'Unblock', 'Report User'];

  constructor(
    private userService:UserService,
    private reportService: ReportService,
  ) { }

  ngOnInit(): void {
    this.userService.userLoggedIn.subscribe((response) => {
      this.user = response;
      this.refreshUsers();
    })
  }

  refreshUsers(){
    this.userService.getBlockedUsers(this.user.id).subscribe((response) => {
      this.blockedUsers= response;
    })
  }

  unblockUser(id:number) {
    this.userService.unblockUser(id, this.user).subscribe((response) => {
      this.refreshUsers();
    })
  }

  reportUser(reportedUser:User) {
    let report: Report = new Report();
    report.user = reportedUser;
    report.date = new Date();
    this.reportService.reportUser(report).subscribe((response) => {
      this.refreshUsers();
    })
  }

}
