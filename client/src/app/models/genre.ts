import {Oeuvre} from './oeuvre';

export interface Genre {
  readonly id?: number;
  nom_genre: string;
  description_genre: string;
  oeuvres?: Oeuvre[];
}
