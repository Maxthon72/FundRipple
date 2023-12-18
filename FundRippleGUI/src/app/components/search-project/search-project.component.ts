import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LocalStorage } from '@ngx-pwa/local-storage';
import { ProjectSLE } from 'src/app/interfaces/Project/ProjectSLE';
import { User } from 'src/app/interfaces/User/fullUser';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { ProjectService } from 'src/app/services/project.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-search-project',
  templateUrl: './search-project.component.html',
  styleUrls: ['./search-project.component.css']
})
export class SearchProjectComponent {
  token:string|null = null
  logedIn = false
  user:User|null=null;
  role:string=""
  projects:ProjectSLE[]=[]
  constructor(private router: Router,private authenticationService:AuthenticationService,private localStorage:LocalStorage,
    private userService:UserService,private projectService:ProjectService){}
  ngOnInit(): void {
    this.projectService.getAllProjectSLE().subscribe(
      (projectsResponse:ProjectSLE[])=>{
        this.projects=projectsResponse;
      }
    )
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
                    this.role=res;
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
    this.navigateToHome()
  }
}
