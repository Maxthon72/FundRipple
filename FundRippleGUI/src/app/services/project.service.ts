import { Project } from './../interfaces/Project/Project';
import { Injectable } from '@angular/core';
import { Observable , throwError} from 'rxjs';
import { Tag } from '../interfaces/Project/ProjectTags';
import { environment } from '../environments/environment';
import { HttpClient , HttpHeaders, HttpErrorResponse} from '@angular/common/http';
import { ProjectDescription } from '../interfaces/Project/ProjectDescription';
import { catchError } from 'rxjs/operators';
import { ProjectBenefit } from '../interfaces/Project/ProjectBenefit';
import { ProjectSubGoal } from '../interfaces/Project/ProjectSubGoal';

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
}
