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
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
