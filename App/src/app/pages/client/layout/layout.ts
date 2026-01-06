import { CommonModule } from '@angular/common';
import { Component, inject, OnDestroy } from '@angular/core';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzLayoutModule } from 'ng-zorro-antd/layout';
import { NzMenuModule } from 'ng-zorro-antd/menu';
import { NzAvatarModule } from 'ng-zorro-antd/avatar';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzTooltipModule } from 'ng-zorro-antd/tooltip';
import { NzBadgeModule } from 'ng-zorro-antd/badge';
import { NzDropDownModule } from 'ng-zorro-antd/dropdown';
import { NzDividerModule } from 'ng-zorro-antd/divider';
import { BaseComponent } from '../../../components/base';
import { NzDrawerModule } from 'ng-zorro-antd/drawer';

@Component({
  standalone: true,
  selector: 'app-layout',
  imports: [
    CommonModule,
    RouterLink,
    RouterOutlet,
    NzIconModule,    
    NzLayoutModule,
    NzMenuModule,
    NzAvatarModule,
    NzButtonModule,
    NzTooltipModule,
    NzBadgeModule,
    NzDropDownModule,
    NzDividerModule,
    NzDrawerModule
  ],
  templateUrl: './layout.html',
  styleUrl: './layout.css',
})
export class Layout extends BaseComponent implements OnDestroy {
  private router = inject(Router);
  protected isVisibleDrawer = false;
  isFullscreen = false;

  constructor() {
    super();
    document.addEventListener('fullscreenchange', this.onFullscreenChange.bind(this));
  }

  override ngOnInit(): void {
    super.ngOnInit();
  }

  override ngOnDestroy(): void {
    document.removeEventListener('fullscreenchange', this.onFullscreenChange.bind(this));
  }

  onFullscreenChange(): void {
    this.isFullscreen = !!document.fullscreenElement;
  }

  toggleFullscreen(): void {
    if (!this.isFullscreen) {
      document.documentElement.requestFullscreen();
    } else {
      if (document.exitFullscreen) {
        document.exitFullscreen();
      }
    }
  }

  logout(): void {
    this.router.navigate(['/login']);
  }

  getTitleFromPath(path: string): string {
    return path.charAt(0).toUpperCase() + path.slice(1);
  }

  toggleDrawer(): void {
    this.isVisibleDrawer = !this.isVisibleDrawer;
  }

}
