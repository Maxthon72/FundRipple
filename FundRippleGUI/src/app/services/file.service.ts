import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class FileService {

  constructor(private http:HttpClient) { }

  //
  public uploadImageProjectDescription(imageFile: File|string, userName: string, projectName: string, imgName: string): Observable<any> {
    const formData = new FormData();
    formData.append('image', imageFile);
  
    const storedToken = localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${storedToken}`
    });
  
    const url = `${environment.apiBaseUrl}/image/upload/description/${userName}/${projectName}/${imgName}`;
  
    // Set 'responseType' to 'text' to handle plain text response
    return this.http.post(url, formData, { headers, responseType: 'text' });
  }
}
