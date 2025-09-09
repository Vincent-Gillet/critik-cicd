import {Realisateur} from './realisateur';
import {Critique} from './critique';
import {Genre} from './genre';

export interface Oeuvre {
  readonly id?: number;
  titre: string;
  description_oeuvre: string;
  type: string;
  date_sortie: string;
  realisateur?: Realisateur;
  critiques?: Critique[];
  genres?: Genre[];
}
