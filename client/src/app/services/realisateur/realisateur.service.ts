import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RealisateurService {

/*
  apiUrl = 'http://localhost:8080/api/realisateurs';
*/
  apiUrl = environment.apiUrl + '/realisateurs';

  http: HttpClient = inject(HttpClient);

  getRealisateurs(): Observable<any> {
    return this.http.get(this.apiUrl);
  }

  getRealisateur(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/${id}`);
  }

  addRealisateur(realisateur: any): Observable<any> {
    return this.http.post(`${this.apiUrl}`, realisateur);
  }

  updateRealisateur(id: number, realisateur: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/${id}`, realisateur);
  }

  deleteRealisateur(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
}
