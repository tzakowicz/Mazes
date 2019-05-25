import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'

import {MazeCell} from '../models/maze-cell.model';
@Injectable(

)
export class MazeService {
  context = 'localhost:8080';
  url = '/mazes/maze/map/';

  constructor(private http: HttpClient) {
  }

  getMap(x = '20', y = '20') {
    return this.http.get<MazeCell[][]>(
      this.context + this.url,
      {
        params: {
          'height': y,
          'width': x
        },
        responseType: 'json'
      }
    );
  }
}
