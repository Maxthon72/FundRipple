import { Project } from './../interfaces/Project/Project';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Tag } from '../interfaces/Project/ProjectTags';
import { environment } from '../environments/environment';
import { HttpClient , HttpHeaders} from '@angular/common/http';
import { ProjectDescription } from '../interfaces/Project/ProjectDescription';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  constructor(private http:HttpClient) { }

  public getAllTags(): Observable<Tag[]> {
      return this.http.get<Tag[]>(`${environment.apiBaseUrl}/tag`);
  }

  public addProject(project:Project):Observable<Project>{
    const storedToken = localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${storedToken}`,
    });
      return this.http.post<Project>(`${environment.apiBaseUrl}/project`,project,{headers});
  }

  public addDescriptionsToProject(descriptions:ProjectDescription[],projectName:string):Observable<Project>{
    const storedToken = localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${storedToken}`,
    });
      return this.http.post<Project>(`${environment.apiBaseUrl}/project/description/${projectName}`,descriptions,{headers});
  }
}
