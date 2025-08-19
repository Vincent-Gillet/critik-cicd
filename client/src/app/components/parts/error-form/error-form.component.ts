import {Component, Input} from '@angular/core';
import {FormGroup} from '@angular/forms';

@Component({
  selector: 'app-error-form',
  standalone: true,
  imports: [],
  templateUrl: './error-form.component.html',
  styleUrl: './error-form.component.scss'
})
export class ErrorFormComponent {
  @Input() fieldName!: string;
  @Input() nameFormGroup!: FormGroup;
  @Input() isSubmitted: boolean = false;

  isFieldInvalid(fieldName: string): boolean {

    const field = this.nameFormGroup.get(fieldName);

    // Retourne true si TOUTES ces conditions sont vraies :
    //    champ existe ET champ invalide ET (champ dirty OU touched OU formulaire est soumis)
    return Boolean(field && field.invalid && this.isSubmitted);
    // Boolean() créer un booléen d'après une donnée flasy ou truthy
  }

  renameInput: Record<string, string> = {
    //User
    'email': 'Email',
    'mot_de_passe': 'Mot de passe'
  }

  getFieldError(fieldName: string): string {
    const field = this.nameFormGroup.get(fieldName);

    // Vérifier si le champ existe et a des erreurs
    if (field && field.errors) {
      // field.errors est un objet avec les types d'erreurs comme clés
      // Ex: { required: true, email: true, minlength: { requiredLength: 6, actualLength: 3 } }
      if (field.errors['required']){
        const displayName = this.renameInput[fieldName] || fieldName;
        return `${displayName} obligatoire`;
      }
      if (field.errors['email']){
        return 'Format email invalide';
      }
      if (field.errors['minlength']) {
        // L'erreur minlength contient des infos détaillées
        return `Minimum ${field.errors['minlength'].requiredLength} caractères`;
      }

    }
    return '';
  }
}
