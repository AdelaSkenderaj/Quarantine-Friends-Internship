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
displayColumnsHobby = ['Hobby Name', 'Times Chosen'];
displayColumnsPreference =['Preference Name', 'Times Chosen'];

  constructor(
    private userService:UserService,
    private statisticsService:StatisticsService,
  ) { }

  ngOnInit(): void {
    this.userService.userLoggedIn.subscribe((response) => {
      this.user = response;
      this.statisticsService.getStatistics().subscribe((response) => {
        this.statistics = response;
        this.statistics.hobbyStatistics.sort((a : HobbyStatistics, b:HobbyStatistics) => a.timesChosen < b.timesChosen? 1 : -1);
        this.statistics.preferenceStatistics.sort((a : PreferenceStatistics, b : PreferenceStatistics) => a.timesChosen < b.timesChosen? 1 : -1);
      })
    })
  }

}
