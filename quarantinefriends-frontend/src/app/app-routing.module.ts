import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserGuard } from './guards/user-guard';
import { ProfileGuardGuard } from './guards/profile-guard.guard';
import { UpdateGuard } from './guards/update.guard';
import { AuthenticationGuard } from './guards/authentication.guard';
import { LoginComponent } from './components/login/login.component';
import { HomepageComponent } from './components/homepage/homepage.component';
import { SignupComponent } from './components/signup/signup.component';


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
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
