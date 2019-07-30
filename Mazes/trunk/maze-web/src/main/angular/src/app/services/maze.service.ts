import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'

import {MazePosition} from "../models/maze-position.model";

@Injectable(

)
export class MazeService {
  url = 'http://www.gilmor.net/mazes/maze/game/';

  constructor(private http: HttpClient) {
  }

  getMap(width: string = '20', height: string = '20', url = this.url) {
    return this.http.get(
      url,
      {
        params: {
          'height': height,
          'width': width
        },
        responseType: 'json',
        withCredentials: true
      }
    );
  }

  move(dir) {
    return this.http.get<MazePosition>(
      this.url + dir,
      {
        withCredentials:true,
        headers: {'Content-Type': 'text/plain'}
      });
  }
}
