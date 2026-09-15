import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Auth } from '../auth';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-register',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './register.html',
  styleUrl: './register.scss',
})
export class Register {

  registerForm=new FormGroup({
    email:new FormControl('',[
      Validators.required,
      Validators.email
    ]),
    password: new FormControl('',[
      Validators.required
    ])
  });

  constructor(private auth:Auth){}

  onSubmit():void{
    if(this.registerForm.invalid){
      return;
    }

    const request ={
      email: this.registerForm.value.email!,
      password: this.registerForm.value.password!
    };
    
    this.auth.register(request).subscribe({
      next: (response) => {
        console.log('Registration successful:', response);
      },
      error:(error) => {
        console.error('Registration failed:', error);
      }
    });
  }
}
