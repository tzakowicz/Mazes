import { Component } from '@angular/core';
import {MazeService} from "./services/maze.service";

import {MazeCell} from "./models/maze-cell.model";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [MazeService]
})
export class AppComponent {

  status: string = 'waiting';
  defaultCellMap: MazeCell[][];

  constructor(private mazeService: MazeService) {
    this.getDefaultMap();
  }

  getDefaultMap() {
    this.status = 'called';
    this.mazeService.getMap()
      .subscribe( cells => {
        cells.forEach(cellRow => {
          console.log(cellRow);
        });
        this.status = 'finished';
      });
  }
}
