import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Articoli, ApiMsg } from 'src/app/articoli/articoli.component'
import { server, port } from 'src/app.constants';

@Injectable({
  providedIn: 'root'
})
export class ArticoliDataService {
  /*
  server:string = "localhost";
  port:string = "5051";
  */

  constructor(private httpClient:HttpClient) { }

  /*getBasicAuthHeader () {
    let Username:string = "PlatEon";
    let Password:string = "breh";
    let retVal:string = "Basic " + window.btoa(Username + ":" + Password);
    return retVal;
  }*/

  getArticoliByDescription(descrizione:string) {
    /*let headers = new HttpHeaders(
      {Authorization: this.getBasicAuthHeader()}
    );*/
    return this.httpClient.get<Articoli[]>(`http://${server}:${port}/api/articoli/cerca/descrizione/${descrizione}`);
  }

  getArticoliByCodeArt(codArt:string) {
    return this.httpClient.get<Articoli>(`http://${server}:${port}/api/articoli/cerca/codice/${codArt}`);
  }

  getArticoliByEan(barcode:string) {
    return this.httpClient.get<Articoli>(`http://${server}:${port}/api/articoli/cerca/ean/${barcode}`);
  }

  deleteArticolo(codArt:string) {
    return this.httpClient.delete<ApiMsg>(`http://${server}:${port}/api/articoli/elimina/${codArt}`);
  }

  updateArticolo(articolo:Articoli){
    return this.httpClient.put<ApiMsg>(`http://${server}:${port}/api/articoli/modifica`, articolo);
  }

  createArticolo(articolo:Articoli) {
    return this.httpClient.post<ApiMsg>(`http://${server}:${port}/api/articoli/inserisci`, articolo);
  }

}
