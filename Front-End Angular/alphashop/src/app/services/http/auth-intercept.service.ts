import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler } from '@angular/common/http';
import { AuthappService } from '../authapp.service';

@Injectable({
  providedIn: 'root'
})
export class AuthInterceptService implements HttpInterceptor {

  constructor(private BasicAuth: AuthappService) { }

  intercept(request: HttpRequest<any>, next: HttpHandler) {
    /*
    let Username:string = "PlatEon";
    let Password:string = "bre_breh";
    let AuthHeader:string = "Basic " + window.btoa(Username + ":" + Password);
    */
    let User = this.BasicAuth.loggedUser();
    let AuthToken = this.BasicAuth.getAuthToken();

    if (User && AuthToken) {
      request = request.clone({
        setHeaders:
        {
          Authorization: AuthToken
        }
      });
    }
    
    return next.handle(request);
  }

}
