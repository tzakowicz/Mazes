import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'

import {MazeCell} from '../models/maze-cell.model';
import {map} from "rxjs/operators";
@Injectable(

)
export class MazeService {
  context = 'localhost:8080';
  url = '/mazes/maze/map/';

  constructor(private http: HttpClient) {
  }

  getMap(url = this.context + this.url, x = '20', y = '20') {
    return this.http.get<Maze>(
      url,
      {
        params: {
          'height': y,
          'width': x
        },
        responseType: 'json'
      }
    ).pipe(map(maze => maze.cells));
  }
}

export class Maze {
  public cells: MazeCell[];
}
