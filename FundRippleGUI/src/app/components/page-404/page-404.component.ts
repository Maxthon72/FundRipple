import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-page-404',
  templateUrl: './page-404.component.html',
  styleUrls: ['./page-404.component.css']
})
export class Page404Component {
  constructor(private router:Router){}
  logedIn = false

  navigateToHome(){
    this.router.navigate(['home']);
  }
}
