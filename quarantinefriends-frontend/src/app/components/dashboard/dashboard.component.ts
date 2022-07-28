import { Component, OnInit } from '@angular/core';
import {HobbyStatistics, PreferenceStatistics, Statistics} from "../../model/statistics";
import {User} from "../../model/model";
import {UserService} from "../../services/user.service";
import {StatisticsService} from "../../services/statistics.service";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
user:User;
statistics:Statistics;

  constructor(
    private userService:UserService,
    private statisticsService:StatisticsService,
  ) { }

  ngOnInit(): void {
    this.userService.userLoggedIn.subscribe((response) => {
      this.user = response;
      this.statisticsService.getStatistics().subscribe((response) => {
        this.statistics = response;
      })
    })
  }

}
