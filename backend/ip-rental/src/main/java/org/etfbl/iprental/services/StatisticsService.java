package org.etfbl.iprental.services;

import org.etfbl.iprental.models.MalfunctionEntity;
import org.etfbl.iprental.models.RentalEntity;
import org.etfbl.iprental.models.VehicleEntity;
import org.etfbl.iprental.repositories.MalfunctionRepository;
import org.etfbl.iprental.repositories.RentalRepository;
import org.etfbl.iprental.repositories.VehicleRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class StatisticsService {
    private final RentalRepository rentalRepository;
    private final VehicleRepository vehicleRepository;
    private final MalfunctionRepository malfunctionRepository;

    public StatisticsService(
            RentalRepository rentalRepository,
            VehicleRepository vehicleRepository,
            MalfunctionRepository malfunctionRepository
    ) {
        this.rentalRepository = rentalRepository;
        this.vehicleRepository = vehicleRepository;
        this.malfunctionRepository = malfunctionRepository;
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

    public Map<String, Integer> getMalfunctionCountPerVehicle() {
        List<VehicleEntity> allVehicles = vehicleRepository.findAll();
        List<MalfunctionEntity> allMalfunctions = malfunctionRepository.findAll();

        // Count malfunctions per vehicle ID
        Map<String, Integer> vehicleIdToCount = new HashMap<>();
        for (MalfunctionEntity malfunction : allMalfunctions) {
            String vehicleId = malfunction.getVehicle().getId();
            vehicleIdToCount.put(vehicleId, vehicleIdToCount.getOrDefault(vehicleId, 0) + 1);
        }

        // Create final map: key = model, value = number of malfunctions (even 0)
        Map<String, Integer> modelToCountMap = new TreeMap<>();
        for (VehicleEntity vehicle : allVehicles) {
            String shortId = vehicle.getId().substring(0, 4);

            String model = vehicle.getModel() + " (#" + shortId + ")" ;
            int count = vehicleIdToCount.getOrDefault(vehicle.getId(), 0);
            modelToCountMap.put(model, count);
        }

        return modelToCountMap;
    }
}
