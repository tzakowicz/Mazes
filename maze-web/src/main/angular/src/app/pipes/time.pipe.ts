import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'time'
})
export class TimePipe implements PipeTransform {

  transform(value: any, args?: any): any {
    const milliseconds = (value % 1000) / 100,
      seconds = Math.floor((value / 1000) % 60),
      minutes = Math.floor((value / (1000 * 60)) % 60),
      hours = Math.floor((value / (1000 * 60 * 60)) % 24);

    const sHours = (hours < 10) ? "0" + hours : hours;
    const sMinutes = (minutes < 10) ? "0" + minutes : minutes;
    const sSeconds = (seconds < 10) ? "0" + seconds : seconds;

    return sHours + ":" + sMinutes + ":" + sSeconds + "." + milliseconds;
  }

}
