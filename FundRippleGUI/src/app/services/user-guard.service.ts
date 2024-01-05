import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { AuthenticationService } from './authentication.service';
import { Observable, of } from 'rxjs';
import { map, catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UserGuardService implements CanActivate {
  
  constructor(
    private authService: AuthenticationService,
    private router: Router
  ) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean> | boolean {
    return this.authService.testUserByHeaderInStorage().pipe(
      map(response => {
        if (response) {
          console.log("Guard OK");
          return true;
        } else {
          console.log("Guard Bad");
          this.router.navigate(['/home']);
          return false;
        }
      }),
      catchError((error) => {
        console.log("Error occurred", error);
        this.router.navigate(['/home']);
        return of(false);
      })
    );
  }
}
