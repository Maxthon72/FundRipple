import { Injectable } from '@angular/core';
import { environment } from '../environments/environment';
import { User } from '../interfaces/User/fullUser';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import { Observable } from 'rxjs';
import { Token } from '../interfaces/token';
import { PartUser } from '../interfaces/User/partUser';
import { LocalStorage } from '@ngx-pwa/local-storage';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private http:HttpClient,private localStorage:LocalStorage) { }

  public registerNormalUser(user:User):Observable<Token>{
    return this.http.post<Token>(`${environment.apiBaseUrl}/auth/register`,user);
  }

  public registerAdminUser(user:User):Observable<Token>{
    const storedToken = localStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${storedToken}`
    });
    return this.http.post<Token>(`${environment.apiBaseUrl}/user/register/admin`,user,{headers});
  }

  public authenticateNormalUser(user:PartUser):Observable<Token>{
    return this.http.post<Token>(`${environment.apiBaseUrl}/auth/authenticate`,user);
  }

  public testUser(token:string|null): Observable<boolean> {
    // Define the HTTP headers with the JWT token
    
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });
    // Make the GET request with the custom headers
    return this.http.get<boolean>(`${environment.apiBaseUrl}/test`, { headers });
  }

  public testUserByHeaderInStorage(): Observable<boolean> {
    const storedToken = localStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${storedToken}`
    });
    // Make the GET request with the custom headers
    return this.http.get<boolean>(`${environment.apiBaseUrl}/test`, { headers });
  }
  public testOrigin():void{
    this.http.get<void>(`${environment.apiBaseUrl}/test/print-origin`)
  }

}
