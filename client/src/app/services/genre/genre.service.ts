import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GenreService {

  apiUrl = 'http://localhost:8080/api/genres';

  http: HttpClient = inject(HttpClient);

  getGenres(): Observable<any> {
    return this.http.get(this.apiUrl);
  }

  getGenre(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/${id}`);
  }

  addGenre(genre: any): Observable<any> {
    return this.http.post(`${this.apiUrl}`, genre);
  }

  updateGenre(id: number, genre: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/${id}`, genre);
  }

  deleteGenre(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
}
