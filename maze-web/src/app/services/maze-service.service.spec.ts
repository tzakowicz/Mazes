import { TestBed } from '@angular/core/testing';

import { MazeServiceService } from './maze-service.service';

describe('MazeServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: MazeServiceService = TestBed.get(MazeServiceService);
    expect(service).toBeTruthy();
  });
});
