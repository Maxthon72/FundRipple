import { Token } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LocalStorage } from '@ngx-pwa/local-storage';
import { ProjectSLE } from 'src/app/interfaces/Project/ProjectSLE';
import { Tag } from 'src/app/interfaces/Project/ProjectTags';
import { User } from 'src/app/interfaces/User/fullUser';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { ProjectService } from 'src/app/services/project.service';
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
  role:string=""
  projects:ProjectSLE[]=[]
  projectsClosingDateClosest:ProjectSLE[]=[]
  projectsNewest:ProjectSLE[]=[]
  projectsClosingGoal:ProjectSLE[]=[]
  currentIndex1 = 0
  currentIndex2 = 0
  currentIndex3 = 0
  loading = true;
  constructor(private router: Router,private authenticationService:AuthenticationService,private localStorage:LocalStorage,
    private userService:UserService,private projectService:ProjectService){}
  ngOnInit(): void {
    const storedToken = localStorage.getItem('token');
    if (storedToken) {
      this.token = storedToken;
      this.authenticationService.testOrigin();
      this.projectService.getOpenProjectSLE().subscribe(
        (resp:ProjectSLE[])=>{
          this.projects=resp
          this.projectsClosingDateClosest=this.getFutureItemsClosestToToday()
          this.projectsClosingGoal=this.getItemsClosestToGoal()
          this.projectsNewest=this.getNewProjects()
          //this.loading=false
        }
      )
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
                    console.log(this.role)
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
    this.router.navigate(['/profile',userName]);
  }
  getUserName():string{
    if(this.user?.userName!==null){
      return this.user?.userName!
    }
    return ''
  }

  getFutureItemsClosestToToday(): ProjectSLE[] {
    const today = new Date();

    return this.projects
      .filter(item=>(item.moneyCollected/item.goal)<1)
      .filter(item => new Date(item.planedDateOfClosing) > today) // Keep only future dates
      .sort((a, b) => new Date(a.planedDateOfClosing).getTime() - new Date(b.planedDateOfClosing).getTime()) // Sort by closest future date
      .slice(0, 5); // Get the first 5 items
  }

  moveToProject(projectName:string){
    this.router.navigate(['/project', projectName]);
  }
  getItemsClosestToGoal():ProjectSLE[]{
    return this.projects
      .filter(item=>(item.moneyCollected/item.goal)<1)
      .sort((a,b)=>a.moneyCollected/a.goal - b.moneyCollected/b.goal)
      .slice(0,5)
  }

  getNewProjects():ProjectSLE[]{
    return this.projects
      .sort((a,b)=>new Date(a.dateCreated).getTime() - new Date(b.dateCreated).getTime())
      .slice(0,5)
  }
  get currentItem1() {
    return this.projectsNewest[this.currentIndex1];
  }
  get currentItem2() {
    return this.projectsClosingGoal[this.currentIndex2];
  }
  get currentItem3() {
    return this.projectsClosingDateClosest[this.currentIndex3];
  }
  next(carusele:number) {
    if(carusele==1){
      if (this.currentIndex1 < this.projectsNewest.length - 1) {
        this.currentIndex1++;
      } else {
        this.currentIndex1 = 0;
      }
    }
    else if(carusele==2){
      if (this.currentIndex2 < this.projectsClosingGoal.length - 1) {
        this.currentIndex2++;
      } else {
        this.currentIndex2 = 0;
      }
    }
    else if(carusele==3){
      if (this.currentIndex3 < this.projectsClosingDateClosest.length - 1) {
        this.currentIndex3++;
      } else {
        this.currentIndex3 = 0;
      }
    }

  }

  previous(carusele:number) {
    if(carusele==1){
      if (this.currentIndex1 > 0) {
        this.currentIndex1--;
      } else {
        this.currentIndex1 = this.projectsNewest.length - 1;
      }
    }
    else if(carusele==2){
      if (this.currentIndex2 > 0) {
        this.currentIndex2--;
      } else {
        this.currentIndex2 = this.projectsClosingGoal.length - 1;
      }
    }
    else if(carusele==3){
      if (this.currentIndex3 > 0) {
        this.currentIndex3--;
      } else {
        this.currentIndex3 = this.projectsClosingDateClosest.length - 1;
      }
    }
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
