import { Directive, effect, EnvironmentInjector, inject, input, TemplateRef, ViewContainerRef } from "@angular/core";
import { DisplayError } from "../../components/display-error/display-error";

@Directive({
    selector:'[appShowOnErrorCode]',
    standalone: true
})

export class ShowErrorDirective {
    private originalTemplate = inject<TemplateRef<any>>(TemplateRef);
    private viewContainer = inject<ViewContainerRef>(ViewContainerRef);
    private injector = inject<EnvironmentInjector>(EnvironmentInjector);
    appShowOnErrorCode = input<string | null>();
    errorMessage = input<string>('');

    constructor() {
    effect(() => {
      const code = this.appShowOnErrorCode();
      const msg = this.errorMessage();

      this.viewContainer.clear();

      if (!code || code === '200') {        
        this.viewContainer.createEmbeddedView(this.originalTemplate);
      } else {        
        const compRef = this.viewContainer.createComponent(DisplayError, {
          environmentInjector: this.injector
        });
        compRef.instance.code = code;
        compRef.instance.message = msg;
      }
    });
  }
}