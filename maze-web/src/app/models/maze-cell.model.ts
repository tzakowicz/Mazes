export class MazeCell {
  x: number;
  y: number;
  north: boolean;
  south: boolean;
  east: boolean;
  west: boolean;

  constructor(x, y, north, south, east, west) {
    this.x = x;
    this.y = y;
    this.north = north;
    this.south = south;
    this.east = east;
    this.west = west;
  }
}
