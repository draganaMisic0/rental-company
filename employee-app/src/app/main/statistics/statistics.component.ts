import { Component, Inject, PLATFORM_ID, ViewChild } from '@angular/core';
import { StatisticsService } from '../../services/statistics.service';
import { MaterialModule } from '../../material/material-imports';
import { ChartConfiguration, ChartType } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';
import { MAT_DATE_FORMATS, MatNativeDateModule } from '@angular/material/core';
import { FormsModule } from '@angular/forms';
import { MatDatepicker } from '@angular/material/datepicker';
import { MONTH_YEAR_FORMATS } from '../../../config/date-formats';
import { isPlatformBrowser } from '@angular/common';

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

  

  lineChartData: ChartConfiguration<'line'>['data'] = {
    labels: [],
    datasets: [
      {
        data: [],
        label: 'Daily Income',
        fill: false,
        borderColor: '#3f51b5'
      }
    ]
  };

  lineChartOptions: ChartConfiguration<'line'>['options'] = {
    responsive: true,
    plugins: {
      legend: {
        display: true,
        position: 'bottom'
      }
    }
  };

  isBrowser = false;

constructor(@Inject(PLATFORM_ID) private platformId: Object, private statisticsService: StatisticsService) {
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

  noop(event: any): void {}

  private fetchDataForMonth(date: Date): void {
    const start = new Date(date.getFullYear(), date.getMonth(), 1);
    const end = new Date(date.getFullYear(), date.getMonth() + 1, 0);

      this.statisticsService.getDailyIncome(start, end).subscribe(data => {

    const allDates: string[] = [];
    for (let d = new Date(start); d <= end; d.setDate(d.getDate() + 1)) {
      const yyyy = d.getFullYear();
      const mm = (d.getMonth() + 1).toString().padStart(2, '0');
      const dd = d.getDate().toString().padStart(2, '0');
      allDates.push(`${yyyy}-${mm}-${dd}`);
    }
    
    // Fill missing dates with 0 values
    const filledData = allDates.map(dateStr => data[dateStr] ?? 0);
    
    // Update chart labels and data
    this.lineChartData.labels = allDates;
    this.lineChartData.datasets[0].data = filledData;
  });
  }
}
