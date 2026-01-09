import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzIconModule, NzIconService } from 'ng-zorro-antd/icon';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzCardModule } from 'ng-zorro-antd/card';
import { Auth } from '../../../services/auth';
import { Router } from '@angular/router';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { catchError, finalize, Subject, takeUntil } from 'rxjs';
import { EyeOutline, EyeInvisibleOutline, UserOutline, LockOutline } from '@ant-design/icons-angular/icons';
import { LoginRequest } from '../../../models/auth.models';

@Component({
  standalone: true,
  selector: 'app-login',
  imports: [
    ReactiveFormsModule,
    NzFormModule,
    NzInputModule,
    NzButtonModule,
    NzCardModule,
    NzIconModule
  ],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  private fb = inject(FormBuilder);
  private nzIconService = inject(NzIconService);
  private authService = inject(Auth);
  private router = inject(Router);
  private notification = inject(NzNotificationService);

  passwordVisible = false;
  isLoading = false;
  loginForm: FormGroup;
  private unsubscribe: Subject<any> = new Subject<any>();

  constructor() {
    this.loginForm = this.fb.group({      
      username: ['', Validators.required],
      password: ['', Validators.required],
    });
    this.nzIconService.addIcon(EyeOutline, EyeInvisibleOutline, UserOutline, LockOutline);
  }

  submitForm(): void {
    if(this.loginForm.valid) {
      const data = this.loginForm.value;
      this.isLoading = true;
      this.authService.login(data as LoginRequest)
        .pipe(
          takeUntil(this.unsubscribe),
          finalize(() => this.isLoading = false)
        )
        .subscribe((res) => {
          if(res && res.errorCode === '200') {
            this.router.navigate(['/home']).then(() => {
              this.notification.success('<b>Thành công</b>', 'Đăng nhập thành công');
            });
          }
        })        
    } else {
      Object.values(this.loginForm.controls).forEach(control => {
        if (control.invalid) {
          control.markAsDirty();
          control.updateValueAndValidity({ onlySelf: true });
        }
      });
    }
  }

}
