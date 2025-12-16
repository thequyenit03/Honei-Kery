import { Component, inject } from "@angular/core";
import { BaseComponent } from "./base";
import { Subject } from "rxjs";
import { FormArray, FormControl, FormGroup, Validators } from "@angular/forms";
import { NZ_MODAL_DATA, NzModalRef } from "ng-zorro-antd/modal";

@Component({
    template: ''
})

export class BaseFormComponent extends BaseComponent {
    protected unsubscribe: Subject<any> = new Subject();
    protected saveForm: FormGroup = new FormGroup({});

    protected nzModalData: any = inject(NZ_MODAL_DATA);
    protected nzModalRef = inject(NzModalRef);

    public override ngOnInit(): void {
        super.ngOnInit();
    }

    public override ngOnDestroy(): void {
        super.ngOnDestroy();
    }

    get saveFormValue(): any {
        return this.saveForm.value;
    }

    get saveFormValueRaw(): any {
        return this.saveForm.getRawValue();
    }

    protected clearSaveForm(): void {
        this.saveForm.reset();
    }

    protected getSaveFormItemControl(name: string): FormControl {
        return this.saveForm.get(name) as FormControl;
    }

    protected getSaveFormItemAsFormArray(name: string): FormArray {
        return this.saveForm.get(name) as FormArray;
    }

    protected setSaveFormItemControl(name: string, value: any): void {
        this.saveForm.get(name)?.setValue(value);
    }

    protected setSaveFormItemControlDisabled(name: string, disabled: boolean): void {
        if(disabled) {
            this.saveForm.get(name)?.disable();            
        } else {
            this.saveForm.get(name)?.enable();
        }
    }

    protected setSaveFormItemControlRequired(name: string, required: boolean): void {
        const control = this.saveForm.get(name);
        if(control) {
            if(required) {
                control.setValidators([Validators.required]);
            } else {
                control.clearValidators();
            }
            control.updateValueAndValidity();
        }
    }

    protected setSaveFormItemControlReadonly(name: string, readonly: boolean): void {
        if (readonly) {
            this.saveForm.get(name)?.disable();
        } else {
            this.saveForm.get(name)?.enable();
        }
    }

    protected getSaveFormItemValue(name: string): any {
        return this.getSaveFormItemControl(name).value;
    }

    protected setSaveFormItemValue(name: string, value: any): void {
        this.getSaveFormItemControl(name).setValue(value);
    }

    protected removeSaveFormItem(name: string): void {
        this.saveForm.removeControl(name);
    }

    protected addSaveFormItem(name: string, value: any): void {
        this.saveForm.addControl(name, new FormControl(value));
    }

    protected addSaveFormItemWithValidators(name: string, value: any, validators: any[]): void {
        this.saveForm.addControl(name, new FormControl(value, validators));
    }

}