import { LoginComponent } from "../pages/login/login.component";

export const AppConstants = {
  APPLICATION_NAME: "Point of Sale (POS)",
};

export interface RouteLink {
  path: string;
  link: string;
  component: string;
}

export const RouterConfig = {
  HOME: {path: '', link: '/'},
  LOGIN: {path: 'login', link: '/login', component: LoginComponent},
  PRODUCT: {path: 'product', link: '/product'}
}

