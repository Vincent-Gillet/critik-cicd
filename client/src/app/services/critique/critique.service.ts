import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CritiqueService {


  apiUrl = 'http://localhost:8080/api/critiques';

  http: HttpClient = inject(HttpClient);

  getCritiques(): Observable<any> {
    return this.http.get(this.apiUrl);
  }

  getCritique(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/${id}`);
  }

  addCritique(critique: any): Observable<any> {
    return this.http.post(`${this.apiUrl}`, critique);
  }

  updateCritique(id: number, critique: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/${id}`, critique);
  }

  deleteCritique(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
}
