import {Component, Input, OnInit} from '@angular/core';
import {MazeCell} from '../models/maze-cell.model';

@Component({
  selector: 'app-maze-display',
  templateUrl: './maze-display.component.html',
  styleUrls: ['./maze-display.component.css']
})
export class MazeDisplayComponent implements OnInit {

  @Input() private map: MazeCell[];

  set map(value: MazeCell[]) {
    this.map = value;
  }

  constructor() { }

  ngOnInit() {
  }

}
