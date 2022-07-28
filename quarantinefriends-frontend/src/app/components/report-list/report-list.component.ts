import { Component, OnInit } from '@angular/core';
import {Report, User} from "../../model/model";
import {ReportService} from "../../services/report.service";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-report-list',
  templateUrl: './report-list.component.html',
  styleUrls: ['./report-list.component.css']
})
export class ReportListComponent implements OnInit {
  reports:Report[] = [];
  displayColumns =['Report Number', 'User Details', 'Date', 'Action'];

  constructor(
    private reportService:ReportService,
    private userService:UserService,
  ) { }

  ngOnInit(): void {
    this.refreshUsers();
  }

  refreshUsers() {
    this.reportService.getAllReports().subscribe((response)=> {
      this.reports = response;
    });
  }

  banUser(user:User ) {
    user.accountTerminated = true;
    this.userService.terminateAccount(user.id, user).subscribe((response) => {
      this.refreshUsers();
    });
  }

}
