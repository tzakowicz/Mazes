import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {SizeEnum} from "../models/size.enum";

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  @Output() event = new EventEmitter;
  size: SizeEnum;

  constructor() { }

  ngOnInit(): void {
    this.size = SizeEnum.Medium;
  }

  startPlaying() {
    this.event.emit(this.size);
  }
}
