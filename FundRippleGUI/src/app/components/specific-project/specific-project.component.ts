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
  loading: boolean = true;
  constructor(private router: Router,private authenticationService:AuthenticationService,private localStorage:LocalStorage,
    private userService:UserService,private projectService:ProjectService,private route: ActivatedRoute){}
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
  get percentageCollected() {
    return (this.project!.moneyCollected / this.project!.goal) * 100;
  }
  navigateToHome(){
    console.log(this.project)
    this.router.navigate(['home']);
  }

  public logOut(){
    localStorage.clear()
    this.logedIn = false
    this.user = null
    this.navigateToHome()
  }
}
