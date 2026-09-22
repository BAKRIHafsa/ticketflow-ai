import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Auth } from '../auth';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './login.html',
  styleUrl: './login.scss',
})
export class Login {

  errorMessage='';

  loginForm = new FormGroup({
    email: new FormControl('',[
      Validators.required,
      Validators.email
    ]),
    password: new FormControl('',[
      Validators.required
    ])
  });

  constructor(private auth:Auth){}

  onSubmit():void{
    if (this.loginForm.invalid){
      return;
    }

    this.errorMessage='';

    const request={
      email:this.loginForm.value.email!,
      password:this.loginForm.value.password!
    };

    this.auth.login(request).subscribe({
      next: (response) => {
        this.auth.saveToken(response.token);
        console.log('Login successful');
      },
      error: (error) => {
        console.error('Login failed:', error);
        this.errorMessage='Invalid email or password.';
      }
    });
  }
}
