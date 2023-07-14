package io.codelex.flightplanner.service;

import io.codelex.flightplanner.dtos.FlightSearchDTO;
import io.codelex.flightplanner.exceptions.AirportDateMismatchException;
import io.codelex.flightplanner.exceptions.DuplicateEntryException;
import io.codelex.flightplanner.exceptions.EqualAirportsException;
import io.codelex.flightplanner.exceptions.FlightNotFoundByIdException;
import io.codelex.flightplanner.model.Airport;
import io.codelex.flightplanner.model.Flight;
import io.codelex.flightplanner.repository.FlightRepository;
import io.codelex.flightplanner.responses.FlightSearchResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class FlightService {

    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public void addFlight(Flight flight) {
        validateAirportsAndDates(flight);
        flight.setId(UUID.randomUUID().toString());
        if (flightRepository.getFlights().contains(flight)) {
            throw new DuplicateEntryException();
        }
        flightRepository.add(flight);
    }

    private void validateAirportsAndDates(Flight flight) {
        validateAirports(flight.getFrom(), flight.getTo());
        if (flight.getDepartureTime().isAfter(flight.getArrivalTime())
                || flight.getDepartureTime().isEqual(flight.getArrivalTime())) {
            throw new AirportDateMismatchException();
        }
    }

    public FlightSearchResponse searchForFlight(FlightSearchDTO searchDTO) {
        validateAirports(searchDTO.getFrom(), searchDTO.getTo());
        List<Flight> flights = searchForAirports(searchDTO.getFrom(), searchDTO.getTo(), searchDTO.getBaseDate());
        return new FlightSearchResponse(flights, 0, flights.size());
    }


    public <T> void validateAirports(T airportFrom, T airportTo) {
        if (airportTo.equals(airportFrom)) {
            throw new EqualAirportsException();
        }
    }

    public List<Flight> searchForAirports(String from, String to, LocalDate departureDate) {
        return flightRepository.getFlights().stream()
                .filter(flightDTO -> flightDTO.getDepartureTime().toLocalDate().equals(departureDate))
                .filter(flightDTO -> flightDTO.getFrom().getAirport().equals(from))
                .filter(flightDTO -> flightDTO.getTo().getAirport().equals(to))
                .toList();
    }

    public void deleteFlightById(String id) {
        synchronized (flightRepository.getFlights()) {
            try {
                flightRepository.deleteById(getFlightById(id).getId());
            } catch (FlightNotFoundByIdException ignored) { }
        }
    }

    public Flight getFlightById(String id) {
        return flightRepository.getFlights().stream()
                .filter(flight -> flight.getId().equals(id))
                .findFirst()
                .orElseThrow(FlightNotFoundByIdException::new);
    }

    public List<Airport> getFilteredMatchList(String match) {
        return flightRepository.getFlights().stream()
                .map(Flight::getFrom)
                .filter(from -> from.getAirport().toLowerCase().contains(match)
                        || from.getCity().toLowerCase().contains(match)
                        || from.getCountry().toLowerCase().contains(match)
                )
                .map(from -> new Airport(from.getCountry(), from.getCity(), from.getAirport()))
                .toList();
    }

    public void clearRepository() {
        this.flightRepository.deleteAll();
    }
}
