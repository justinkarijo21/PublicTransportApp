package com.example.demo.model;

import java.util.List;

public class Trajectory {
    private static final String ARROW = " → ";

    private final String departure;
    private final String arrival;
    private final List<String> departureTimes;
    private final int travelMinutes;

    public Trajectory(String departure, String arrival, List<String> departureTimes, int travelMinutes) {
        this.departure = departure;
        this.arrival = arrival;
        this.departureTimes = departureTimes;
        this.travelMinutes = travelMinutes;
    }

    public String getDeparture() {
        return departure;
    }

    public String getArrival() {
        return arrival;
    }

    public List<String> getDepartureTimes() {
        return departureTimes;
    }

    public int getTravelMinutes() {
        return travelMinutes;
    }

    @Override
    public String toString() {
        return departure + ARROW + arrival;
    }
}
