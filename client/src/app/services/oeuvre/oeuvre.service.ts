import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OeuvreService {

  apiUrl = 'http://localhost:8080/api/oeuvres';

  http: HttpClient = inject(HttpClient);

  getOeuvres(): Observable<any> {
    return this.http.get(this.apiUrl);
  }

  getOeuvre(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/${id}`);
  }

  addOeuvre(oeuvre: any): Observable<any> {
    return this.http.post(`${this.apiUrl}`, oeuvre);
  }

  updateOeuvre(id: number, oeuvre: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/${id}`, oeuvre);
  }

  deleteOeuvre(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
}
