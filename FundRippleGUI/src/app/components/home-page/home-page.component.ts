import { Token } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LocalStorage } from '@ngx-pwa/local-storage';
import { User } from 'src/app/interfaces/User/fullUser';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit{
  token:string|null = null
  logedIn = false
  user:User|null=null;
  constructor(private router: Router,private authenticationService:AuthenticationService,private localStorage:LocalStorage,
    private userService:UserService){}
  ngOnInit(): void {
    const storedToken = localStorage.getItem('token');
    
    if (storedToken) {
      this.token = storedToken;
      // Use the retrieved token with the authentication service
      this.authenticationService.testUser(this.token).subscribe(
        (res: boolean) => {
          this.logedIn=res
          this.userService.getUser().subscribe(
            (user: User | null) => {
              if (user !== null) {
                this.user = user;
              } else {
              }
            },
            (error) => {
              console.error('Error getting user info:', error);
            }
          );        
        }
      );
    } else {
      console.log('Token not found in localStorage');
    }
  }

  getUserName():string{
    if(this.user?.userName!==null){
      return this.user?.userName!
    }
    return ''
  }

  navigateToHome(){
    this.router.navigate(['home']);
  }

  public logOut(){
    localStorage.clear()
    this.logedIn = false
    this.user = null
  }
}
