import { Component } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { MatSelectChange } from '@angular/material/select';
import { Router } from '@angular/router';
import { LocalStorage } from '@ngx-pwa/local-storage';
import { ProjectSLE } from 'src/app/interfaces/Project/ProjectSLE';
import { Tag } from 'src/app/interfaces/Project/ProjectTags';
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
  searchTerm:string=""
  projects:ProjectSLE[]=[]
  selectedFilter: string = 'all';
  filteredAndSearchedProjects:ProjectSLE[]=[]
  allTags:Tag[]=[]
  selectedTags:Tag[]=[]
  projectStatus:string[]=["active","completed","all"]
  projectElements:string[]=["Date of cloasing","Date started","Goal","Money collected","Number of supporters","Project name"]
  direction:string[]=["Ascending","Descending"]
  selectedProjectElement:string="Project name"
  selectedDirection:string="Descending"
  loading: boolean = true;

  readonly sortFunctions = {
    'Project name': (a:ProjectSLE, b:ProjectSLE) => a.projectName.localeCompare(b.projectName),
    'Date of closing': (a:ProjectSLE, b:ProjectSLE) => new Date(a.planedDateOfClosing).getTime() - new Date(b.planedDateOfClosing).getTime(),
    'Date started': (a:ProjectSLE, b:ProjectSLE) => new Date(a.dateCreated).getTime() - new Date(b.dateCreated).getTime(),
    'Goal': (a:ProjectSLE, b:ProjectSLE) => a.goal - b.goal,
    'Money collected': (a:ProjectSLE, b:ProjectSLE) => a.moneyCollected - b.moneyCollected,
    'Number of supporters': (a:ProjectSLE, b:ProjectSLE) => a.numberOfSupporters - b.numberOfSupporters
  };

  paginatedProjects:ProjectSLE[] = []; // Array for projects to display on current page
  totalLength = this.projects.length;
  pageSize = 8; // Default number of items per page
  pageSizeOptions = [4, 8, 12,16]; // Options for user to select items per page

  constructor(private router: Router,private authenticationService:AuthenticationService,private localStorage:LocalStorage,
    private userService:UserService,private projectService:ProjectService){}


  ngOnInit(): void {
    const currentUrl = this.router.url;
    if(currentUrl=="/list/suspicion"){

    }
    else if(currentUrl=="/list/verify"){
      this.projectService.getProjectSLEToVerify().subscribe(
        (projectsResponse:ProjectSLE[])=>{
          this.projects=projectsResponse;
          this.loading=false
          this.filteredAndSearchedProjects=projectsResponse;
          this.totalLength = this.filteredAndSearchedProjects.length;
          this.paginateProjects(0, this.pageSize);
        }
      )
    }
    else{
      this.projectService.getOpenAndClosedProjectSLE().subscribe(
        (projectsResponse:ProjectSLE[])=>{
          this.projects=projectsResponse;
          this.loading=false
          this.filteredAndSearchedProjects=projectsResponse;
          this.totalLength = this.filteredAndSearchedProjects.length;
          this.paginateProjects(0, this.pageSize);
        }
      )
    }
    this.projectService.getAllTags().subscribe(
      (tags: Tag[]) => {
        this.allTags = tags;
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

  
  onTagSelect(event: MatSelectChange) {
    if (event.value.length > 3) {
      // If more than 3 tags are selected, revert to the previous selection
      event.source.value = this.selectedTags;
      event.source.writeValue(this.selectedTags);
      // Optionally show a user-friendly message or toast
    } else {
      this.selectedTags = event.value;
    }
    this.applyFilters()
  }

  onPageChange(event: PageEvent) {
    this.paginateProjects(event.pageIndex, event.pageSize);
  }

  paginateProjects(pageIndex: number, pageSize: number) {
    const start = pageIndex * pageSize;
    this.paginatedProjects = this.filteredAndSearchedProjects.slice(start, start + pageSize);
  }

  applyFilters():void{
    this.filteredAndSearchedProjects = this.projects.filter(project=>{
      let matchedTags = 0
      project.tags.forEach(tag=>{
        
        this.selectedTags.forEach(selectedTag=>{
          if(selectedTag.tagName==tag.tagName){
            matchedTags+=1
          }
        })
      })
      if(matchedTags==this.selectedTags.length){
        if(project.status==this.selectedFilter || this.selectedFilter=='all'){
          if(project.projectName.includes(this.searchTerm)){
            return true
          }
        }
      }
      return false
    })
    const comparisonFunction = this.sortFunctions[this.selectedProjectElement as keyof typeof this.sortFunctions];
    if (this.selectedDirection === 'Descending') {
      this.projects.sort((a, b) => comparisonFunction(b, a));
    } else {
      this.projects.sort(comparisonFunction);
    }
    this.totalLength = this.filteredAndSearchedProjects.length;
    this.paginateProjects(0, this.pageSize);
  }
  onCardClick(projectName:string){
    this.router.navigate(['/project', projectName]);
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
