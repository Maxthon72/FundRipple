import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LocalStorage } from '@ngx-pwa/local-storage';
import { environment } from '../environments/environment';
import { Observable, of } from 'rxjs';
import { User } from '../interfaces/User/fullUser';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http:HttpClient,private localStorage:LocalStorage) { }

  public getUser(): Observable<User|null> {
    // Define the HTTP headers with the JWT token
    const storedToken = localStorage.getItem('token');
  
    if (storedToken) {
      const headers = new HttpHeaders({
        Authorization: `Bearer ${storedToken}`,
      });
  
      return this.http.get<User>(`${environment.apiBaseUrl}/user`, { headers });
    } else {
      // If there's no token, return an observable that emits null
      return of(null);
    }
  }

  public getUserRole(userName:string):Observable<string>{
    const storedToken = localStorage.getItem('token');
  
    const headers = new HttpHeaders({
        Authorization: `Bearer ${storedToken}`,
      });
      return this.http.get(`${environment.apiBaseUrl}/user/role/${userName}`, { headers, responseType: 'text'  });
  }

  public getUserByName(userName:string):Observable<User>{
    const storedToken = localStorage.getItem('token');
  
    const headers = new HttpHeaders({
        Authorization: `Bearer ${storedToken}`,
      });
      return this.http.get<User>(`${environment.apiBaseUrl}/user/${userName}`, { headers});
  }
  public updateUser(user:User):Observable<User>{
    const storedToken = localStorage.getItem('token');
  
    const headers = new HttpHeaders({
        Authorization: `Bearer ${storedToken}`,
      });
      return this.http.put<User>(`${environment.apiBaseUrl}/user`,user, { headers});
  }
}
