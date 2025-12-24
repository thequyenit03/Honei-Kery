import { Component, Input, OnInit, signal } from '@angular/core';
import { NzResultModule } from 'ng-zorro-antd/result';
import { NzButtonModule } from 'ng-zorro-antd/button';

@Component({
  standalone: true,
  selector: 'app-display-error',
  imports: [
    NzResultModule,
    NzButtonModule
  ],
  templateUrl: './display-error.html',
  styleUrl: './display-error.css',
})
export class DisplayError {
  errorCode = signal<string | null>(null);
  errorMessage = signal<string>('');

  @Input({required: true})
  set code(value: string | null) {
    this.errorCode.set(value);
  }

  @Input({required: true})
  set message(value: string) {
    this.errorMessage.set(value || 'Đã xảy ra lỗi !');
  }

  reloadPage(){
    location.reload();
  }
}
