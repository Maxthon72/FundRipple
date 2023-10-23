
import { Component } from '@angular/core';
import { Token } from 'src/app/interfaces/token';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.css']
})
export class RegisterPageComponent {
  user = {
    userName: '',
    firstName: null,
    lastName: null,
    email: '',
    password: ''
  };
  token:Token|null=null

  constructor(private auth:AuthenticationService){}

  onSubmit() {
    this.auth.registerNormalUser(this.user).subscribe(
      (response:Token)=>{
        this.token = response
        console.log(this.token)
      }
    )
    console.log('Registration submitted:', this.user)
  }
}
