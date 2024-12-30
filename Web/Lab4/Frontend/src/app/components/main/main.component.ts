import { Component, OnInit } from '@angular/core';
import { PointService, PointResponse } from '../../services/point.service';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

interface TableRow {
  x: number;
  y: number;
  r: number;
  hitStatus: boolean;
  date: string;
}

@Component({
  selector: 'app-main',
  standalone: true,
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css'],
  imports: [CommonModule, FormsModule],
})
export class MainComponent implements OnInit {
  xValue = '';
  yValue: string | null = null;
  rValue: string = '1';
  pointsByR: Map<number, TableRow[]> = new Map();
  currentPoints: TableRow[] = [];
  yOptions: number[] = [-3, -2, -1, 0, 1, 2, 3, 4, 5];
  rOptions: number[] = [1, 1.5, 2, 2.5, 3];

  errorMessage: string = '';

  constructor(
      private pointService: PointService,
      private authService: AuthService,
      private router: Router
  ) {}

  ngOnInit(): void {
    this.loadRValue();
    this.fetchPointsFromServer();
  }

  onSelectY(value: number) {
    this.yValue = String(value);
  }

  onSelectR(value: number) {
    this.rValue = String(value);
    this.saveRValue();
    const r = parseFloat(this.rValue);
    this.currentPoints = this.pointsByR.get(r) || [];
    this.drawPoints();
  }

  isValidX(xValue: string): boolean {
    const numX = parseFloat(xValue);
    if (isNaN(numX)) {
      this.errorMessage = 'X должен быть числом.';
      return false;
    }
    if (numX < -3 || numX > 5) {
      this.errorMessage = 'X должен быть в диапазоне от -3 до 5.';
      return false;
    }
    if (numX === -3 || numX === 5) {
      if (xValue.includes('.')) {
        const decimalPart = xValue.split('.')[1];
        if (!/^[0]+$/.test(decimalPart)) {
          this.errorMessage = 'Для -3 и 5 после точки должны быть только нули.';
          return false;
        }
      }
    }
    this.errorMessage = '';
    return true;
  }

  onSubmit() {
    if (!this.isValidX(this.xValue) || !this.yValue || !this.rValue) {
      if (!this.errorMessage) {
        this.errorMessage = 'Заполните все поля: X, Y и R.';
      }
      return;
    }

    const xNum = parseFloat(this.xValue);
    const yNum = parseFloat(this.yValue);
    const rNum = parseFloat(this.rValue);

    this.pointService.addPoint(xNum, yNum, rNum).subscribe({
      next: (response: PointResponse) => {
        console.log('Response from addPoint:', response);

        if (
            response.x === undefined ||
            response.y === undefined ||
            response.r === undefined ||
            response.hitStatus === undefined ||
            (typeof response.date !== 'string' && !Array.isArray(response.date))
        ) {
          console.error('Некорректные данные точки:', response);
          this.errorMessage = 'Получены некорректные данные точки.';
          return;
        }

        const formattedDate = this.formatDate(response.date);
        if (formattedDate === 'Invalid Date') {
          console.error('Некорректная дата точки:', response.date);
          this.errorMessage = 'Некорректная дата точки.';
          return;
        }

        const newPoint: TableRow = {
          x: response.x,
          y: response.y,
          r: response.r,
          hitStatus: response.hitStatus,
          date: formattedDate,
        };

        if (!this.pointsByR.has(rNum)) {
          this.pointsByR.set(rNum, []);
        }
        this.pointsByR.get(rNum)?.push(newPoint);

        if (this.rValue === rNum.toString()) {
          this.currentPoints.push(newPoint);
          this.drawPoint(newPoint);
        }

        this.errorMessage = '';
      },
      error: (err) => {
        console.error('Ошибка при добавлении точки:', err);
        this.errorMessage = 'Ошибка при добавлении точки.';
      },
    });
  }

  onSvgClick(event: MouseEvent) {
    if (!this.rValue) {
      this.errorMessage = 'Сначала выберите R!';
      return;
    }

    const svg: SVGElement | null = event.currentTarget as SVGElement;
    if (!svg) {
      this.errorMessage = 'Ошибка обработки SVG.';
      return;
    }

    const rect = svg.getBoundingClientRect();
    const offsetX = event.clientX - rect.left;
    const offsetY = event.clientY - rect.top;

    const center = 210;
    const rNum = parseFloat(this.rValue);
    const scale = rNum / 140;


    const x = (offsetX - center) * scale;
    const y = (center - offsetY) * scale;

    if (isNaN(x) || isNaN(y)) {
      console.error('Некорректные координаты клика:', { x, y });
      this.errorMessage = 'Некорректные координаты клика.';
      return;
    }

    this.pointService.addPoint(x, y, rNum).subscribe({
      next: (response: PointResponse) => {
        console.log('Response from addPoint (SVG):', response);

        if (
            response.x === undefined ||
            response.y === undefined ||
            response.r === undefined ||
            response.hitStatus === undefined ||
            (typeof response.date !== 'string' && !Array.isArray(response.date))
        ) {
          console.error('Некорректные данные точки:', response);
          this.errorMessage = 'Получены некорректные данные точки.';
          return;
        }

        const formattedDate = this.formatDate(response.date);
        if (formattedDate === 'Invalid Date') {
          console.error('Некорректная дата точки:', response.date);
          this.errorMessage = 'Некорректная дата точки.';
          return;
        }

        const newPoint: TableRow = {
          x: response.x,
          y: response.y,
          r: response.r,
          hitStatus: response.hitStatus,
          date: formattedDate,
        };

        if (!this.pointsByR.has(rNum)) {
          this.pointsByR.set(rNum, []);
        }
        this.pointsByR.get(rNum)?.push(newPoint);

        if (this.rValue === rNum.toString()) {
          this.currentPoints.push(newPoint);
          this.drawPoint(newPoint);
        }

        this.errorMessage = '';
      },
      error: (err) => {
        console.error('Ошибка при добавлении точки через SVG:', err);
        this.errorMessage = 'Ошибка при добавлении точки.';
      },
    });
  }

