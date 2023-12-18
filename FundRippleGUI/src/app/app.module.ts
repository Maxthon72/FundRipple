import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HomePageComponent } from './components/home-page/home-page.component';
import { LoginPageComponent } from './components/login-page/login-page.component';
import { RegisterPageComponent } from './components/register-page/register-page.component';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import {FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { MatInputModule } from '@angular/material/input'; // Import MatInputModule for input fields
import { MatFormFieldModule } from '@angular/material/form-field'; // Import MatFormFieldModule for form fields
import { MatButtonModule } from '@angular/material/button';
import { CreateProjectComponent } from './components/create-project/create-project.component';
import { SpecificProjectComponent } from './components/specific-project/specific-project.component';
import { UserPageComponent } from './components/user-page/user-page.component'; // Import MatButtonModule for buttons
import { MatChipsModule } from '@angular/material/chips';
import { VerifyProjectComponent } from './components/verify-project/verify-project.component';
import { SuspicionsProjectComponent } from './components/suspicions-project/suspicions-project.component';
import { SearchProjectComponent } from './components/search-project/search-project.component';
import {MatCardModule } from '@angular/material/card';
import { MatListModule } from '@angular/material/list';
import { MatProgressBarModule } from '@angular/material/progress-bar';

@NgModule({
  declarations: [
    AppComponent,
    HomePageComponent,
    LoginPageComponent,
    RegisterPageComponent,
    CreateProjectComponent,
    SpecificProjectComponent,
    UserPageComponent,
    VerifyProjectComponent,
    SuspicionsProjectComponent,
    SearchProjectComponent
  ],
  imports: [
    MatProgressBarModule,
    MatListModule,
    MatCardModule,
    MatChipsModule,
    MatIconModule,
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    HttpClientModule,
    FormsModule,
    MatButtonModule,
    MatInputModule,
    MatFormFieldModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
