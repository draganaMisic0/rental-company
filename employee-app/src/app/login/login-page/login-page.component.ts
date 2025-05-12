import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { LoginService } from '../../services/login-service.service';

@Component({
  selector: 'app-login-page',
  imports: [FormsModule, ReactiveFormsModule],
  templateUrl: './login-page.component.html',
  styleUrl: './login-page.component.css',
  providers: [LoginService]
})
export class LoginPageComponent {

  submitButtonDisabled = true;

  loginForm: FormGroup;

  constructor(private fb: FormBuilder, private loginService: LoginService) {
    this.loginForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(3)]],
      password: ['', [Validators.required, Validators.minLength(3)]]
    });
  }

  get username() {
    return this.loginForm.get('username');
  }

  get password() {
    return this.loginForm.get('password')!;
  }

  onSubmit(): void {
    if (this.loginForm.invalid) {
      this.loginForm.markAllAsTouched();
      return;
    }

    const formData = this.loginForm.value;
    this.loginService.login({username: this.username?.value, password: this.password?.value}).subscribe(
      {next: (data)=> {console.log("SUCCESS: " + data)},
      error: (error)=> {console.error(error);}
      }
    );
  }
}
