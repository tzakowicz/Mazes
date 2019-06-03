export class MazeCell {
  x: number;
  y: number;
  player: boolean;
  cellType: number;

  constructor(x, y, player, cellType) {
    this.x = x;
    this.y = y;
    this.player = player;
    this.cellType = cellType;
  }
}
