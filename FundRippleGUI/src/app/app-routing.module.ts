import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomePageComponent } from './components/home-page/home-page.component';
import { LoginPageComponent } from './components/login-page/login-page.component';
import { RegisterPageComponent } from './components/register-page/register-page.component';
import { CreateProjectComponent } from './components/create-project/create-project.component';
import { SearchProjectComponent } from './components/search-project/search-project.component';
import { SpecificProjectComponent } from './components/specific-project/specific-project.component';
import { UserGuardService } from './services/user-guard.service';
import { ProfileComponent } from './components/profile/profile.component';
import { PaymentComponent } from './components/payment/payment.component';
import { Page404Component } from './components/page-404/page-404.component';

const routes: Routes = [
  {path:'home', component:HomePageComponent},
  {path:'', redirectTo: '/home', pathMatch: 'full' },
  {path:'login', component:LoginPageComponent},
  {path:'register', component:RegisterPageComponent},
  {path:'register/admin', component:RegisterPageComponent,canActivate:[UserGuardService]},
  {path:'createProject',component:CreateProjectComponent,canActivate:[UserGuardService]},
  {path:'search',component:SearchProjectComponent},
  {path:'list/suspicion',component:SearchProjectComponent,canActivate:[UserGuardService]},
  {path:'list/verify',component:SearchProjectComponent,canActivate:[UserGuardService]},
  {path:'project/:projectName',component:SpecificProjectComponent},
  {path:'profile/:userName',component:ProfileComponent,canActivate:[UserGuardService]},
  {path:'payment/:status/:projectName/:amount',component:PaymentComponent},
  { path: '**', component: Page404Component },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
