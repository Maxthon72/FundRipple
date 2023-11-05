
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LocalStorage } from '@ngx-pwa/local-storage';
import { User } from 'src/app/interfaces/User/fullUser';
import { Token } from 'src/app/interfaces/token';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.css']
})
export class RegisterPageComponent implements OnInit{
  user!: User;
  token:Token|null=null

  constructor(private auth:AuthenticationService,private router:Router,private localStorage: LocalStorage){}

  ngOnInit(): void {
    this.user = {
      userName: '',
      firstName: null,
      lastName: null,
      email: '',
      password: ''
    };
  }

  onSubmit() {
    console.log(this.user)
    this.auth.registerNormalUser(this.user).subscribe(
      (response:Token)=>{
        this.token = response
        this.localStorage.setItem('token',this.token)
        this.router.navigate(['/']);
      }
    )
    console.log('Registration submitted:', this.user)
  }
}
