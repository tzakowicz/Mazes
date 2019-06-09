import { BrowserModule } from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from "@angular/forms";

import { ButtonModule } from "primeng/button";
import { RadioButtonModule } from "primeng/primeng";
import { DialogModule } from 'primeng/dialog';

import { AppComponent } from './app.component';
import { TimePipe } from './pipes/time.pipe';
import { MazeDisplayComponent } from './maze-display/maze-display.component';
import { MazeMenuComponent } from './menu/maze-menu.component';

@NgModule({
  declarations: [
    AppComponent,
    MazeDisplayComponent,
    MazeMenuComponent,
    TimePipe
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    ButtonModule,
    RadioButtonModule,
    FormsModule,
    DialogModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
