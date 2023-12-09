import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Tag } from '../interfaces/Project/ProjectTags';
import { environment } from '../environments/environment';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  constructor(private http:HttpClient) { }

  public getAllTags(): Observable<Tag[]> {
      return this.http.get<Tag[]>(`${environment.apiBaseUrl}/tag`);
  }
}
