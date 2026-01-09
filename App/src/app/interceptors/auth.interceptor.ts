import { HttpEvent, HttpHandlerFn, HttpRequest } from "@angular/common/http";
import { inject } from "@angular/core";
import { catchError, Observable, throwError } from "rxjs";
import { Auth } from "../services/auth";
import { authStore } from "../store/auth.store";

export const AuthInterceptor = (req: HttpRequest<any>, next: HttpHandlerFn): Observable<HttpEvent<any>> => {
    const token = authStore.getValue().token;
    const authService = inject(Auth);

    if (req.headers.get('auth') === 'false') {
      const customReq = req.clone({
        headers: req.headers.delete('auth')
      });
      return next(customReq);
    }

    if (token) {
      const authReq = req.clone({
        setHeaders: {
          Authorization: token,
        },
      });
      return next(authReq).pipe(
        catchError(error => {
          if (error.status === 401) {
            authService.logout();
          }
          return throwError(() => error);
        })
      );
    }
    return next(req);
};