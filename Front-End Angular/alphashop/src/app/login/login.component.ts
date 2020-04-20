import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthJWTService } from '../services/AuthJWTService';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username:string = '';
  password:string = '';
  autenticato:boolean = true;
  errorMsg:string = 'Spiacente, username e/o password sono errati!';
  //consentito:boolean = false;
  //infoMsg:string = 'Login effettuato con successo!';

  constructor(private route:Router, private BasicAuth:AuthJWTService) { }

  ngOnInit(): void {
  }

  gestAut(): void {
    this.BasicAuth.autenticaService(this.username, this.password).subscribe(
      data => {
        console.log(data);
        this.autenticato = true;
        this.route.navigate(['welcome', this.username]);
      },
      error => {
        console.log(error);
        this.autenticato = false;
      }
    )
    /*
    if(this.BasicAuth.autentica(this.username, this.password)) {
      this.autenticato = true;
      this.route.navigate(['welcome', this.username]);
    } else {
      this.autenticato = false;
    }
    */
  }

}
