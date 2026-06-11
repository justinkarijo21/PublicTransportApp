package com.example.demo.model;

import com.example.demo.view.BusyStatus;

import java.time.LocalTime;
import java.util.List;
import java.util.Random;

public class Trajectory {
    private static final String ARROW = " → ";

    private final String departure;
    private final String arrival;
    // new: internal representation of departures with status
    private final List<Departure> departures;
    private final int travelMinutes;
    private final TransportType transportType;

    // Constructor for backwards-compatibility: accept List<String> and convert to Departure objects
    public Trajectory(String departure, String arrival, List<String> departureTimes, int travelMinutes, TransportType transportType) {
        this.departure = departure;
        this.arrival = arrival;
        this.departures = List.copyOf(Departure.fromStrings(departureTimes));
        this.travelMinutes = travelMinutes;
        this.transportType = transportType;
    }



    public TransportType getTransportType(){
        return transportType;
    }

    public String getDeparture() {
        return departure;
    }

    public String getArrival() {
        return arrival;
    }

    // new getter
    public List<Departure> getDepartures() {
        return departures;
    }

    // compatibility method: returns old List<String>
    public List<String> getDepartureTimes() {
        List<String> times = new java.util.ArrayList<>();
        for (Departure d : departures) {
            times.add(d.getTime());
        }
        return times;
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

    // Static inner class representing a departure time with a BusyStatus
    public static class Departure {
        private final String time; // "HH:mm"
        private final BusyStatus busyStatus;

        public Departure(String time, BusyStatus busyStatus) {
            this.time = time;
            this.busyStatus = busyStatus;
        }

        public String getTime() {
            return time;
        }

        public BusyStatus getBusyStatus() {
            return busyStatus;
        }

        @Override
        public String toString() {
            return time + " (" + busyStatus.getDescription() + ")";
        }

        // Helper to convert old List<String> to List<Departure>
        public static List<Departure> fromStrings(List<String> times) {
            List<Departure> list = new java.util.ArrayList<>();
            Random rnd = new Random();
            if (times == null) return list;
            for (String t : times) {
                BusyStatus bs = assignBusyStatus(t, rnd);
                list.add(new Departure(t, bs));
            }
            return list;
        }

        // Basic heuristic: rush hours -> FAST, shoulder -> MEDIUM, otherwise SLOW. Add small randomness.
        private static BusyStatus assignBusyStatus(String timeStr, Random rnd) {
            try {
                LocalTime t = LocalTime.parse(timeStr);
                int hour = t.getHour();

                boolean morningRush = (hour >= 7 && hour <= 9);
                boolean eveningRush = (hour >= 16 && hour <= 18);
                boolean shoulder = (hour >= 10 && hour <= 15) || (hour == 6) || (hour == 20);

                int roll = rnd.nextInt(100);
                if (morningRush || eveningRush) {
                    if (roll < 75) return BusyStatus.FAST;
                    return BusyStatus.MEDIUM;
                }
                if (shoulder) {
                    if (roll < 60) return BusyStatus.MEDIUM;
                    return BusyStatus.SLOW;
                }
                // otherwise off-peak
                if (roll < 80) return BusyStatus.SLOW;
                return BusyStatus.MEDIUM;
            } catch (Exception e) {
                return BusyStatus.SLOW;
            }
        }
    }
}
