import { Injectable } from '@angular/core';
import { environment } from '../environments/environment';
import { User } from '../interfaces/User/fullUser';
import {HttpClient} from "@angular/common/http";
import { Observable } from 'rxjs';
import { Token } from '../interfaces/token';
import { PartUser } from '../interfaces/User/partUser';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private http:HttpClient) { }

  public registerNormalUser(user:User):Observable<Token>{
    return this.http.post<Token>(`${environment.apiBaseUrl}/auth/register`,user);
  }

  public authenticateNormalUser(user:PartUser):Observable<Token>{
    return this.http.post<Token>(`${environment.apiBaseUrl}/auth/authenticate`,user);
  }

  public testUser(): Observable<any> {
    // Define the HTTP headers with the JWT token
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${this.jwtToken}`, // Include the JWT token in the 'Authorization' header
    });

    // Make the GET request with the custom headers
    return this.http.get(`${this.apiUrl}/endpoint`, { headers });
  }

}
