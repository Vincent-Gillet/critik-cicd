import {Component, inject, OnInit} from '@angular/core';
import {OeuvreService} from '../../../services/oeuvre/oeuvre.service';
import {Oeuvre} from '../../../models/oeuvre';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {Genre} from '../../../models/genre';
import {GenreService} from '../../../services/genre/genre.service';
import {CarteOeuvreComponent} from '../../parts/carte-oeuvre/carte-oeuvre.component';

@Component({
  selector: 'app-oeuvres',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule,
    CarteOeuvreComponent
  ],
  templateUrl: './oeuvres.component.html',
  styleUrl: './oeuvres.component.scss'
})
export class OeuvresComponent implements OnInit {
  oeuvreService: OeuvreService = inject(OeuvreService);
  genreService: GenreService = inject(GenreService);

  oeuvres: Oeuvre[] = [];
  genres: Genre[] = [];

  totalOeuvres: number = 0;
  totalPage: number = 0;
  pageActuelle: number = 0;

  arrayPages: number[] = [];

  // FormGroup pour le formulaire de recherche

  searchOeuvreForm: FormGroup;
  // Booléens d'état
  isSubmitted = false;
  isLoading = false;


  constructor(private fb: FormBuilder, private router: Router) {
    // Création du form
    this.searchOeuvreForm = this.fb.group({
      titre: ['', []],
      type: ['', []],
      date_sortie: ['', []],
      nom_genre: ['', []],
      nom_realisateur: ['', []],
      page: [this.pageActuelle, []],
      size: [4, []]
    });
  }

  generatePageNumbers(): void {
    this.arrayPages = [];
    for (let i = 0; i < this.totalPage; i++) {
      this.arrayPages.push(i);
    }
  }

  previousPage() {
    if (this.pageActuelle > 0) {
      this.pageActuelle--;
      this.searchOeuvres();
    }
    console.log(this.pageActuelle);
  }

  nextPage() {
    if (this.pageActuelle < this.totalPage - 1) {
      this.pageActuelle++;
      this.searchOeuvres();
    }
    console.log(this.pageActuelle);
  }

  goToPage(page: number) {
    if (this.pageActuelle !== page && page >= 0 && page < this.totalPage) {
      this.pageActuelle = page;
      this.searchOeuvres();
    }
    console.log(this.pageActuelle);

  }

  searchOeuvres() {
    const searchValue = this.searchOeuvreForm.value.search;

    this.searchOeuvreForm.patchValue({
      page: this.pageActuelle,
      size: this.searchOeuvreForm.value.size || 4
    });

    const oeuvreData = this.searchOeuvreForm.value;
    this.oeuvres = [];

    this.oeuvreService.searchOeuvres(oeuvreData).subscribe({
      next: (data: any)=> {
        console.log('Oeuvres brutes:', data);
        console.log('Oeuvres loaded:', data.oeuvres.content);
        this.oeuvres = [...data.oeuvres.content];
        this.totalPage = data.pages;
        this.totalOeuvres = data.total;
        this.pageActuelle = data.currentPage;
        this.generatePageNumbers();
      },
      error: (error) => {
        console.error('Error loading oeuvres:', error);
      }
    });
  }

  resetForm() {
    this.searchOeuvreForm.reset({
      titre: '',
      type: '',
      nom_genre: '',
      nom_realisateur: ''
    });
    this.searchOeuvres()
  }

  loadGenres() {
    this.genreService.getGenres().subscribe({
      next: (data: Genre[]) => {
        console.log('Genres loaded:', data);
        this.genres = data;
      },
      error: (error) => {
        console.error('Error loading genres:', error);
      }
    });
  }

  ngOnInit() {
    this.searchOeuvres();
    this.loadGenres();


  }



  onSubmit():void {
    this.oeuvres = [];
    this.searchOeuvres();
  }
}
