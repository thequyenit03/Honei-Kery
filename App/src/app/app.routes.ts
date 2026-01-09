import { Routes } from '@angular/router';
import { Layout } from './pages/client/layout/layout';
import { Login } from './pages/client/login/login';
import { authGuard } from './guards/auth.guard';

export const routes: Routes = [
    { path: '', pathMatch: 'full', redirectTo: 'login'},
    { path: 'login', component: Login},
    { path: '',
        canActivate: [authGuard],
        canActivateChild: [authGuard],
        component: Layout,
        children: [
            {path: 'home',
                loadComponent: () => import('./pages/client/home/home').then(m => m.Home)
            },
            {path: 'menu',
                children: [
                    {path: 'coffee',
                        loadComponent: () => import('./pages/client/menu/coffee/coffee').then(m => m.Coffee)
                    },
                    {path: 'tea',
                        loadComponent: () => import('./pages/client/menu/tea/tea').then(m => m.Tea)
                    },
                    {path: 'freeze',
                        loadComponent: () => import('./pages/client/menu/freeze/freeze').then(m => m.Freeze)
                    },
                    {path: 'sweet-cake',
                        loadComponent: () => import('./pages/client/menu/sweet-cake/sweet-cake').then(m => m.SweetCake)
                    },
                ]

            }
        ]

    }
];
