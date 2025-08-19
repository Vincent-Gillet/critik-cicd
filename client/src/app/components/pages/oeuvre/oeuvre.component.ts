import {Component, inject, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {OeuvreService} from '../../../services/oeuvre/oeuvre.service';
import {Oeuvre} from '../../../models/oeuvre';
import {DatePipe} from '@angular/common';

@Component({
  selector: 'app-oeuvre',
  standalone: true,
  imports: [
    DatePipe
  ],
  templateUrl: './oeuvre.component.html',
  styleUrl: './oeuvre.component.scss'
})
export class OeuvreComponent implements OnInit {
  private route: ActivatedRoute = inject(ActivatedRoute)
  private oeuvreService = inject(OeuvreService);
  oeuvre: Oeuvre | undefined;

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.oeuvreService.getOeuvre(params['id']).subscribe({
        next: (data) => {
          console.log('Oeuvre loaded:', data);
          this.oeuvre = data;
        },
        error: (error) => {
          console.error('Error loading oeuvre:', error);
        }
      })

    });
  }
}
