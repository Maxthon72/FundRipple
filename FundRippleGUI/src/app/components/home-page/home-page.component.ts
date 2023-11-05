import { Token } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { LocalStorage } from '@ngx-pwa/local-storage';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit{
  token:string|null = null
  constructor(private authenticationService:AuthenticationService,private localStorage:LocalStorage){}
  ngOnInit(): void {
    const storedToken = localStorage.getItem('token');
    
    if (storedToken) {
      this.token = storedToken;
      // Use the retrieved token with the authentication service
      this.authenticationService.testUser(this.token).subscribe(
        (res: boolean) => {
          console.log(res);
        }
      );
    } else {
      console.log('Token not found in localStorage');
    }
  }
}
