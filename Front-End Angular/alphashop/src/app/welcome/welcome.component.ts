import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SalutiDataService } from '../services/data/saluti-data.service';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.css']
})
export class WelcomeComponent implements OnInit {

  saluti:string = 'Benvenuti nel sito Alphashop';
  titolo2:string = 'Seleziona gli articoli da acquistare';

  utente:string = '';
  messaggio:Object = '';

  constructor(private route:ActivatedRoute, private salutiSrv:SalutiDataService) { }

  ngOnInit(): void {
    this.utente = this.route.snapshot.params['username'];
  }

  getSaluti() {
    this.salutiSrv.getSaluti(this.utente).subscribe(
      response => this.handleResponse(response),
      error => this.handleError(error)
    );
  }

  handleResponse(response: Object) {
    this.messaggio = response;
    //console.log(response);
  }

  handleError(error) {
    this.messaggio = error.error.message;
  }

}
