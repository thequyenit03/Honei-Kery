import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class Auth {
  login(loginRequest: { username: any; password: any; }, companyCode: any) {
    throw new Error('Method not implemented.');
  }
  
}
