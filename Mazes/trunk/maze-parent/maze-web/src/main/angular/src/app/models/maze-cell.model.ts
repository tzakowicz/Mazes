export class MazeCell {
  x: number;
  y: number;
  cellType: number;
  player: boolean;
  finish: boolean;

  constructor(cell) {
    this.x = cell.x;
    this.y = cell.y;
    this.cellType = cell.cellType;
    this.player = false;
    this.finish = false;
  }
}
