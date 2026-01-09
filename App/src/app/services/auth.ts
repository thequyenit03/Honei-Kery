import { Injectable } from '@angular/core';
import { LoginRequest, LoginResponse } from '../models/auth.models';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { Router } from '@angular/router';
import { ApiResponse } from '../models/api-response.models';
import { environment } from '../../environments/environment';
import { ApiConstants } from '../constants/api.constants';
import { catchError, tap, throwError } from 'rxjs';
import { authStore, resetAuth, updateAuth } from '../store/auth.store';

@Injectable({
  providedIn: 'root',
})
export class Auth {
  private readonly apiUrl: string = environment.ApiUrl;

  constructor(private http: HttpClient, private notification: NzNotificationService, private router: Router) {}

  login(loginRequest: LoginRequest) {
    const reqHeaders = new HttpHeaders().set('auth', 'false');
    const options = { headers: reqHeaders };
    return this.http.post<ApiResponse<LoginResponse>>(`${this.apiUrl}${ApiConstants.API_AUTH_LOGIN}`, loginRequest, options)
      .pipe(
        tap((res) => {
          if(res && res.errorCode === '200') {
            console.log('token', res.data.token);
            updateAuth(res.data.token, null, null, null);
          }else {
            throw new Error(res.message);
          }
        }),
        catchError((error) => {
          this.notification.error('<b>Lỗi đăng nhập</b>', error.message);
          return throwError(() => error);
        })
      );
  }

  logout(): void {
    resetAuth();    
    this.router.navigate(['/login']).then(() => {
      this.notification.success('<b>Thành công</b>', 'Đăng xuất thành công');
    })
  }

  get Token(): string | null {
    return authStore.getValue().token;
  }
  
}
