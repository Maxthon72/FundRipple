import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/interfaces/User/fullUser';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {
  constructor(private router: Router,private route: ActivatedRoute,private authenticationService:AuthenticationService,private userService:UserService){}
  logedIn = false
  role:string=""
  userName:string=""
  user:User|null=null
  token:string=""
  toUserProfile(userName:string){
    this.router.navigate(['/user', userName]);
  }
  ngOnInit(): void {
    this.route.params.subscribe(param=>{
      this.userName=param['userName']
      const storedToken = localStorage.getItem('token');
      if (storedToken) {
        this.token = storedToken;
        this.authenticationService.testOrigin();
        // Use the retrieved token with the authentication service
        this.authenticationService.testUser(this.token).subscribe(
          (res: boolean) => {
            this.logedIn=res
            this.userService.getUser().subscribe(
              (user: User | null) => {
                if (user !== null) {
                  this.user = user;
                  this.userService.getUserRole(this.user.userName).subscribe(
                    (res:string)=>{
                      this.role=res
                    }
                  )
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
    })
  }
  public logOut(){
    localStorage.clear()
    this.logedIn = false
    this.user = null
    this.navigateToHome()
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
}
