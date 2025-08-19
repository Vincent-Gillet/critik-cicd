import {Utilisateur} from './utilisateur';
import {Oeuvre} from './oeuvre';

export interface Critique {
  readonly id?: number;
  note: number;
  commentaire: string;
  date: Date;
  utilisateur?: Utilisateur;
  oeuvre?: Oeuvre;
}
