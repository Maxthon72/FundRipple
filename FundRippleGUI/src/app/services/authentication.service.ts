import { Injectable } from '@angular/core';
import { environment } from '../environments/environment';
import { User } from '../interfaces/user';
import {HttpClient} from "@angular/common/http";
import { Observable } from 'rxjs';
import { Token } from '../interfaces/token';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private http:HttpClient) { }

  public registerNormalUser(user:User):Observable<Token>{
    return this.http.post<Token>(`${environment.apiBaseUrl}/auth/register`,user);
  }

}
