package io.codelex.flightplanner.responses;

import io.codelex.flightplanner.model.Flight;

import java.util.List;

public record FlightSearchResponse(List<Flight> items, int page, int totalItems) { }