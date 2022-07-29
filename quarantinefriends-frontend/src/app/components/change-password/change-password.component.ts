import { MatSnackBar } from '@angular/material/snack-bar';
import { FormBuilder, Validators } from '@angular/forms';
import { User } from './../../model/model';
import { UserService } from './../../services/user.service';
import { Component, Inject, OnInit } from '@angular/core';
import {
  MatDialog,
  MatDialogConfig,
  MatDialogRef,
  MAT_DIALOG_DATA,
} from '@angular/material/dialog';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css'],
})
export class ChangePasswordComponent implements OnInit {
  password: string;
  passwordConfirmation: string;
  form = this.fb.group({
    password: ['', [Validators.required, Validators.maxLength(60)]],
    passwordConfirmation: ['', Validators.required],
  });
  constructor(
    private userService: UserService,
    private fb: FormBuilder,
    @Inject(MAT_DIALOG_DATA) private user: User,
    private dialogRef: MatDialogRef<ChangePasswordComponent>,
    private _snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {}

  editPassword() {
    let user: User = JSON.parse(localStorage.getItem('user'));
    this.userService
      .resetPassword(user.id, this.form.value.password)
      .subscribe((response) => {
        console.log('Password reseted');
        this.dialogRef.close();
      });
  }
  close() {
    this.dialogRef.close();
  }

  sameFields(): boolean {
    if (
      this.form.controls['password'] !==
      this.form.controls['passwordConfirmation']
    ) {
      return false;
    }
    return true;
  }
}

export function openEditPasswordDialog(dialog: MatDialog, user: User) {
  const config = new MatDialogConfig();
  config.disableClose = true;
  config.autoFocus = true;
  config.data = {
    ...user,
  };
  const dialogRef = dialog.open(ChangePasswordComponent, config);
  return dialogRef.afterClosed();
}
