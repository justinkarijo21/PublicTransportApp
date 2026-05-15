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
