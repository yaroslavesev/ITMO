import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  standalone: true,
  imports: [CommonModule, FormsModule],
})
export class LoginComponent {
  username = '';
  password = '';
  errorMessage = '';

  constructor(
      private authService: AuthService,
      private router: Router
  ) {}

  onLogin() {
    this.authService.login(this.username, this.password).subscribe({
      next: (response) => {
        if (response && response.token) {
          this.authService.setToken(response.token);
          this.router.navigate(['/main']);
        }
      },
      error: (err) => {
        this.errorMessage = err.error?.message || 'Ошибка авторизации!';
      },
    });
  }

  onRegister() {
    this.authService.register(this.username, this.password).subscribe({
      next: (response) => {
        if (response && response.token) {
          this.authService.setToken(response.token);
          this.router.navigate(['/main']);
        }
      },
      error: (err) => {
        this.errorMessage = err.error?.message || 'Ошибка регистрации!';
      },
    });
  }
}
