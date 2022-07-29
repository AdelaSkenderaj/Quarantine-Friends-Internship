import { User, Hobby, Preference } from '../../model/model';
import { UserService } from './../../services/user.service';
import { Component,Inject, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { Subscription } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { HobbyService } from 'src/app/services/hobby.service';
import { PreferenceService } from 'src/app/services/preference.service';
@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css'],
})
export class SignupComponent implements OnInit {
  userFirstName:string;
  userLastName:string;
  usersername:string;
  userAge:number;
  userEmail:string;
  userPassword:string;
  userHobbies: Hobby[] = [];
  userPreferences: Preference[] = [];
  hide = true;

  allHobbies:Hobby[] = [];
  allPreferences: Preference[] = [];

  dropdownList=[];

  form = this.formBuilder.group({
    firstname:['', [Validators.required]],
    lastname: ['', Validators.required],
    username: ['', Validators.required],
    age: ['' , Validators.required],
    email: ['', Validators.required],
    password: ['', Validators.required],
    hobbies: [[]],
    preferences: [[]],
  });

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private hobbyService:HobbyService,
    private preferenceService:PreferenceService
  ) {}

  ngOnInit(): void {
    this.hobbyService.getHobbies().subscribe((response) => {
      this.allHobbies = response;
      this.preferenceService.getPreferences().subscribe((response) => {
        this.allPreferences = response;
      })
    })

    console.log(this.allHobbies);
    console.log(this.allPreferences);
  }

  loadHobbies() {
      this.hobbyService.getHobbies().subscribe((response) => {
        this.allHobbies = response;
      });
      console.log(this.allHobbies);
  }

  loadPreferences() {
      this.preferenceService.getPreferences().subscribe((response) => {
        this.allPreferences = response;
      })
      console.log(this.allPreferences);
  }

  onSubmit() {
    let user = new User();
    user.firstName = this.form.controls['firstname'].value;
    user.lastName = this.form.controls['lastname'].value;
    user.username = this.form.controls['username'].value;
    user.age = Number(this.form.controls['age'].value);
    user.email= this.form.controls['email'].value;
    user.password=this.form.controls['password'].value;

    let hobbyId = this.form.controls['hobbies'].value;

    for(let h of this.allHobbies) {
      for(let id of hobbyId) {
        if(h.id == id) {
          this.userHobbies.push(h);
        }
      }
    }

    let preferenceId = this.form.controls['preferences'].value;

    for(let p of this.allPreferences) {
      for(let id of preferenceId) {
        if(p.id == id) {
          this.userPreferences.push(p);
        }
      }
    }

    user.hobbies = this.userHobbies;
    user.preferences = this.userPreferences;
    this.userService.addUser(user).subscribe((response) => {
      console.log("user added");
      this.router.navigateByUrl('/login');
    });


  }

}
