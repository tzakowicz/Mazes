import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MazeMenuComponent } from './maze-menu.component';

describe('MazeMenuComponent', () => {
  let component: MazeMenuComponent;
  let fixture: ComponentFixture<MazeMenuComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MazeMenuComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MazeMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
