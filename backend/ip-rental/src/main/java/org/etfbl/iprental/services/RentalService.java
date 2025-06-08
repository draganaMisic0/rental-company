package org.etfbl.iprental.services;

import org.etfbl.iprental.models.ClientEntity;
import org.etfbl.iprental.models.DTO.RentalDTO;
import org.etfbl.iprental.models.RentalEntity;
import org.etfbl.iprental.models.VehicleEntity;
import org.etfbl.iprental.repositories.ClientRepository;
import org.etfbl.iprental.repositories.RentalRepository;
import org.etfbl.iprental.repositories.VehicleRepository;
import org.etfbl.iprental.utils.mappers.RentalMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final VehicleRepository vehicleRepository;
    private final ClientRepository clientRepository;
    private final RentalMapper rentalMapper;

    public RentalService(RentalRepository rentalRepository, VehicleRepository vehicleRepository, ClientRepository clientRepository, RentalMapper rentalMapper) {
        this.rentalRepository = rentalRepository;
        this.vehicleRepository = vehicleRepository;
        this.clientRepository = clientRepository;
        this.rentalMapper = rentalMapper;
    }

    public RentalDTO addRental(RentalDTO dto) {
        RentalEntity rental = rentalMapper.toEntity(dto);

        VehicleEntity vehicle = vehicleRepository.findById(dto.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found with ID: " + dto.getVehicleId()));
        ClientEntity client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found with ID: " + dto.getClientId()));

        rentalMapper.setVehicle(rental, vehicle);
        rentalMapper.setClient(rental, client);

        RentalEntity saved = rentalRepository.save(rental);
        return rentalMapper.toDto(saved);
    }

    public List<RentalDTO> getAllRentals() {
        return rentalRepository.findAll().stream()
                .map(rentalMapper::toDto)
                .sorted((rental1, rental2) -> {return rental2.getDateAndTime().compareTo(rental1.getDateAndTime());})
                .collect(Collectors.toList());
    }

    public RentalDTO getRentalById(Integer id) {
        RentalEntity rental = rentalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental not found with ID: " + id));
        return rentalMapper.toDto(rental);
    }

    public List<RentalDTO> getRentalsByVehicleId(String vehicle_id) {
        List<RentalEntity> rentals = rentalRepository.findAllByVehicleId(vehicle_id);

        List<RentalDTO> listToReturn= new ArrayList<RentalDTO>();
        for(RentalEntity rental : rentals)
        {
            listToReturn.add(rentalMapper.toDto(rental));
        }
        return listToReturn;
    }

    public RentalDTO updateRental(Integer id, RentalDTO dto) {
        RentalEntity existing = rentalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental not found with ID: " + id));

        existing.setDateAndTime(dto.getDateAndTime());
        existing.setStartLat(dto.getStartLat());
        existing.setStartLon(dto.getStartLon());
        existing.setEndLat(dto.getEndLat());
        existing.setEndLon(dto.getEndLon());
        existing.setDuration(dto.getDuration());
        existing.setTotalPrice(dto.getTotalPrice());

        if (dto.getVehicleId() != null) {
            VehicleEntity vehicle = vehicleRepository.findById(dto.getVehicleId())
                    .orElseThrow(() -> new RuntimeException("Vehicle not found with ID: " + dto.getVehicleId()));
            existing.setVehicle(vehicle);
        }

        if (dto.getClientId() != null) {
            ClientEntity client = clientRepository.findById(dto.getClientId())
                    .orElseThrow(() -> new RuntimeException("Client not found with ID: " + dto.getClientId()));
            existing.setClient(client);
        }

        RentalEntity saved = rentalRepository.save(existing);
        return rentalMapper.toDto(saved);
    }

    public List<RentalDTO> getLatestRentalsPerVehicle() {
        List<RentalEntity> foundEntities;
        try{
            foundEntities = rentalRepository.findLatestRentalsPerVehicle();
        }
        catch(Exception e){
            foundEntities = List.of();
        }

        List<RentalDTO> mappedEntities = new ArrayList<>();

        foundEntities.forEach(rentalEntity -> {
            mappedEntities.add(rentalMapper.toDto(rentalEntity));
        });

        return mappedEntities;
    }

    public void deleteRental(Integer id) {
        rentalRepository.deleteById(id);
    }

    public void deleteAllRentals() {
        rentalRepository.deleteAll();
    }
}
