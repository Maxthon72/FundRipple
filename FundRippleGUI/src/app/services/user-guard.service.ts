import { Injectable } from '@angular/core';
import { AuthenticationService } from './authentication.service';
import { ActivatedRouteSnapshot, Router, RouterStateSnapshot } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class UserGuardService {

  constructor(private authService: AuthenticationService,
    private router: Router) { }

    canActivate(
      route: ActivatedRouteSnapshot,
      state: RouterStateSnapshot
    ): boolean{
      const targetRoute = state.url;
      console.log(targetRoute)
      if(this.authService.testUser2()) {
        return true
      } else {
        this.router.navigate(['/home']);
        return false;
      }
      
    }
}
