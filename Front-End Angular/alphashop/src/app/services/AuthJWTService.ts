import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { authServerUri } from 'src/app.constants';

export class AuthData {

  constructor(public codice:string, public messaggio:string) {}

}

@Injectable({
  providedIn: 'root'
})
export class AuthJWTService {

  constructor(private httpClient:HttpClient) { }

  autenticaService(username:string, password:string) {
    return this.httpClient.post<any>(
      `${authServerUri}`, {username, password}).pipe(
        map(
          data => {
            sessionStorage.setItem("Utente", username);
            sessionStorage.setItem("AuthToken", `Bearer ${data.token}`);
            return data;
          }
        )
      );
  }

  loggedUser():string {
    let utente = sessionStorage.getItem("Utente");
    return(sessionStorage.getItem("Utente") != null) ? utente : "";
  }

  getAuthToken():string {
    if (this.loggedUser) {
      return sessionStorage.getItem("AuthToken");
    } else {
      return "";
    }
  }

  isLogged():boolean{
    return(sessionStorage.getItem("Utente") != null) ? true : false;
  }

  clearAll():void {
    sessionStorage.removeItem("Utente");
  }

}
