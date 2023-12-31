import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { LocalStorage } from '@ngx-pwa/local-storage';
import { ProjectBenefit } from 'src/app/interfaces/Project/ProjectBenefit';
import { ProjectBenefitForUser } from 'src/app/interfaces/Project/ProjectBenefitForUser';
import { ProjectSLE } from 'src/app/interfaces/Project/ProjectSLE';
import { User } from 'src/app/interfaces/User/fullUser';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { ProjectService } from 'src/app/services/project.service';
import { UserService } from 'src/app/services/user.service';
import { PopupComponent } from '../popup/popup.component';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit{
  token:string|null = null
  logedIn = false
  user:User|null=null;
  role:string=""
  userName:string=""
  userProfile:User|null=null
  roleProfile:string=""
  loading:boolean=false
  userProjects:ProjectSLE[]=[]
  userBenefits:ProjectBenefitForUser[]=[]
  supportedProjects:ProjectSLE[]=[]
  userDataToMod:User|null=null
  constructor(private router: Router,private authenticationService:AuthenticationService,private localStorage:LocalStorage,
    private userService:UserService,private projectService:ProjectService,private route: ActivatedRoute,private dialog:MatDialog){}
  ngOnInit(): void {
    this.route.params.subscribe(params => {
      console.log('Route parameter:', params['userName']);
      this.userName=params['userName']
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
                this.userService.getUserRole(this.user.userName).subscribe(
                  (res:string)=>{
                    this.role=res;
                  }
                )
                this.userService.getUserByName(this.userName).subscribe(
                  (resp:User)=>{
                    this.userProfile=resp
                    this.userService.getUserRole(this.userProfile.userName).subscribe(
                      (res:string)=>{
                        this.roleProfile=res;
                      }
                    )
                    if(this.user!.userName==this.userProfile?.userName){
                      this.projectService.getAllProjectSLEForUserByHeader().subscribe(
                        (resp:ProjectSLE[])=>{
                          this.userProjects=resp
                          
                        }
                      )
                      this.projectService.getAllBenefitsForUserByUserName(this.userName).subscribe(
                        (resp:ProjectBenefitForUser[])=>{
                          this.userBenefits=resp
                        }
                      )
                    }else{
                      this.projectService.getOpenAndClosedProjectSLEForUserByUserName(this.userName).subscribe(
                        (resp:ProjectSLE[])=>{
                          this.userProjects=resp
                        }
                      )
                    }
                  }
                )
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
  modifayUser(){
    const dialogRef = this.dialog.open(PopupComponent, {
      width: '500px',
      data: { myParam: 'MU',user: {...this.userProfile} }
    });

    dialogRef.afterClosed().subscribe(result => {
      this.userDataToMod=result
      this.userService.updateUser(this.userDataToMod!).subscribe(
        (resp:User)=>{
          this.userProfile=resp
        }
      )
    });
  }
  onCardClickProject(projectName:string){
    this.router.navigate(['/project', projectName]);
  }
  toUserProfile(userName:string){
    this.router.navigate(['/profile',userName]);
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
