import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Hobby, Preference, User } from 'src/app/model/model';
import { UserService } from 'src/app/services/user.service';
import { HobbyService } from 'src/app/services/hobby.service';
import { PreferenceService } from 'src/app/services/preference.service';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpEventType, HttpResponse } from '@angular/common/http';
import { FileService } from 'src/app/services/file.service';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {
  user:User;
  selectedFiles?: FileList;
  currentFile?: File;
  message = '';
  errorMsg = '';
  userFormGroup: FormGroup;
  allHobbies:Hobby[] = [];
  allPreferences: Preference[] = [];
  dropdownList=[];


  constructor(
    private userService:UserService,
    private dialog:MatDialog,
    private fileService: FileService,
    private formBuilder:FormBuilder,
    private hobbyService:HobbyService,
    private preferenceService:PreferenceService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
  ) { }

  ngOnInit(): void {
    this.userService.userLoggedIn.subscribe((response) => {
      this.user = response;
      this.hobbyService.getHobbies().subscribe((response) => {
        this.allHobbies = response;
        this.preferenceService.getPreferences().subscribe((response) => {
          this.allPreferences = response;
        })
      })
      this.refreshFormGroup();
    })
  }

  refreshFormGroup() {
    this.userFormGroup = this.formBuilder.group({
      firstname:[`${this.user ? this.user.firstName : ''}`, [Validators.required]],
      lastname: [`${this.user? this.user.lastName: ''}`, Validators.required],
      username: [`${this.user? this.user.username: ''}`, Validators.required],
      age: [`${this.user? this.user.age: ''}` , Validators.required],
      email: [`${this.user? this.user.email : ''}`, Validators.required],
      hobbies: [`${this.user? this.user.hobbies : []}`, Validators.required],
      preferences: [`${this.user ? this.user.preferences : []}`, Validators.required],
    });
  }

  onSubmit() {
    let user = new User();
    user.firstName = this.userFormGroup.controls['firstname'].value;
    user.lastName = this.userFormGroup.controls['lastname'].value;
    user.username = this.userFormGroup.controls['username'].value;
    user.age = Number(this.userFormGroup.controls['age'].value);
    user.email= this.userFormGroup.controls['email'].value;

    let userHobbies: Hobby[] = [];
    let userPreferences: Preference[] = [];

    let hobbyId = this.userFormGroup.controls['hobbies'].value;

    for(let h of this.allHobbies) {
      for(let id of hobbyId) {
        if(h.id == id) {
          userHobbies.push(h);
        }
      }
    }

    let preferenceId = this.userFormGroup.controls['preferences'].value;

    for(let p of this.allPreferences) {
      for(let id of preferenceId) {
        if(p.id == id) {
          userPreferences.push(p);
        }
      }
    }

    user.hobbies = userHobbies;
    user.preferences = userPreferences;

    this.userService.updateUser(this.user.id ,user).subscribe((response)=> {
      localStorage.setItem('user', JSON.stringify(response['user']));
      if(response['token'] != null) {
        localStorage.clear();
        localStorage.setItem('user', JSON.stringify(response['user']));
        localStorage.setItem('token', response['token']);
        this.userService.userLoggedIn.next(response['user']);
      }

      this.userService.userLoggedIn.next(this.user);
      this.userService.userLoggedIn.subscribe((response)=> {
        this.user = response;
        console.log(response);
        this.refreshFormGroup();
      })
    })
  }

  upload(): void {
    this.errorMsg = '';

    if (this.selectedFiles) {
      const file: File | null = this.selectedFiles.item(0);

      if (file) {
        this.currentFile = file;

        this.fileService.upload(this.user.id, this.currentFile).subscribe(
          (event: any) => {
            console.log('After image upload: ' + this.user.photo);
            this.userService.userLoggedIn.next(this.user);

            if (event.type === HttpEventType.UploadProgress) {
              console.log(Math.round((100 * event.loaded) / event.total));
            } else if (event instanceof HttpResponse) {
              this.message = event.body.responseMessage;
            }
            this.userService.userLoggedIn.next(this.user);
          },
          (err: any) => {
            console.log(err);

            if (err.error && err.error.responseMessage) {
              this.errorMsg = err.error.responseMessage;
            } else {
              this.errorMsg = 'Error occurred while uploading a file!';
            }
            this.currentFile = undefined;
          }
        );
        this.userService.userLoggedIn.next(this.user);
      }
      this.userService.userLoggedIn.next(this.user);
      this.selectedFiles = undefined;
    }
  }

  selectFile(event: any): void {
    this.selectedFiles = event.target.files;
  }

  // resetPassword() {
  //   openEditPasswordDialog(this.dialog, this.user).subscribe();
  // }

}
