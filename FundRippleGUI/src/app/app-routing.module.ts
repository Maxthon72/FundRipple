import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomePageComponent } from './components/home-page/home-page.component';
import { LoginPageComponent } from './components/login-page/login-page.component';
import { RegisterPageComponent } from './components/register-page/register-page.component';
import { CreateProjectComponent } from './components/create-project/create-project.component';
import { SearchProjectComponent } from './components/search-project/search-project.component';
import { SuspicionsProjectComponent } from './components/suspicions-project/suspicions-project.component';
import { VerifyProjectComponent } from './components/verify-project/verify-project.component';
import { SpecificProjectComponent } from './components/specific-project/specific-project.component';

const routes: Routes = [
  {path:'home', component:HomePageComponent},
  {path:'', redirectTo: '/home', pathMatch: 'full' },
  {path:'login', component:LoginPageComponent},
  {path:'register', component:RegisterPageComponent},
  {path:'createProject',component:CreateProjectComponent},
  {path:'search',component:SearchProjectComponent},
  {path:'suspicions',component:SuspicionsProjectComponent},
  {path:'verify',component:VerifyProjectComponent},
  {path:'project/:projectName',component:SpecificProjectComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
