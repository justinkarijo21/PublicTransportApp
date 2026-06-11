package com.example.demo.model;

import java.util.List;

public class Trajectory {
    private static final String ARROW = " → ";

    private final Station departure;
    private final Station arrival;
    private final List<String> departureTimes;
    private final int travelMinutes;
    private final TransportType transportType;

    public Trajectory(Station departure, Station arrival, List<String> departureTimes, int travelMinutes, TransportType transportType) {
        this.departure = departure;
        this.arrival = arrival;
        this.departureTimes = departureTimes;
        this.travelMinutes = travelMinutes;
        this.transportType = transportType;
    }

    public TransportType getTransportType(){
        return transportType;
    }

    public Station getDeparture() {
        return departure;
    }

    public Station getArrival() {
        return arrival;
    }

    public List<String> getDepartureTimes() {
        return departureTimes;
    }

    public int getTravelMinutes() {
        return travelMinutes;
    }

    public String getDurationString() {
        int hours = travelMinutes / 60;
        int minutes = travelMinutes % 60;

        if (hours == 0) {
            // Alleen minuten
            return minutes + " minuten";
        } else if (minutes == 0) {
            // Alleen uren
            return hours + " uur";
        } else {
            // Beide uren en minuten
            return hours + " uur " + minutes + " minuten";
        }
    }

    @Override
    public String toString() {
        return departure + ARROW + arrival;
    }
}
