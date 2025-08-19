export interface Utilisateur {
  readonly id?: number;
  nom_utilisateur: string;
  email: string;
}

export interface UtilisateurCreation {
  readonly id?: number;
  nom_utilisateur: string;
  email: string;
  mot_de_passe: string;
}
