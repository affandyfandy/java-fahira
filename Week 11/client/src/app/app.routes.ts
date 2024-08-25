import { Routes } from '@angular/router';
import { RouterConfig } from './config/app.constant';

export const routes: Routes = [
  {
    path: RouterConfig.HOME.path,
    redirectTo: RouterConfig.LOGIN.path,
    pathMatch: 'full'
  },
  {
    path: RouterConfig.LOGIN.path,
    component: RouterConfig.LOGIN.component
  },
  {
    path: RouterConfig.PRODUCT.path,
    loadChildren: () =>
      import('./pages/product/product.routes')
        .then(m => m.productRoutes)
  }
];
