import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { WelcomeComponent } from './welcome/welcome.component';
import { ErrorComponent } from './error/error.component';
import { ArticoliComponent } from './articoli/articoli.component';
import { NewartComponent } from './newart/newart.component';
import { LogoutComponent } from './logout/logout.component';
import { RouteGuardService } from './services/route-guard.service';
import { Ruoli } from 'src/models/Ruoli';
import { ForbiddenComponent } from './forbidden/forbidden.component';


const routes: Routes = [
  {path:'', component:LoginComponent},
  {path:'index', component:LoginComponent},
  {path:'login', component:LoginComponent},
  {path:'welcome/:username', component:WelcomeComponent, canActivate:[RouteGuardService], data: { roles: [Ruoli.utente]}},
  {path:'articoli', component:ArticoliComponent, canActivate:[RouteGuardService], data: { roles: [Ruoli.utente]}},
  {path:'articoli/:filter', component:ArticoliComponent, canActivate:[RouteGuardService], data: { roles: [Ruoli.utente]}},
  {path:'newart', component:NewartComponent, canActivate:[RouteGuardService], data: { roles: [Ruoli.amministratore]}},
  {path:'newart/:codart', component:NewartComponent, canActivate:[RouteGuardService], data: { roles: [Ruoli.amministratore]}},
  {path:'logout', component:LogoutComponent},
  {path:'forbidden', component:ForbiddenComponent},
  {path:'**', component:ErrorComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
