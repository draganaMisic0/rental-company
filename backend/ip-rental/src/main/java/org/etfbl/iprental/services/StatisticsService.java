package org.etfbl.iprental.services;

import org.etfbl.iprental.models.RentalEntity;
import org.etfbl.iprental.repositories.RentalRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class StatisticsService {
    private final RentalRepository rentalRepository;

    public StatisticsService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public Map<LocalDate, BigDecimal> getDailyIncomeBetween(Instant startDate, Instant endDate) {
        List<RentalEntity> rentals = rentalRepository.findByDateAndTimeBetweenOrderByDateAndTimeAsc(startDate, endDate);

        Map<LocalDate, BigDecimal> dailyIncomeMap = new TreeMap<>(); // TreeMap keeps keys sorted

        for (RentalEntity rental : rentals) {
            LocalDate rentalDate = rental.getDateAndTime().atZone(ZoneId.systemDefault()).toLocalDate();
            dailyIncomeMap.put(rentalDate,
                    dailyIncomeMap.getOrDefault(rentalDate, BigDecimal.ZERO).add(rental.getTotalPrice()));
        }

        return dailyIncomeMap;
    }
}
