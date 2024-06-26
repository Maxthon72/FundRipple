import { FullProject, Project,OnlyProjectName } from './../interfaces/Project/Project';
import { Injectable } from '@angular/core';
import { Observable , throwError} from 'rxjs';
import { Tag } from '../interfaces/Project/ProjectTags';
import { environment } from '../environments/environment';
import { HttpClient , HttpHeaders, HttpErrorResponse} from '@angular/common/http';
import { ProjectDescription } from '../interfaces/Project/ProjectDescription';
import { catchError } from 'rxjs/operators';
import { ProjectBenefit } from '../interfaces/Project/ProjectBenefit';
import { ProjectSubGoal } from '../interfaces/Project/ProjectSubGoal';
import { ProjectSLE } from '../interfaces/Project/ProjectSLE';
import { PostUnderProjectRead, PostUnderProjectWrite } from '../interfaces/Project/PostUnderProject';
import { Reason } from '../interfaces/reason';
import { ProjectBenefitForUser } from '../interfaces/Project/ProjectBenefitForUser';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  constructor(private http:HttpClient) { }

  public getAllTags(): Observable<Tag[]> {
      return this.http.get<Tag[]>(`${environment.apiBaseUrl}/tag`);
  }

  public addProject(project: Project): Observable<Project> {
    const storedToken = localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${storedToken}`,
    });

    return this.http.post<Project>(`${environment.apiBaseUrl}/project`, project, { headers })
      .pipe(
        catchError(this.handleError)
      );
  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'An unknown error occurred';
    if (error.error instanceof ErrorEvent) {
      // Client-side error
      errorMessage = `Error: ${error.error.message}`;
    } else {
      // Server-side error
      errorMessage = error.error || `Server returned code: ${error.status}`;
    }

    return throwError(errorMessage);
  }

  public addDescriptionsToProject(descriptions:ProjectDescription[],projectName:string):Observable<Project>{
    const storedToken = localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${storedToken}`,
    });
      return this.http.post<Project>(`${environment.apiBaseUrl}/project/description/${projectName}`,descriptions,{headers}).pipe(
        catchError(this.handleError)
      );
  }

  public addTagsToProject(tags:Tag[],projectName:string):Observable<Project>{
    const storedToken = localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${storedToken}`,
    });
      return this.http.post<Project>(`${environment.apiBaseUrl}/project/tag/${projectName}`,tags,{headers}).pipe(
        catchError(this.handleError)
      );
  }

  public addBenefitsToProject(benefits:ProjectBenefit[],projectName:string):Observable<Project>{
    const storedToken = localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${storedToken}`,
    });
      return this.http.post<Project>(`${environment.apiBaseUrl}/benefit/forProject/${projectName}`,benefits,{headers}).pipe(
        catchError(this.handleError)
      );
  }

  public addSubGoalsToProject(subGoals:ProjectSubGoal[],projectName:string):Observable<Project>{
    const storedToken = localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${storedToken}`,
    });
      return this.http.post<Project>(`${environment.apiBaseUrl}/subGoal/forProject/${projectName}`,subGoals,{headers}).pipe(
        catchError(this.handleError)
      );
  }

  public getOpenAndClosedProjectSLE():Observable<ProjectSLE[]>{
    return this.http.get<ProjectSLE[]>(`${environment.apiBaseUrl}/public/projects`);
  }

  public getOpenAndClosedProjectSLEForUserByHeader():Observable<ProjectSLE[]>{
    const storedToken = localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${storedToken}`,
    });
    return this.http.get<ProjectSLE[]>(`${environment.apiBaseUrl}/project/openAndClosed/forUser`, { headers });
  }

  public getOpenAndClosedProjectSLEForUserByUserName(userName:string):Observable<ProjectSLE[]>{
    const storedToken = localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${storedToken}`,
    });
    return this.http.get<ProjectSLE[]>(`${environment.apiBaseUrl}/project/openAndClosed/forUser/${userName}`, { headers });
  }

  public getAllProjectSLEForUserByHeader():Observable<ProjectSLE[]>{
    const storedToken = localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${storedToken}`,
    });
    return this.http.get<ProjectSLE[]>(`${environment.apiBaseUrl}/project/allProjects/forUser`, { headers });
  }

  public getAllProjectSLESupportedNyUserByHeader():Observable<ProjectSLE[]>{
    const storedToken = localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${storedToken}`,
    });
    return this.http.get<ProjectSLE[]>(`${environment.apiBaseUrl}/project/allProjectsSuported/forUser`, { headers });
  }

  public getAllProjectSLEForUserByUserName(userName:string):Observable<ProjectSLE[]>{
    const storedToken = localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${storedToken}`,
    });
    return this.http.get<ProjectSLE[]>(`${environment.apiBaseUrl}/project/allProjects/forUser/${userName}`, { headers });
  }

  public getAllBenefitsForUserByUserName(userName:string):Observable<ProjectBenefitForUser[]>{
    const storedToken = localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${storedToken}`,
    });
    return this.http.get<ProjectBenefitForUser[]>(`${environment.apiBaseUrl}/benefit/forUser/${userName}`, { headers });
  }

  public getOpenProjectSLE():Observable<ProjectSLE[]>{
    return this.http.get<ProjectSLE[]>(`${environment.apiBaseUrl}/public/projects`);
  }

  public getSuspectProjectSLE():Observable<ProjectSLE[]>{
    const storedToken = localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${storedToken}`,
    });
    return this.http.get<ProjectSLE[]>(`${environment.apiBaseUrl}/project/suspects`,{headers});
  }

  public getProjectSLEToVerify():Observable<ProjectSLE[]>{
    const storedToken = localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${storedToken}`,
    });
    return this.http.get<ProjectSLE[]>(`${environment.apiBaseUrl}/project/toVerify`, { headers });
  }

  public setProjectStatusOk(projectName: string): Observable<FullProject> {
    const storedToken = localStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${storedToken}`
    });
  
    // The headers need to be passed as an options object
    const options = { headers: headers };
  
    // Now pass the options as the third argument in the put request
    return this.http.put<FullProject>(`${environment.apiBaseUrl}/project/chStatus/open/${projectName}`, {}, options);
  }

  public setProjectStatusBad(reson:Reason):Observable<FullProject>{
    const storedToken = localStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${storedToken}`
    });
  
    // The headers need to be passed as an options object
    const options = { headers: headers };
    console.log(reson)
    return this.http.put<FullProject>(`${environment.apiBaseUrl}/project/chStatus/earlyClose`, reson, options);
  }

  public getProjectByProjectName(projectName:string):Observable<FullProject>{
    return this.http.get<FullProject>(`${environment.apiBaseUrl}/public/project/${projectName}`);
  }

  public getSubGoalsForProject(projectName:string):Observable<ProjectSubGoal[]>{
    return this.http.get<ProjectSubGoal[]>(`${environment.apiBaseUrl}/subGoal/forProject/${projectName}`);
  }
  public getBenefitsForProject(projectName:string):Observable<ProjectBenefit[]>{
    return this.http.get<ProjectBenefit[]>(`${environment.apiBaseUrl}/benefit/forProject/${projectName}`);
  }
  public checkIfSuspect(projectNameSus:string,userName:string):Observable<boolean>{
    const storedToken = localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${storedToken}`,
    });
    return this.http.get<boolean>(`${environment.apiBaseUrl}/sus/${userName}/${projectNameSus}`, { headers }).pipe(
      catchError(this.handleError)
    );
  }


  public suspect(projectNameSus:string,userName:string):Observable<boolean>{

    const storedToken = localStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${storedToken}`
    });
  
    // The headers need to be passed as an options object
    const options = { headers: headers };
    // Make the GET request with the custom headers
    return this.http.put<boolean>(`${environment.apiBaseUrl}/sus/${userName}/${projectNameSus}`, {}, options).pipe(
      catchError(this.handleError)
    );
  }

  public getPostsUnderProject(projectName:string):Observable<PostUnderProjectRead[]>{
    // Make the GET request with the custom headers
    return this.http.get<PostUnderProjectRead[]>(`${environment.apiBaseUrl}/public/postUnderProject/${projectName}`).pipe(
      catchError(this.handleError)
    );
  }

  public addPostUnderProject(projectName:string,post:PostUnderProjectWrite):Observable<PostUnderProjectRead>{
    const storedToken = localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${storedToken}`,
    });
    // Make the GET request with the custom headers
    console.log(post)
    return this.http.post<PostUnderProjectRead>(`${environment.apiBaseUrl}/posts/project/${projectName}`,post, { headers }).pipe(
      catchError(this.handleError)
    );
  }
}
