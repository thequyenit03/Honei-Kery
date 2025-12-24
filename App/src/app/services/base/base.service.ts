import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { environment } from "../../../environments/environment.development";
import { catchError, MonoTypeOperatorFunction, of, tap } from "rxjs";
import { ApiResponse, ApiResponseList, ApiResponseListAll } from "../../models/api-response.models";

@Injectable({providedIn: 'root'})
export class BaseService {
    protected readonly http = inject(HttpClient);
    protected readonly notification = inject(NzNotificationService)

    protected readonly apiUrl = environment.ApiUrl;

    constructor(...args: unknown[]);

    constructor(){}

    private joinUrl(...parts: string[]) {
        return parts
            .map((p, i) => (i === 0 ? p.replace(/\/+$/g, '') : p.replace(/^\/+|\/+$/g, '')))
            .join('/');
    }

    private handleResponse<R extends {errorCode ?: string; message?: string}> (
        notify: boolean
    ): MonoTypeOperatorFunction<R> {
        return (source) => 
            source.pipe(
                tap((res: any) => {
                    if(!res || res.errorCode !== '200') {
                        if(notify) {
                            this.notification.error('<b>Lỗi</b>', res?.message ?? 'Unknown error');
                        }
                        return;
                    }

                    if(notify) {
                        this.notification.success('<b>Thành công </b>', res.message);
                    }
                }),

                catchError((err) => {
                    const apiError = {
                        datetime: new Date().toISOString(),
                        errorCode: err?.error?.errorCode ?? '500',
                        message: 
                            err?.error?.message ??
                            err?.message ??
                            'Đã có lỗi xảy ra',
                        data: null,
                        success: false
                    };
                    
                    if(notify) {
                        this.notification.error('<b>Lỗi</b>', apiError.message);
                    }
                    return of(apiError as unknown as R);
                })
            );
    }

    protected get<T>(endpoint: string, params?: any, notification = false) {
        const url = this.joinUrl(this.apiUrl, endpoint);
        return this.http.get<ApiResponse<T>>(url, { params }).pipe(this.handleResponse(notification));
    }

    protected post<T>(endpoint: string, body?: any, params?: any, notification = true) {
        const url = this.joinUrl(this.apiUrl, endpoint);
        return this.http.post<ApiResponse<T>>(url, body, { params }).pipe(this.handleResponse(notification));
    }

    protected put<T>(endpoint: string, body?: any, notification = false) {
        const url = this.joinUrl(this.apiUrl, endpoint);
        return this.http.put<ApiResponse<T>>(url, body).pipe(this.handleResponse(notification));
    }

    protected deleteItem<T>(endpoint: string, notification = false) {
        const url = this.joinUrl(this.apiUrl, endpoint);
        return this.http.delete<ApiResponse<T>>(url).pipe(this.handleResponse(notification));
    }

    protected postList<T>(endpoint: string, body?: any, params?: any, notification = false) {
        const url = this.joinUrl(this.apiUrl, endpoint);
        return this.http.post<ApiResponseList<T>>(url, body, { params }).pipe(this.handleResponse(notification));
    }

    protected postListAll<T>(endpoint: string, body?: any, params?: any, notification = false) {
        const url = this.joinUrl(this.apiUrl, endpoint);
        return this.http.post<ApiResponseListAll<T>>(url, body, { params }).pipe(this.handleResponse(notification));
    }

}