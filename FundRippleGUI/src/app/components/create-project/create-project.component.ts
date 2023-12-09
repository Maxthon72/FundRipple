import { Component, ElementRef, Inject, OnInit, ViewChild, inject } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable, map, startWith } from 'rxjs';
import { Project } from 'src/app/interfaces/Project/Project';
import { Tag } from 'src/app/interfaces/Project/ProjectTags';
import { User } from 'src/app/interfaces/User/fullUser';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { ProjectService } from 'src/app/services/project.service';
import { UserService } from 'src/app/services/user.service';
import {COMMA, ENTER} from '@angular/cdk/keycodes';
import { LiveAnnouncer } from '@angular/cdk/a11y';
import { MatChipInputEvent } from '@angular/material/chips';
import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';


@Component({
  selector: 'app-create-project',
  templateUrl: './create-project.component.html',
  styleUrls: ['./create-project.component.css']
})
export class CreateProjectComponent implements OnInit{
  logedIn = false
  user:User|null=null;
  currentStep = 1;
  project:Project;
  token:string|null = null
  allTags:Tag[] = []
  selectedTags:Tag[]=[]
  maxTags = 4;
  totalSelected=0;

  announcer = Inject(LiveAnnouncer);
  

  constructor (private router: Router,private authenticationService:AuthenticationService,private userService:UserService,
      private projectService:ProjectService){
    this.project={
      projectName:"",
      goal:0,
      summery:"",
      planedDateOfClosing:""
    }
  }

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
                this.projectService.getAllTags().subscribe(
                  (tags:Tag[])=>{
                    this.allTags=tags;
                  }
                )
              } else {
              }
            },
            (error) => {
              console.error('Error getting user info:', error);
              localStorage.clear()
              this.router.navigate(['home']);
            }
          );        
        }
      );
    } else {
      console.log('Token not found in localStorage');
      localStorage.clear()
      this.router.navigate(['home']);
    }
  }

  addOrRemove(selectedTag:Tag):void{
    const indexToRemove = this.selectedTags.findIndex(tag => tag.tagName === selectedTag.tagName);
    
    if (indexToRemove !== -1) {
      this.selectedTags.splice(indexToRemove,1);
      this.totalSelected-=1
      return
    }
    else if(this.totalSelected<this.maxTags){
      this.totalSelected+=1
      this.selectedTags.push(selectedTag);
      return
    }
    this.announcer.announce('Max 4 tags can be choosen');
  }

  isSelected(check:Tag):boolean{
    const index = this.selectedTags.findIndex(tag => tag.tagName === check.tagName);
    if (index !== -1) {
      return true;
    }
    return false
  }

  getUserName():string{
    if(this.user?.userName!==null){
      return this.user?.userName!
    }
    return ''
  }

  nextStep() {
    this.currentStep++;
  }

  goBack() {
    this.currentStep--;
  }

  finish() {
    // Add logic to submit the form data
  }

  formatGoal() {
    // Format the number to display two decimal places
    if (this.project.goal !== null && this.project.goal !== undefined) {
      this.project.goal = Number(Number(this.project.goal).toFixed(2));
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
