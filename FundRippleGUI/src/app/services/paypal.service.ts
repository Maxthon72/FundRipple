import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Payment } from '../interfaces/payment';
import { Observable } from 'rxjs';
import { PayPal } from '../interfaces/PayPal';
import { environment } from '../environments/environment';
import { Project } from '../interfaces/Project/Project';

@Injectable({
  providedIn: 'root'
})
export class PaypalService {

  constructor(private http:HttpClient) { }

  public initPayPalPayment(amount:number,payment:Payment):Observable<PayPal>{
    const storedToken = localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${storedToken}`,
    });

    return this.http.post<PayPal>(`${environment.apiBaseUrl}/paypal/pay/${payment.projectName}?amount=${amount}`,payment, { headers });
  }

  public addPaymentToProject(payment:Payment):Observable<Project>{
    const storedToken = localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${storedToken}`,
    });

    return this.http.post<Project>(`${environment.apiBaseUrl}/payment`,payment, { headers });
  }

  public getSupportOfProjectByUser(projectName: string): Observable<number> {
    const storedToken = localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${storedToken}`,
    });
  
  
    return this.http.get<number>(`${environment.apiBaseUrl}/payment/support/${projectName}`, { headers});
  }
  
}
