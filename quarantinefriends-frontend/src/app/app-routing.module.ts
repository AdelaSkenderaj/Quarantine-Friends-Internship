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
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
