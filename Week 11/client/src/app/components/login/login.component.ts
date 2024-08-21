import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../services/user.service';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { User } from '../../models/user.model';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    RouterModule,
    HttpClientModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent implements OnInit{
  username: string = '';
  password: string = '';
  loggedUser?: User;
  message: string = '';
  isSuccess: boolean = false;

  constructor (private userService: UserService){}

  ngOnInit(): void {
    this.login();
  }

  login(): void{
    this.userService.authenticate(this.username, this.password).subscribe({
      next: (data) => {
        if (this.username && this.password){
          if (data){
            this.loggedUser = data;
            this.message = "Login successful!";
            this.isSuccess = true;
          }
          else {
            this.message = "Login failed. Username and password didn't match.";
            this.isSuccess = false;
          }
        }
      }
    });
  }

}
