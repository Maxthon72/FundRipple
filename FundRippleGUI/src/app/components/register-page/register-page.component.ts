import { Component } from '@angular/core';
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

  constructor(private auth:AuthenticationService){}

  onSubmit() {
    this.auth.registerNormalUser(this.user);
    console.log('Registration submitted:', this.user);
  }
}
