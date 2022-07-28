import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserGuard } from './guards/user-guard';
import { ProfileGuardGuard } from './guards/profile-guard.guard';
import { UpdateGuard } from './guards/update.guard';
import { AuthenticationGuard } from './guards/authentication.guard';
import { LoginComponent } from './components/login/login.component';
import { HomepageComponent } from './components/homepage/homepage.component';
import { SignupComponent } from './components/signup/signup.component';
import { RequestsComponent } from './components/requests/requests.component';
import { BlockedUsersComponent } from './components/blocked-users/blocked-users.component';
import { ProfileComponent } from './components/profile/profile.component';
import { SettingsComponent } from './components/settings/settings.component';
import { FriendListComponent } from './components/friend-list/friend-list.component';
import { ChatComponent } from './components/chat/chat.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { HomeComponent } from './components/home/home.component';
import {UserListComponent} from "./components/user-list/user-list.component";
import {ReportListComponent} from "./components/report-list/report-list.component";
import {BannedUserListComponent} from "./components/banned-user-list/banned-user-list.component";


const routes: Routes = [
  {
    path: 'login', component: LoginComponent
  },
  {
    path: 'homepage',
    component: HomepageComponent,
  },
  {
    path: 'signup', component: SignupComponent,
  },
  {
    path: 'match-requests', component:RequestsComponent,
  },
  {
    path: 'blocked-users', component: BlockedUsersComponent,
  },
  {
    path: 'profile/:id', component: ProfileComponent,
  },
  {
    path:'settings', component:SettingsComponent,
  },
  {
    path:'friends', component:FriendListComponent,
  },
  {
    path: 'chat/:id', component:ChatComponent,
  },
  {
    path: 'dashboard', component: DashboardComponent,
  },
  {
    path: 'home', component: HomeComponent,
  },
  {
    path:'users', component: UserListComponent,
  },
  {
    path: 'reports', component:ReportListComponent,
  },
  {
    path: 'banned-users', component:BannedUserListComponent,
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
