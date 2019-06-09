import { Component } from '@angular/core';

import { SizeEnum } from "./models/size.enum";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  playing = false;
  time = 0;
  popup = false;
  size: SizeEnum;
  constructor() {}

  startPlaying(size) {
    this.size = size;
    this.playing = true;
  }

  newGame(event) {
    if (event > 0) {
      this.time = event;
      this.popup = true;
    }
    this.playing = false;
  }
}
