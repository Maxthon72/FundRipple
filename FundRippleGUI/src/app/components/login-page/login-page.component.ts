import { Token } from 'src/app/interfaces/token';
import { Component, OnInit } from '@angular/core';
import { PartUser } from 'src/app/interfaces/User/partUser';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { LocalStorage } from '@ngx-pwa/local-storage';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit{
  user!: PartUser;
  token:Token|null=null
  constructor(private authenticationService:AuthenticationService,private localStorage:LocalStorage,private router:Router){}
  ngOnInit(): void {
    this.user = {userName:'',password:''}
  }
  onSubmit(){
    console.log(this.user)
    this.authenticationService.authenticateNormalUser(this.user).subscribe(
      (response:Token)=>{
        this.token=response
        console.log(this.token.token)
        this.localStorage.clear()
        localStorage.setItem('token', this.token.token);

        // Log all items in localStorage
        for (let i = 0; i < localStorage.length; i++) {
          const key = localStorage.key(i);
          const value = localStorage.getItem(key!);
          console.log(`Key: ${key}, Value: ${value}`);
        }

        // Navigate to the desired route
        this.router.navigate(['/']);
      }
    )
  }
}
