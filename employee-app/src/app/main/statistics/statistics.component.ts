  import { Component, Inject, NgZone, PLATFORM_ID, ViewChild } from '@angular/core';
  import { StatisticsService } from '../../services/statistics.service';
  import { MaterialModule } from '../../material/material-imports';
  import { ChartConfiguration, ChartType } from 'chart.js';
  import { BaseChartDirective } from 'ng2-charts';
  import { MAT_DATE_FORMATS, MatNativeDateModule } from '@angular/material/core';
  import { FormsModule } from '@angular/forms';
  import { MatDatepicker } from '@angular/material/datepicker';
  import { MONTH_YEAR_FORMATS } from '../../../config/date-formats';
  import { isPlatformBrowser } from '@angular/common';
  import { ChangeDetectorRef } from '@angular/core';

  @Component({
    selector: 'app-statistics',
    imports: [MaterialModule, FormsModule, BaseChartDirective, MatNativeDateModule],
    templateUrl: './statistics.component.html',
    styleUrl: './statistics.component.css',
    providers:[
      StatisticsService,
      { provide: MAT_DATE_FORMATS, useValue: MONTH_YEAR_FORMATS }
    ]
  })
  export class StatisticsComponent {
    selectedDate = new Date(); // Defaults to current month

    @ViewChild('picker') picker!: MatDatepicker<Date>;
    @ViewChild(BaseChartDirective) chart?: BaseChartDirective;
    

    

    lineChartData1: ChartConfiguration<'line'>['data'] = {
      labels: [],
      datasets: [
        {
          data: [],
          backgroundColor: '#248532',
          label: 'Daily Income',
          fill: true,
          borderColor: '#3f51b5',
        }
      ]

    };

     chartData2: ChartConfiguration<'line'>['data'] = {
      labels: [],
      datasets: [
        {
          data: [],
          backgroundColor: 'purple',
          label: 'Daily Income',
          fill: true,
          borderColor: '#3f51b5',
        }
      ]

    };

    lineChartOptions1: ChartConfiguration<'line'>['options'] = {
  responsive: true,
  plugins: {
    legend: {
      display: true,
      position: 'bottom',
    },
    title: {
      display: true,
      text: 'Daily Income per Month',
      fullSize: true,
    },
  },
  scales: {
    y: {
      beginAtZero: true,
      suggestedMax: 200, // or dynamically set this if needed
      ticks: {
        stepSize: 20,
        precision: 0
      }
    },
    x: {
      ticks: {
        autoSkip: true,
        maxRotation: 45,
        minRotation: 45
      }
    }
  }
};

   chartOptions2: ChartConfiguration<'line'>['options'] = {
  responsive: true,
  plugins: {
    legend: {
      display: true,
      position: 'bottom',
    },
    title: {
      display: true,
      text: 'Daily Income per Month',
      fullSize: true,
    },
  },
  scales: {
    y: {
      beginAtZero: true,
      suggestedMax: 200, // or dynamically set this if needed
      ticks: {
        stepSize: 20,
        precision: 0
      }
    },
    x: {
      ticks: {
        autoSkip: true,
        maxRotation: 45,
        minRotation: 45
      }
    }
  }
};

    isBrowser = false;

  constructor(
    @Inject(PLATFORM_ID) private platformId: Object, 
    private statisticsService: StatisticsService,
    private cdr: ChangeDetectorRef,
    private zone: NgZone
  ) {
    this.isBrowser = isPlatformBrowser(platformId);
  }

    ngOnInit(): void {
      this.fetchDataForMonth(this.selectedDate);
    }

    onMonthChanged(event: any): void {
      const newDate = new Date(event);
      this.fetchDataForMonth(newDate);
    }

    onMonthSelected(normalizedMonth: Date, datepicker: MatDatepicker<Date>): void {
      this.selectedDate = new Date(normalizedMonth.getFullYear(), normalizedMonth.getMonth(), 1);
      this.fetchDataForMonth(this.selectedDate);
      
      datepicker.close();
    }


      private fetchDataForMonth(date: Date): void {
      const year = date.getFullYear();
      const month = date.getMonth();

      const start = new Date(year, month, 1);
      const end = new Date(year, month + 1, 0);

      this.statisticsService.getDailyIncome(start, end).subscribe(data => {
        const allDates: string[] = [];
        const chartData: number[] = [];

        for (let day = 1; day <= end.getDate(); day++) {
          const yyyy = year;
          const mm = (month + 1).toString().padStart(2, '0');
          const dd = day.toString().padStart(2, '0');
          const dateStr = `${yyyy}-${mm}-${dd}`;

          allDates.push(dateStr);
          chartData.push(data[dateStr] ?? 0);
          this.cdr.detectChanges();
        }

        this.lineChartData1.labels = allDates;
        this.lineChartData1.datasets[0].data = chartData;

        
          if (this.chart) {
            setTimeout(() => this.chart?.update(), 200);
            setTimeout(() => this.cdr.detectChanges(), 201);
          }
          
      });
    }
  }
