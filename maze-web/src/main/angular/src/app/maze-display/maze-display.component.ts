import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
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
      this.getMaze('20', '20');
    } else if (this.size == SizeEnum.Large) {
      this.getMaze('30', '30');
    }
  }

  newGame() {
    this.event.emit(0);
  }

  getMaze(width, height) {
    console.log(`width: ${width}, height: ${height}`);
    this.mazeService.getMap(width, height)
      .subscribe(maze => {
          this.maze.handleMaze(maze);
        },
        err => {
          console.log(err);
        });
  }

  move(dir) {
    if (dir === 'u' || dir === 'd' || dir === 'l' || dir === 'r') {
      this.mazeService.move(dir)
        .subscribe(res => {
          this.maze.setPlayerPos(res);
        });
    }
  }
}
