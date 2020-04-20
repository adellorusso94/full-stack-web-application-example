import { Component, OnInit } from '@angular/core';
import { ArticoliDataService } from '../services/data/articoli-data.service';
import { ActivatedRoute, Router } from '@angular/router';

export class Articoli {

  constructor(
    public codArt:string,
    public descrizione:string,
    public um:string,
    public pzCart:number,
    public pesoNetto:number,
    public prezzo:number,
    public idStatoArt:string,
    public data:Date,
    public iva:Iva,
    public famAssort:FamAss
  ) { }

}

export class Iva {

  constructor(public idIva:number, public descrizione:string, public aliquota:number) {}

}

export class FamAss {

  constructor(public id:number, public descrizione:string) {}

}

export class ApiMsg {

  constructor(public code: string, public message: string) { }

}

@Component({
  selector: 'app-articoli',
  templateUrl: './articoli.component.html',
  styleUrls: ['./articoli.component.css']
})
export class ArticoliComponent implements OnInit {

  NumArt: number = 0;

  pagina: number = 1;
  righe: number = 10;

  apiMsg: ApiMsg;
  messaggio: string;

  filter: string = '';
  articoli: Articoli[];
  articolo: Articoli;

  constructor(private route: ActivatedRoute, private router: Router, private articoliService: ArticoliDataService) { }

  ngOnInit(): void {
    this.filter = this.route.snapshot.params['filter'];
    if (this.filter != undefined) {
      this.getArticoli(this.filter);
    }
  }

  refresh() {
    this.messaggio = "";
    this.getArticoli(this.filter);
  }

  getArticoli(filter: string) {
    this.articoliService.getArticoliByCodeArt(filter).subscribe(
      response => {
        console.log('Ricerca articoli per codArt con filtro ' + filter);
        this.articoli = [];
        this.articolo = response;
        this.articoli.push(this.articolo);
        this.NumArt = this.articoli.length;
        console.log('Articolo trovato!');
      },
      error => {
        console.log('Ricerca articoli per codArt con filtro ' + filter);
        console.log(error.error.messaggio);
        this.articoliService.getArticoliByDescription(filter).subscribe(
          response => {
            console.log('Ricerca articoli per descrizione con filtro ' + filter);
            this.articoli = [];
            this.articoli = response;
            this.articoli.push(this.articolo);
            this.NumArt = this.articoli.length;
            console.log('Articoli trovati!');
          },
          error => {
            console.log('Ricerca articoli per descrizione con filtro ' + filter);
            console.log(error.error.messaggio);
            this.articoliService.getArticoliByEan(filter).subscribe(
              response => {
                console.log('Ricerca per barcode con filtro ' + filter);
                this.articoli = [];
                this.articolo = response;
                this.articoli.push(this.articolo);
                this.NumArt = this.articoli.length;
                console.log('Articolo trovato!');
              },
              error => {
                console.log('Ricerca articolo per barcode con filtro ' + filter);
                console.log(error.error.messaggio);
                this.articoli = [];
                this.NumArt = this.articoli.length;
              }
            )
          }
        )
      }
    )
  }

  Elimina(codArt: string) {
    console.log(`Eliminazione articolo ${codArt}`);
    this.articoliService.deleteArticolo(codArt).subscribe(
      response => {
        this.apiMsg = response;
        this.messaggio = this.apiMsg.message;
        this.refresh();
      }
    )
  }

  Modifica(codArt: string) {
    if(codArt != null) {
      console.log(`Modifica articolo ${codArt}`);
      this.router.navigate(['newart/', codArt]);
    } else {
      console.log(`Inserimento nuovo articolo`);
      this.router.navigate(['newart']);
    }
  }

}
