import {Component, inject, OnInit} from '@angular/core';
import {CarteOeuvreComponent} from '../../parts/carte-oeuvre/carte-oeuvre.component';
import {OeuvreService} from '../../../services/oeuvre/oeuvre.service';
import {Oeuvre} from '../../../models/oeuvre';
import {LoaderComponent} from '../../parts/loader/loader.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    CarteOeuvreComponent,
    LoaderComponent
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit {

  oeuvreService: OeuvreService = inject(OeuvreService);

  oeuvres: Oeuvre[] = [];

  ngOnInit() {
    this.oeuvreService.getOeuvres().subscribe({
      next: (data: Oeuvre[])=> {
        console.log('Oeuvres loaded:', data);
        this.oeuvres = data;
      },
      error: (error) => {
      console.error('Error loading oeuvres:', error);
      }
    });
  }

}
