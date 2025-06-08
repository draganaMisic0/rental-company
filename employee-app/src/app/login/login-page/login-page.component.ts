import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { LoginService } from '../../services/login-service.service';
import { Router } from '@angular/router';
import { Privileges } from '../../layout/main-layout/privileges';

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

  constructor(private fb: FormBuilder, 
    private loginService: LoginService,
    private router: Router
  ) {
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

    
    const MANAGER_PRIVILEGES: Privileges[] = [
      Privileges.MANAGE_VEHICLES,
      Privileges.MANAGE_USERS,
      Privileges.MANAGE_MANUFACTURERS,
      Privileges.MANAGE_RENTALS,
      Privileges.REPORT_ISSUES,
      Privileges.VIEW_STATISTICS,
      Privileges.VIEW_VEHICLE_MAP,
      Privileges.SET_RENTAL_PRICES,
      Privileges.MANAGE_EMPLOYEES,
      Privileges.LOGOUT
    ];
    const OPERATOR_PRIVILEGES : Privileges[] = [
      Privileges.MANAGE_RENTALS,
      Privileges.VIEW_VEHICLE_MAP,
      Privileges.MANAGE_USERS,
      Privileges.REPORT_ISSUES,
      Privileges.LOGOUT
    ];
    const ADMIN_PRIVILEGES : Privileges[]  = [
      Privileges.MANAGE_VEHICLES,
      Privileges.MANAGE_MANUFACTURERS,
      Privileges.MANAGE_USERS,
      Privileges.MANAGE_EMPLOYEES,
      Privileges.LOGOUT
    ];

    this.loginService.login({username: this.username?.value, password: this.password?.value}).subscribe(
      {next: (data)=> {
        let dataWithPrivileges : any = data;
        switch(data.role){
          case "admin":
            dataWithPrivileges.privileges = ADMIN_PRIVILEGES;
            break;
          case "operator":
            dataWithPrivileges.privileges = OPERATOR_PRIVILEGES;
            break;
          case "manager":
            dataWithPrivileges.privileges = MANAGER_PRIVILEGES;
            break;
          default:
            dataWithPrivileges.privileges = ["logout"]
            
        }
        localStorage.setItem('userData', JSON.stringify(data));
        this.router.navigate(['/'])
      },
      error: (error)=> {console.error(error);}
      }
    );
  }
}
