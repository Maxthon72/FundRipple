import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { LocalStorage } from '@ngx-pwa/local-storage';
import { FullProject, Project } from 'src/app/interfaces/Project/Project';
import { ProjectBenefit } from 'src/app/interfaces/Project/ProjectBenefit';
import { ProjectSubGoal } from 'src/app/interfaces/Project/ProjectSubGoal';
import { User } from 'src/app/interfaces/User/fullUser';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { ProjectService } from 'src/app/services/project.service';
import { UserService } from 'src/app/services/user.service';
import { PopupComponent } from '../popup/popup.component';
import { MatDialog } from '@angular/material/dialog';
import { PostUnderProjectRead, PostUnderProjectWrite } from 'src/app/interfaces/Project/PostUnderProject';
import { ProjectSLE } from 'src/app/interfaces/Project/ProjectSLE';
import { Reason } from 'src/app/interfaces/reason';

@Component({
  selector: 'app-specific-project',
  templateUrl: './specific-project.component.html',
  styleUrls: ['./specific-project.component.css']
})
export class SpecificProjectComponent {
  token:string|null = null
  logedIn = false
  user:User|null=null;
  role:string=""
  project:FullProject|null=null
  benefits:ProjectBenefit[]=[]
  subGoals:ProjectSubGoal[]=[]
  projectName=""
  posts:PostUnderProjectRead[]=[]
  loading: boolean = true;
  constructor(private router: Router,private authenticationService:AuthenticationService,private localStorage:LocalStorage,
    private userService:UserService,private projectService:ProjectService,private route: ActivatedRoute,private dialog:MatDialog){}
  ngOnInit(): void {

    this.route.params.subscribe(params => {
      console.log('Route parameter:', params['projectName']);
      this.projectName=params['projectName']
      this.projectService.getProjectByProjectName(this.projectName).subscribe(
        (fullProjectResp:FullProject)=>{
          this.project=fullProjectResp
          this.loading=false
            this.projectService.getBenefitsForProject(this.projectName).subscribe(
              (benefitResp:ProjectBenefit[])=>{
                this.benefits=benefitResp
                console.log(this.benefits)
              }
            )
            this.projectService.getSubGoalsForProject(this.projectName).subscribe(
              (subGoalResp:ProjectSubGoal[])=>{
                this.subGoals=subGoalResp
                console.log(this.subGoals)
              }
            )
            this.projectService.getPostsUnderProject(this.project.projectName).subscribe(
              (postsResp:PostUnderProjectRead[])=>{
                this.posts=postsResp.reverse();
              }
            )
        }
      )
    });
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


  }
  toUserProfile(userName:string){
    this.router.navigate(['/user', userName]);
  }
  verifyOk(){
    this.projectService.setProjectStatusOk(this.project!.projectName).subscribe(
      (resp:FullProject)=>{
        this.project=resp
        this.router.navigate(['/project', this.project!.projectName]);
      }
    )
  }
  verifyBad(){
    const dialogRef = this.dialog.open(PopupComponent, {
      width: '500px',
      data: { myParam: 'VB' }
    });

    dialogRef.afterClosed().subscribe(result => {
      let reasonBad:Reason ={
        reason:result,
        projectName:this.project!.projectName
      }
      this.projectService.setProjectStatusBad(reasonBad).subscribe(
        (resp:FullProject)=>{
          this.project=resp
          this.router.navigate(['/project', this.project!.projectName]);
        }
      )
    });
  }
  openDialog(): void {
    const dialogRef = this.dialog.open(PopupComponent, {
      width: '500px',
      data: { myParam: 'P' }
    });
  
    dialogRef.afterClosed().subscribe(result => {
      let post:PostUnderProjectWrite=result
      this.projectService.addPostUnderProject(this.projectName,post).subscribe(
        (postResp:PostUnderProjectRead)=>{
          console.log(postResp)
        }
      )
    });
  }

  getUserName():string{
    if(this.user?.userName!==null){
      return this.user?.userName!
    }
    return ''
  }
  get percentageCollected() {
    return (this.project!.moneyCollected / this.project!.goal) * 100;
  }
  navigateToHome(){
    console.log(this.project)
    this.router.navigate(['home']);
  }
  support(){
    if(this.logedIn){

    }
    else{
      this.router.navigate(['login']);
    }
  }
  suspect(){
    if(this.logedIn){
      this.projectService.suspect(this.project!.projectName,this.user!.userName).subscribe(
        (res:boolean)=>{
          if(res){
            this.project!.suspicions+=1;
          }
        }
      )
    }
    else{
      this.router.navigate(['login']);
    }
  }

  public logOut(){
    localStorage.clear()
    this.logedIn = false
    this.user = null
    this.navigateToHome()
  }
}
