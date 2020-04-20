import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthJWTService } from './AuthJWTService';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class RouteGuardService implements CanActivate{

  token:string = '';
  ruoli:string[];

  constructor(private BasicAuth:AuthJWTService, private route:Router) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
    this.token = this.BasicAuth.getAuthToken();

    const helper = new JwtHelperService();
    const decodedToken = helper.decodeToken(this.token);

    this.ruoli = decodedToken['authorities'];

    if(!this.BasicAuth.isLogged()){
      this.route.navigate(['login']);
      return false;
    } else {
      if(route.data.roles == null || route.data.roles.length === 0) {
        return true;
      } else if (this.ruoli.some(r => route.data.roles.includes(r))){
        return true;
      } else {
        this.route.navigate(['forbidden']);
        return false;
      }
    }
  }

}
