import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginRequest, LoginResponse } from './auth.models';

@Injectable({
  providedIn: 'root',
})
export class Auth {

  private readonly apiUrl='http://localhost:8080/api/auth';

  constructor(private http: HttpClient){}

  login(request: LoginRequest): Observable<LoginResponse>{
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, request);
  }

  /* register(request: RegisterRequest): Observable<RegisterResponse>{
    return this.http.post<RegisterResponse>(`${this.apiUrl}/register`, request);
  } */

  saveToken(token: string): void {
    localStorage.setItem('token', token);
  }

  logout():void{
    localStorage.removeItem('token');
  }
}
