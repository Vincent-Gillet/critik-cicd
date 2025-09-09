import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Oeuvre} from '../../models/oeuvre';
import {environment} from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class OeuvreService {

  apiUrl = environment.apiUrl + '/oeuvres';

  http: HttpClient = inject(HttpClient);

  getOeuvres(): Observable<any> {
    return this.http.get(this.apiUrl);
  }

  searchOeuvres(params: { [key: string]: any }) {
/*    const httpParams = new HttpParams({ fromObject: params });
    console.log("url requete oeuvre : ", this.http.get<Oeuvre[]>(this.apiUrl + '/recherche', { params: httpParams }))
    return this.http.get<Oeuvre[]>(this.apiUrl + '/recherche', { params: httpParams });
  */

    const filteredParams: { [key: string]: any } = {};
    Object.keys(params).forEach(key => {
      const value = params[key];
      if (value !== '' && value !== null && value !== undefined) {
        filteredParams[key] = value;
      }
    });
    const httpParams = new HttpParams({ fromObject: filteredParams });
    return this.http.get<Oeuvre[]>(this.apiUrl + '/recherche', { params: httpParams });

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
