import { Component } from "@angular/core";
import { BaseComponent } from "./base";
import { fromEvent, Subject, Subscription, takeUntil } from "rxjs";
import { FormControl, FormGroup } from "@angular/forms";
import { UIUtils } from "../utils/ui.utils";

@Component({
    template: ''
})

export abstract class BaseListComponent<T = any> extends BaseComponent {
    protected unsubscribe: Subject<any> = new Subject();

    protected searchForm: FormGroup = new FormGroup({});
    protected searchFormSubscription: Subscription | undefined;

    private _dataSource: T[] = [];
    protected isLoadingDataSource: boolean = false;
    protected total: number = 0;
    protected currentPage: number = 1;
    protected pageSize: number = 20;
    protected sort: { sortField: string | null; sortOrder: string | null } | null = null;
    protected sortDirection: any[] = ['asc', 'desc', null];

    protected abstract loadData(): void;

    protected tableScrollY: string = '800px';

    public override ngOnInit(): void {
        super.ngOnInit();
        this.listenWindowResize();
    }

    override ngAfterViewInit(): void {
        super.ngAfterViewInit();
    }

    public override ngOnDestroy(): void {
        super.ngOnDestroy();
        if(this.searchFormSubscription) {
            this.searchFormSubscription.unsubscribe();
        }
    }
    
    protected get dataSource() {
        return this._dataSource;
    }

    protected set dataSource(value: T[]) {
        this._dataSource = value;
        this.calculateTableScrollY();
    }

    protected get searchFormValue(): any {
        return this.searchForm.value;
    }

    protected clearSearchForm(): void {
        this.searchForm.reset();
    }

    protected getSearchFormItemControl(name: string): FormControl {
        return this.searchForm.get(name) as FormControl;
    }

    protected getSearchFormItemValue(name: string): any {
        return  this.getSearchFormItemControl(name).value;
    }

    protected setSearchFormItemValue(name: string, value: any): void {
        this.getSearchFormItemControl(name).setValue(value);
    }

    protected removeSearchFormItem(name: string): void {
        this.searchForm.removeControl(name);
    }

    protected addSearchFormItem(name: string, value: any): void {
        this.searchForm.addControl(name, new FormControl(value));
    }

    get paramsForSearchLoadData() {
        const params = {
            page: this.currentPage - 1,
            size: this.pageSize
        }
        if(this.sort && this.sort?.sortField && this.sort?.sortOrder) {
            return {...params, sort: this.sort?.sortField + '.' + this.sort?.sortOrder};
        }
        return params;
    }

    onBtnSearch(): void {
        if(this.currentPage !== 1) {
            this.currentPage - 1;
            return;
        }
        this.loadData();
    }

    setCurrentPageToFirst(): void {
        this.currentPage = 1;
    }

    get widthForModal(): string {
        // dựa theo size của tailwind để đặt width cho modal
        if (window.innerWidth < 640) {
        return '100%'; // sm
        }
        if (window.innerWidth < 768) {
        return '90%'; // md
        }
        if (window.innerWidth < 1024) {
        return '80%'; // lg
        }
        if (window.innerWidth < 1280) {
        return '60%'; // xl
        }
        if (window.innerWidth < 1536) {
        return '50%'; // 2xl
        }
        return '40%'; // default for larger screens
    }

    private calculateTableScrollY() {
        setTimeout(() => {
        const viewportHeight = window.innerHeight;
        const appHeaderHeight = UIUtils.getBoxModelDimensions(document.querySelector('div.app-header')).totalHeight;
        const totalHeaderHeight = UIUtils.getBoxModelDimensions(document.getElementById('header')).totalHeight;
        const totalNzPaginHeight = UIUtils.getBoxModelDimensions(document.querySelector('nz-pagination.ant-pagination')).totalHeight;
        const totalntTableTHead = UIUtils.getBoxModelDimensions(document.querySelector('div.ant-table-header')).totalHeight;
        const paddingTopOfInnerContent = UIUtils.getBoxModelDimensions(document.querySelector('div.inner-content')).padding.top;
        const paddingBottomOfInnerContent = UIUtils.getBoxModelDimensions(document.querySelector('div.inner-content')).padding.bottom;
        this.tableScrollY = `${viewportHeight - totalHeaderHeight - totalNzPaginHeight - totalntTableTHead - paddingTopOfInnerContent - paddingBottomOfInnerContent - appHeaderHeight}px`;
        }, 1)
    }

    private listenWindowResize(): void {
        fromEvent(window, 'resize')
            .pipe(takeUntil(this.unsubscribe))
            .subscribe(() => {
            this.calculateTableScrollY();
            });
    }



}