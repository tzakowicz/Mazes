import { BrowserModule } from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { MazeDisplayComponent } from './maze-display/maze-display.component';
import {ButtonModule} from "primeng/button";
import { MenuComponent } from './menu/menu.component';
import {RadioButtonModule} from "primeng/primeng";
import {FormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    AppComponent,
    MazeDisplayComponent,
    MenuComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    ButtonModule,
    RadioButtonModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
