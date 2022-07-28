import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {

  constructor(
    private router: Router,
    private userService:UserService,
  ) { }

  email:string;

  ngOnInit(): void {
  }

  onSubmit() {
    this.userService.forgotPassword(this.email).subscribe((response) => {
      this.router.navigate(['/login']);
    })
  }

}
