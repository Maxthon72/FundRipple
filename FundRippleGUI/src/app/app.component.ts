import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from './services/authentication.service';
import { LocalStorage } from '@ngx-pwa/local-storage';
import { UserService } from './services/user.service';
import { User } from './interfaces/User/fullUser';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent{
  constructor(){}

}
