import { Directive, effect, ElementRef, EnvironmentInjector, inject, input, TemplateRef, ViewContainerRef } from "@angular/core";
import { DisplayLoading } from "../../components/display-loading/display-loading";

@Directive({
    selector: '[appShowLoading]',
    standalone: true
})

export class ShowLoadingDirective {
    private originalTemplate = inject<TemplateRef<any>>(TemplateRef);
    private viewContainer = inject<ViewContainerRef>(ViewContainerRef);
    private injector = inject<EnvironmentInjector>(EnvironmentInjector);
    private elementRef = inject<ElementRef>(ElementRef);
    appShowLoading = input<boolean>(false);

    constructor() {
        effect(() => {
            const isLoading = this.appShowLoading();
            this.viewContainer.clear();
            if(isLoading) {
                const parentElement = this.elementRef.nativeElement.parentElement;
                const parentWidth = parentElement ? parentElement.offsetWidth : window.innerWidth;

                const compRef = this.viewContainer.createComponent(DisplayLoading, {
                    environmentInjector: this.injector
                });

                compRef.instance.setParentWidth(parentWidth);
            }else {
                this.viewContainer.createEmbeddedView(this.originalTemplate);
            }
        });
    }
}