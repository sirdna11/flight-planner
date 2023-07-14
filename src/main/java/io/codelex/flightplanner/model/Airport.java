package io.codelex.flightplanner.model;

import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public class Airport {

    @NotBlank(message = "Country is mandatory")
    private String country;
    @NotBlank(message = "City is mandatory")
    private String city;
    @NotBlank(message = "Airport location identifier is mandatory")
    private String airport;

    public Airport(String country, String city, String airport) {
        this.country = country;
        this.city = city;
        this.airport = airport;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getAirport() {
        return airport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airport airport1 = (Airport) o;
        return country.equalsIgnoreCase(airport1.country)
                && city.equalsIgnoreCase(airport1.city)
                && airport.strip().equalsIgnoreCase(airport1.airport.strip());
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, city, airport);
    }
}