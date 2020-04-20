import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { server, port } from 'src/app.constants';

export class AuthData {

  constructor(public codice:string, public messaggio:string) {}

}

@Injectable({
  providedIn: 'root'
})
export class AuthappService {

  constructor(private httpClient:HttpClient) { }

  autenticaService(Username:string, Password:string) {
    let AuthString = "Basic " + window.btoa(Username + ":" + Password);
    let headers = new HttpHeaders(
      {Authorization: AuthString}
    );
    return this.httpClient.get<AuthData>(
      `http://${server}:${port}/api/articoli/test`,
      {headers}).pipe(
        map(
          data => {
            sessionStorage.setItem("Utente", Username);
            sessionStorage.setItem("AuthToken", AuthString);
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
