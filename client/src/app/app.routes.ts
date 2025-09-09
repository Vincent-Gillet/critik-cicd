import { Routes } from '@angular/router';
import {HomeComponent} from './components/pages/home/home.component';
import {OeuvreComponent} from './components/pages/oeuvre/oeuvre.component';
import {LoginComponent} from './components/pages/login/login.component';
import {SubcribeComponent} from './components/pages/subcribe/subcribe.component';
import {ProfilComponent} from './components/pages/profil/profil.component';
import {OeuvresComponent} from './components/pages/oeuvres/oeuvres.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'oeuvres', component: OeuvresComponent },
  { path: 'oeuvre/:id', component: OeuvreComponent },
  { path: 'connexion', component: LoginComponent},
  { path: 'inscription', component: SubcribeComponent},
  { path: 'profil', component: ProfilComponent },
];
