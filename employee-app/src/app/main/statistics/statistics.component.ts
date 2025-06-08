  import { AfterViewInit, Component, Inject, NgZone, PLATFORM_ID, QueryList, ViewChild, ViewChildren } from '@angular/core';
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
  export class StatisticsComponent{
    selectedDate = new Date(); // Defaults to current month

    @ViewChild('picker') picker!: MatDatepicker<Date>;
    @ViewChildren(BaseChartDirective) charts!: QueryList<BaseChartDirective>;

    

    

    chartData1: ChartConfiguration<'line'>['data'] = {
      labels: [],
      datasets: [
        {
          data: [],
          label: 'Income',
          fill: true,
          tension: 0.2,
          backgroundColor: '#609F6DA0',
          borderColor: '#56a991FA',
          pointBackgroundColor: '#248532',
          pointRadius: 3,
          pointHoverRadius: 7,
        }
      ]

    };

     chartData2: ChartConfiguration<'bar'>['data'] = {
      labels: [],
      datasets: [
        {
          data: [],
          label: 'Malfunctions',
          backgroundColor: '#9C27B0AE',  
          borderColor: '#7B1FA2',
          borderWidth: 2,
          hoverBackgroundColor: '#7B1FA2', 
          hoverBorderColor: '#9C27B0FF',
          barThickness: "flex",
          borderRadius: 4,
        }
      ]
    };

    chartData3: ChartConfiguration<'doughnut'>['data'] = {
      labels: [],
      datasets: [
        {
          data: [],
          label: 'Income by Vehicle Type',
          backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56'],
          borderColor: '#ffffff',
          borderWidth: 2
        }
      ]
    };

  chartOptions1: ChartConfiguration<'line'>['options'] = {
      responsive: true,
      maintainAspectRatio: false,
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
          suggestedMax: 200,
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

   chartOptions2: ChartConfiguration<'bar'>['options'] = {
      responsive: true,
      maintainAspectRatio: false,
      plugins: {
        legend: {
          display: true,
          position: 'bottom',
        },
        title: {
          display: true,
          text: 'Malfunctions Per Vehicle',
          fullSize: true,
        },
      },
      scales: {
        y: {
          beginAtZero: true,
          suggestedMax: 20,
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

    chartOptions3: ChartConfiguration<'doughnut'>['options'] = {
      responsive: true,
      plugins: {
        legend: {
          display: true,
          position: 'right',
          labels: {
            boxWidth: 20,
            padding: 20,
          }
        },
        title: {
          display: true,
          text: 'Income Per Vehicle Type',
          fullSize: true,
        }
      },
      maintainAspectRatio: false // Allow custom height
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
      this.fetchDataForVehicleMalfunctions();
      this.fetchIncomePerVehicleType();

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

        this.chartData1.labels = allDates;
        this.chartData1.datasets[0].data = chartData;

        
          if (this.charts) {
            setTimeout(() => this.refreshCharts(), 0);
            setTimeout(() => this.cdr.detectChanges(), 1);
          }
          
      });
    }

    private fetchDataForVehicleMalfunctions(): void {
          this.statisticsService.getVehicleMalfunctions().subscribe((data: Record<string, number>) => {
            const allCars: string[] = [];
            const chartData: number[] = [];

            for (const [car, count] of Object.entries(data)) {
              allCars.push(car);
              chartData.push(count);   
            }

        this.chartData2.labels = allCars;
        this.chartData2.datasets[0].data = chartData;

        if (this.charts) {
          setTimeout(() => this.refreshCharts(), 0);
          setTimeout(() => this.cdr.detectChanges(), 1);
        }
        });
      }

      private fetchIncomePerVehicleType(): void {
        this.statisticsService.getIncomePerVehicleType().subscribe((data: Record<string, number>) => {
          const vehicleTypes: string[] = [];
          const incomes: number[] = [];

          for (const [type, income] of Object.entries(data)) {
            vehicleTypes.push(type);
            incomes.push(income);
          }

          this.chartData3.labels = vehicleTypes;
          this.chartData3.datasets[0].data = incomes;

          if (this.charts) {
            setTimeout(() => this.refreshCharts(), 0);
            setTimeout(() => this.cdr.detectChanges(), 1);
          }
        });
      }

      private refreshCharts(){
        this.charts.forEach((child: BaseChartDirective) => {
            child.chart?.update();
          });
      }
}
