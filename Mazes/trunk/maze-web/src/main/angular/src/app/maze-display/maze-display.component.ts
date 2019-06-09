import {Component, EventEmitter, HostListener, Input, OnInit, Output} from '@angular/core';

import {MazeService} from "../services/maze.service";
import {PlayableMaze} from "../models/playable-maze.model";
import {SizeEnum} from "../models/size.enum";

@Component({
  selector: 'app-maze-display',
  templateUrl: './maze-display.component.html',
  styleUrls: ['./maze-display.component.css'],
  providers: [MazeService]
})
export class MazeDisplayComponent implements OnInit {
  maze = new PlayableMaze;
  @Output() event = new EventEmitter;
  @Input() size: SizeEnum;

  constructor(private mazeService: MazeService) {}

  ngOnInit() {
    if (this.size == SizeEnum.Small) {
      this.getMaze('10', '10');
    } else if (this.size == SizeEnum.Medium) {
      this.getMaze('20', '15');
    } else if (this.size == SizeEnum.Large) {
      this.getMaze('30', '20');
    }
  }

  getMaze(width, height) {
    this.mazeService.getMap(width, height)
      .subscribe(maze => {
          this.maze.handleMaze(maze);
        },
        err => {
          console.log(err);
        });
  }

  @HostListener('document:keydown', ['$event'])
  handleKeyboardEvent(event: KeyboardEvent) {
    if (event.key === 'w' || event.key === 'ArrowUp') {
      event.preventDefault();
      this.move('u');
    }
    if (event.key === 'a' || event.key === 'ArrowLeft') {
      event.preventDefault();
      this.move('l');
    }
    if (event.key === 's' || event.key === 'ArrowDown') {
      event.preventDefault();
      this.move('d');
    }
    if (event.key === 'd' || event.key === 'ArrowRight') {
      event.preventDefault();
      this.move('r');
    }
  }

  move(dir) {
    if (dir === 'u' || dir === 'd' || dir === 'l' || dir === 'r') {
      this.mazeService.move(dir)
        .subscribe(res => {
          if (res.time > 0) {
            this.event.emit(res.time);
          } else {
            this.maze.setPlayerPos(res);
            this.scrollToCell(res.x, res.y);
          }
        });
    }
  }

  scrollToCell(x, y) {
    const cellX = this.maze.height - 1 - x;
    const cell = document.getElementById( `row-${cellX}-col-${y}`);
    const cellCenter = cell.offsetTop;
    const halfWindowHeight = (window.innerHeight / 2 );
    const target = cellCenter - halfWindowHeight;
    window.scrollTo(0, target);
  }
}
