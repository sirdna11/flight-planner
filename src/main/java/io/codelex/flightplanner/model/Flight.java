package io.codelex.flightplanner.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Flight {

    private String id;
    @NotNull(message = "Airport from cannot be null")
    @Valid
    private io.codelex.flightplanner.model.Airport from;
    @NotNull(message = "Airport to cannot be null")
    @Valid
    private io.codelex.flightplanner.model.Airport to;
    @NotBlank(message = "Airline name is mandatory")
    private String carrier;
    @NotBlank(message = "Departure time is mandatory")
    private String departureTime;
    @NotBlank(message = "Arrival time is mandatory")
    private String arrivalTime;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Flight(io.codelex.flightplanner.model.Airport from, io.codelex.flightplanner.model.Airport to, String carrier, String departureTime, String arrivalTime) {
        this.from = from;
        this.to = to;
        this.carrier = carrier;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public io.codelex.flightplanner.model.Airport getFrom() {
        return from;
    }

    public io.codelex.flightplanner.model.Airport getTo() {
        return to;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    public LocalDateTime getDepartureTime() {
        return LocalDateTime.parse(departureTime, formatter);
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    public LocalDateTime getArrivalTime() {
        return LocalDateTime.parse(arrivalTime, formatter);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return Objects.equals(from, flight.from)
                && Objects.equals(to, flight.to)
                && Objects.equals(carrier, flight.carrier)
                && Objects.equals(departureTime, flight.departureTime)
                && Objects.equals(arrivalTime, flight.arrivalTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(to, carrier, departureTime, arrivalTime);
    }
}