import {Component, OnInit} from '@angular/core';
import {MazeService} from "../services/maze.service";
import {Maze} from "../models/maze.model";

@Component({
  selector: 'app-maze-display',
  templateUrl: './maze-display.component.html',
  styleUrls: ['./maze-display.component.css'],
  providers: [MazeService]
})
export class MazeDisplayComponent implements OnInit {

  width: number;
  height: number;
  maze: Maze;
  rows;

  constructor(private mazeService: MazeService) { }

  ngOnInit() {
    this.getInitialMap();
  }

  getInitialMap() {
    this.mazeService.getMap()
      .subscribe(maze => {
        console.log(maze);
        this.maze = maze;
        this.setUpMaze();
        this.handleMaze();
      },
      err => {
        console.log(err);
      });
  }

  setUpMaze() {
    this.width = this.maze.width;
    this.height = this.maze.height;
    this.rows = [];
    for(var i = 0; i < this.height; i++) {
      this.rows[i] =  {cols: []};
      this.rows[i].cols = [];
    }
  }

  getMap() {
    this.mazeService.getMap()
      .subscribe(maze => {
          this.maze = maze;
          this.handleMaze();
        },
        err => {
          console.log(err);
        });
  }

  handleMaze() {
    for(var i = this.height; i > 0; i--) {
      for (var j = 0; j < this.width; j++) {
        const row = this.height-i;
        const index = row*this.height + j;
        this.rows[i-1].cols[j] = this.maze.cells[index];
      }
    }
  }

  newGame() {
    this.mazeService.newGame().subscribe(res => this.getMap());
  }

  move(dir) {
    if (dir === 'u' || dir === 'd' || dir === 'l' || dir === 'r') {
      this.mazeService.move(dir).subscribe(res => this.getMap());
    }
  }
}
