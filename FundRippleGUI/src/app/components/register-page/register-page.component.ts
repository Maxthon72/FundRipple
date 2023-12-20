
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

  hidePassword = true;
  userNameFlag=false
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
    if (this.user.userName.includes(' ')) {
      this.userNameFlag = true;
    } else {
      this.userNameFlag = false;
    }
    console.log(this.userNameFlag)
    if(!this.userNameFlag){
      this.auth.registerNormalUser(this.user).subscribe(
        (response:Token)=>{
          this.token = response
          this.localStorage.clear()
          this.localStorage.setItem('token',response)
          this.userNameFlag=false;
          this.router.navigate(['/']);
        }
      )
    }
    console.log('Registration submitted:', this.user)
  }
  navigateToHome(){
    this.router.navigate(['home']);
  }
  togglePasswordVisibility() {
    this.hidePassword = !this.hidePassword;
  }
}
