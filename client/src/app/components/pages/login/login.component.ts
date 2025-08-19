import {Component, inject} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {AuthService} from '../../../services/auth/auth.service';
import {Router, RouterLink} from '@angular/router';
import {ErrorFormComponent} from '../../parts/error-form/error-form.component';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    ErrorFormComponent,
    RouterLink
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  authService: AuthService = inject(AuthService);

  // Propriété représentant le formulaire
  loginForm: FormGroup;
  // Booléens d'état
  isSubmitted = false;
  isLoading = false;


  constructor(private fb: FormBuilder, private router: Router) {
    //Création du form
    this.loginForm = this.fb.group({

      email: ['', [Validators.required, Validators.email]],  // Champ nommé email, requis, contrainte EMAIL
      mot_de_passe: ['', [Validators.required, Validators.minLength(4)]] // Champ nommé email, requis, contrainte longueur
    });
  }

  onSubmit():void {
    // TODO: Use EventEmitter with form value
    console.warn(this.loginForm.value);

    this.isSubmitted = true;

    console.log("MON FORM EST SOUMIS");
    console.log("loginForm.valid ",this.loginForm.valid);
    console.log("Toutes les valeurs des control du groupe -> loginForm.value ",this.loginForm.value);
    console.log("Recuperer un seul control avec loginForm.get('email')",this.loginForm.get("mot_de_passe"));
    console.log("Recuperer la validité d'un control avec loginForm.get('email').valid",this.loginForm.get("email")?.valid);
    console.log("Recuperer les erreurs d'un control avec loginForm.get('motDePasse').errors",this.loginForm.get("mot_de_passe")?.errors);
    console.log("Recuperer un seul control avec loginForm.get('motDePasse')",this.loginForm.get("mot_de_passe"));


    if (this.loginForm.valid) {
      // 3. Activer le state de chargement
      this.isLoading = true;

      // 4. Récupérer les données du formulaire
      const loginData = this.loginForm.value;

      console.log('Données de connexion:', loginData);

      // 5. Simuler un appel API avec setTimeout
      // (Dans un vrai projet, ça serait un appel HTTP)
      setTimeout(() => {

        this.authService.login(loginData).subscribe(
          {
            next: (response) => {
              console.log('User login successfully:', response);
              localStorage.setItem("tokenStorage", JSON.stringify(response));

              this.authService.verifyAuth("/profil");
            },
            error: (error) => {
              console.error('Error login user:', error);
            }
          }
        );

      }, 2000);

    }

  }
}
