package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.model.Flight;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

@Repository
public class FlightRepository {

    private final LinkedList<Flight> flights;

    public FlightRepository() {
        this.flights = new LinkedList<>();
    }

    public List<Flight> getFlights() {
        return this.flights;
    }

    public void deleteAll() {
        this.flights.clear();
    }

    public boolean add(Flight flight) {
        synchronized (flights) {
            return this.flights.add(flight);
        }
    }


    public void deleteById(String id) {
        this.getFlights().removeIf(flight -> flight.getId().equals(id));
    }

}