import {Component, inject, OnInit} from '@angular/core';
import {OeuvreService} from '../../../services/oeuvre/oeuvre.service';
import {Oeuvre} from '../../../models/oeuvre';

@Component({
  selector: 'app-oeuvres',
  standalone: true,
  imports: [],
  templateUrl: './oeuvres.component.html',
  styleUrl: './oeuvres.component.scss'
})
export class OeuvresComponent implements OnInit {
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
