import {inject, Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UtilisateurService {

  apiUrl = 'http://localhost:8080/api/utilisateurs';

  http: HttpClient = inject(HttpClient);

  getUtilisateurs(): Observable<any> {
    return this.http.get(this.apiUrl);
  }

  getUtilisateur(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/${id}`);
  }

  addUtilisateur(utilisateur: any): Observable<any> {
    return this.http.post(`${this.apiUrl}`, utilisateur);
  }

  updateUtilisateur(id: number, utilisateur: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/${id}`, utilisateur);
  }

  deleteUtilisateur(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }

}
