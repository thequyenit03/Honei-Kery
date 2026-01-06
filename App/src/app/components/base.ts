import { AfterContentChecked, AfterContentInit, AfterViewChecked, AfterViewInit, Component, DoCheck, inject, OnChanges, OnDestroy, OnInit, signal } from "@angular/core";
import { FormBuilder } from "@angular/forms";
import { NgxPermissionsService } from "ngx-permissions";
import { BehaviorSubject, Subscription } from "rxjs";

@Component({
    template: ''
})

export class BaseComponent implements OnInit, OnDestroy, AfterViewInit, AfterContentInit, AfterViewChecked, AfterContentChecked, DoCheck, OnChanges {
    //protected userProfile$: BehaviorSubject<UserProfileResponse | null> = new BehaviorSubject<UserProfileResponse | null>(null);
  protected userProfileSubscription: Subscription | undefined;
  protected listPermissions$: BehaviorSubject<{ id: number; name: string }[]> = new BehaviorSubject<{ id: number; name: string }[]>([]);
  private permissionsService: NgxPermissionsService = inject(NgxPermissionsService);
  public errorCode = signal<string>('');
  public errorMessage = signal<string>('');

  protected readonly fb: FormBuilder = inject(FormBuilder);

  ngOnInit(): void {
      
  }

  ngOnDestroy(): void {
      
  }

  ngAfterViewInit() {}
  ngAfterContentInit() {}
  ngAfterViewChecked() {}
  ngAfterContentChecked() {}
  ngDoCheck() {}
  ngOnChanges() {}

  protected openNewTab(route: string) {
    const url = route.startsWith('/')
      ? window.location.origin + route
      : route;

    window.open(url, '_blank');
  }  

}