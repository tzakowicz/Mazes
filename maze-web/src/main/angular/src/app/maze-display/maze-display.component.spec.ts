import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MazeDisplayComponent } from './maze-display.component';

describe('MazeDisplayComponent', () => {
  let component: MazeDisplayComponent;
  let fixture: ComponentFixture<MazeDisplayComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MazeDisplayComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MazeDisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
