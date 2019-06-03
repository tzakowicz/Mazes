import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'

import {Maze} from "../models/maze.model";

@Injectable(

)
export class MazeService {
  context = 'http://www.gilmor.net';
  url = '/mazes/maze/game/';

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
        responseType: 'json',
        withCredentials:true
      }
    );
  }

  newGame() {
    return this.getDefault("new");
  }

  move(dir) {
    return this.getDefault(dir);
  }

  getDefault(endpoint) {
    return this.http.get(
      this.context + this.url + endpoint,
      {
        withCredentials:true,
        headers: {'Content-Type': 'text/plain'}
      });
  }
}
