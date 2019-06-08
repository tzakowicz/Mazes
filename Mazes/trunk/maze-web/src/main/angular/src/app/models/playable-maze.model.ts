import {Maze} from "./maze.model";
import {MazePosition} from "./maze-position.model";
import {MazeCell} from "./maze-cell.model";

export class PlayableMaze extends Maze {
  player: MazePosition;
  finish: MazePosition;

  handleMaze(maze) {
    this.width = maze.width;
    this.height = maze.height;
    this.rows = [];
    for(let i = this.height; i > 0; i--) {
      this.rows[i-1] = {cols: []};
      for (let j = 0; j < this.width; j++) {
        const row = this.height-i;
        const index = row*this.height + j;
        this.rows[i-1].cols[j] = new MazeCell(maze.cells[index]);
      }
    }
    this.setFinishPos(new MazePosition(maze.finishX, maze.finishY));
    this.setPlayerPos(new MazePosition(maze.playerX, maze.playerY));
  }

  setFinishPos(pos = this.finish) {
    this.clearPlayerPos();
    this.finish = pos;
    this.rows[this.height - 1 - this.finish.x].cols[this.finish.y].finish = true;
  }

  setPlayerPos(pos = this.player) {
    this.clearPlayerPos();
    this.player = pos;
    this.rows[this.height - 1 - this.player.x].cols[this.player.y].player = true;
  }

  clearPlayerPos() {
    this.rows.forEach(row => {
      row.cols.forEach(cell => {
        cell.player = false;
      })
    })
  }
}
