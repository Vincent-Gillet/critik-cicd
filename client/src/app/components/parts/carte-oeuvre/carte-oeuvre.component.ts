import {Component, Input} from '@angular/core';
import {Oeuvre} from '../../../models/oeuvre';
import {DatePipe} from '@angular/common';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-carte-oeuvre',
  standalone: true,
  imports: [
    DatePipe,
    RouterLink
  ],
  templateUrl: './carte-oeuvre.component.html',
  styleUrl: './carte-oeuvre.component.scss'
})
export class CarteOeuvreComponent {
  @Input()oeuvre: Oeuvre | undefined;

}