  fetchPointsFromServer() {
    this.pointService.getAllPoints().subscribe({
      next: (points) => {
        console.log('Полученные точки:', points);
        if (!Array.isArray(points)) {
          console.error('Полученные точки не являются массивом:', points);
          this.errorMessage = 'Некорректные данные точек.';
          return;
        }
        this.pointsByR.clear();
        points.forEach((point) => {
          if (
              point.x === undefined ||
              point.y === undefined ||
              point.r === undefined ||
              point.hitStatus === undefined ||
              (typeof point.date !== 'string' && !Array.isArray(point.date))
          ) {
            console.error('Некорректная точка, пропущена:', point);
            return;
          }

          const formattedDate = this.formatDate(point.date);
          if (formattedDate === 'Invalid Date') {
            console.error('Некорректная дата точки, пропущена:', point.date);
            return;
          }

          const formattedPoint: TableRow = {
            x: point.x,
            y: point.y,
            r: point.r,
            hitStatus: point.hitStatus,
            date: formattedDate,
          };

          if (!this.pointsByR.has(formattedPoint.r)) {
            this.pointsByR.set(formattedPoint.r, []);
          }
          this.pointsByR.get(formattedPoint.r)?.push(formattedPoint);
        });

        const r = parseFloat(this.rValue);
        this.currentPoints = this.pointsByR.get(r) || [];
        this.drawPoints();
      },
      error: (err) => {
        console.error('Ошибка загрузки точек:', err);
        this.errorMessage = 'Ошибка загрузки точек.';
      },
    });
  }

  formatDate(date: string | number[]): string {
    console.log('formatDate called with:', date);
    if (!date) {
      return 'Invalid Date';
    }
    try {
      if (typeof date === 'string') {
        const truncatedDate = date.replace(/(\.\d{3})\d+/, '$1') + 'Z';
        const parsedDate = new Date(truncatedDate);
        console.log('Parsed Date (string):', parsedDate);
        if (isNaN(parsedDate.getTime())) {
          return 'Invalid Date';
        }
        return parsedDate.toLocaleString();
      } else if (Array.isArray(date)) {
        if (date.length < 7) {
          return 'Invalid Date';
        }
        const [year, month, day, hour, minute, second, millisecond] = date;
        console.log('Constructing date with:', year, month, day, hour, minute, second, millisecond);
        const ms = (millisecond && millisecond < 1000) ? millisecond : 0;
        console.log('Milliseconds set to:', ms);
        const constructedDate = new Date(year, month - 1, day, hour, minute, second, ms);
        console.log('Constructed Date:', constructedDate);
        if (isNaN(constructedDate.getTime())) {
          return 'Invalid Date';
        }
        return constructedDate.toLocaleString();
      } else {
        return 'Invalid Date';
      }
    } catch (error) {
      console.error('Error in formatDate:', error);
      return 'Invalid Date';
    }
  }

  drawPoints() {
    const svgGroup = document.getElementById('points-group');
    if (!svgGroup) {
      console.error('SVG группа points-group не найдена.');
      return;
    }

    svgGroup.innerHTML = '';
    this.currentPoints.forEach((point) => this.drawPoint(point));
  }

  drawPoint(point: TableRow) {
    if (
        point.x === undefined ||
        point.y === undefined ||
        point.r === undefined ||
        isNaN(point.x) ||
        isNaN(point.y) ||
        isNaN(point.r) ||
        point.r === 0
    ) {
      console.error('Некорректные данные точки для отрисовки:', point);
      return;
    }

    const svg = document.getElementById('main-svg');
    if (!svg) {
      console.error('SVG элемент main-svg не найден.');
      return;
    }

    const pointsGroup = document.getElementById('points-group');
    if (!pointsGroup) {
      console.error('SVG группа points-group не найдена.');
      return;
    }

    const centerX = 210;
    const centerY = 210;
    const scale = 140 / point.r;

    const pointX = centerX + point.x * scale;
    const pointY = centerY - point.y * scale;

    if (isNaN(pointX) || isNaN(pointY)) {
      console.error('Некорректные координаты точки:', { pointX, pointY });
      return;
    }

    const svgns = 'http://www.w3.org/2000/svg';
    const circle = document.createElementNS(svgns, 'circle');
    circle.setAttribute('cx', pointX.toString());
    circle.setAttribute('cy', pointY.toString());
    circle.setAttribute('r', '5');
    circle.setAttribute('fill', point.hitStatus ? 'green' : 'red');
    circle.setAttribute('stroke', 'black');
    pointsGroup.appendChild(circle);
  }

  saveRValue(): void {
    localStorage.setItem('currentR', this.rValue);
  }

  loadRValue(): void {
    const savedR = localStorage.getItem('currentR');
    if (savedR) {
      this.rValue = savedR;
    } else {
      this.rValue = '1';
      this.saveRValue();
    }
  }

  logout() {
    this.authService.logout();
    localStorage.removeItem('currentR');
    this.pointsByR.clear();
    this.currentPoints = [];
    this.router.navigate(['/login']);
  }
}
