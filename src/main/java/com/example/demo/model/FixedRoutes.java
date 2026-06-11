package com.example.demo.model;

import java.util.List;

public class FixedRoutes {

    public static List<Trajectory> getAllRoutes() {
        Station zwolle = findStationByName("Zwolle");
        Station utrecht = findStationByName("Utrecht");
        Station amsterdam = findStationByName("Amsterdam");
        Station rotterdam = findStationByName("Rotterdam");
        Station groningen = findStationByName("Groningen");
        Station leeuwarden = findStationByName("Leeuwarden");
        Station denHaag = findStationByName("Den Haag");
        Station eindhoven = findStationByName("Eindhoven");
        Station maastricht = findStationByName("Maastricht");
        Station nijmegen = findStationByName("Nijmegen");

        return List.of(
                // Bus routes
                new Trajectory(zwolle, utrecht, List.of("08:30", "10:15", "17:35"), 55, TransportType.BUS, true),
                new Trajectory(utrecht, zwolle, List.of("09:05", "12:05", "18:10"), 55, TransportType.BUS,false),
                new Trajectory(amsterdam, rotterdam, List.of("07:05", "12:40", "18:20"), 95, TransportType.BUS,true),
                new Trajectory(rotterdam, amsterdam, List.of("08:10", "14:05", "19:40"), 95, TransportType.BUS,false),
                new Trajectory(groningen, leeuwarden, List.of("09:10", "13:55", "20:05"), 100, TransportType.BUS,true),
                new Trajectory(leeuwarden, groningen, List.of("07:35", "15:10", "21:15"), 100, TransportType.BUS,false),
                new Trajectory(denHaag, eindhoven, List.of("07:30", "12:00", "18:00"), 90, TransportType.BUS,false),
                new Trajectory(eindhoven, denHaag, List.of("08:45", "13:30", "19:30"), 90, TransportType.BUS,true),
                new Trajectory(maastricht, nijmegen, List.of("07:20", "12:30", "18:15"), 80, TransportType.BUS,false),
                new Trajectory(nijmegen, maastricht, List.of("08:30", "13:45", "19:25"), 80, TransportType.BUS,false),
                new Trajectory(utrecht, eindhoven, List.of("07:15", "12:45", "18:30"), 70, TransportType.BUS,true),
                new Trajectory(eindhoven, utrecht, List.of("08:30", "13:30", "19:15"), 70, TransportType.BUS,true),
                new Trajectory(rotterdam, denHaag, List.of("07:00", "12:30", "18:00"), 30, TransportType.BUS,true),
                new Trajectory(denHaag, rotterdam, List.of("08:15", "13:45", "19:30"), 30, TransportType.BUS,false),
                new Trajectory(groningen, amsterdam, List.of("07:00", "12:00", "18:00"), 120, TransportType.BUS,true),
                new Trajectory(amsterdam, groningen, List.of("08:30", "13:30", "19:30"), 120, TransportType.BUS,true),

                // Trein routes
                new Trajectory(zwolle, utrecht, List.of("07:00", "11:30", "16:45"), 45, TransportType.TREIN,false),
                new Trajectory(utrecht, zwolle, List.of("08:15", "13:00", "17:20"), 45, TransportType.TREIN,false),
                new Trajectory(amsterdam, rotterdam, List.of("07:45", "13:00", "19:00"), 50, TransportType.TREIN,true),
                new Trajectory(rotterdam, amsterdam, List.of("09:00", "11:15", "20:00"), 50, TransportType.TREIN,true),
                new Trajectory(groningen, leeuwarden, List.of("08:00", "13:00", "19:45"), 85, TransportType.TREIN,false),
                new Trajectory(leeuwarden, groningen, List.of("09:35", "14:00", "21:40"), 85, TransportType.TREIN,true),
                new Trajectory(denHaag, eindhoven, List.of("08:00", "13:00", "19:00"), 60, TransportType.TREIN,false),
                new Trajectory(eindhoven, denHaag, List.of("09:00", "14:00", "20:00"), 60, TransportType.TREIN,false),
                new Trajectory(maastricht, nijmegen, List.of("08:55", "13:55", "19:55"), 55, TransportType.TREIN,true),
                new Trajectory(nijmegen, maastricht, List.of("09:10", "14:10", "20:10"), 55, TransportType.TREIN,false),
                new Trajectory(utrecht, eindhoven, List.of("08:45", "13:25", "19:45"), 50, TransportType.TREIN,true),
                new Trajectory(eindhoven, utrecht, List.of("09:05", "14:00", "20:00"), 50, TransportType.TREIN,false),
                new Trajectory(rotterdam, denHaag, List.of("07:40", "11:30", "19:50"), 20, TransportType.TREIN,true),
                new Trajectory(denHaag, rotterdam, List.of("09:50", "14:30", "20:20"), 20, TransportType.TREIN,false),
                new Trajectory(groningen, amsterdam, List.of("08:35", "13:35", "19:10"), 90, TransportType.TREIN,true),
                new Trajectory(amsterdam, groningen, List.of("09:10", "14:30", "20:45"), 90, TransportType.TREIN,true)
        );
    }

    private static Station findStationByName(String name) {
        return FixedStations.getAllStations().stream()
                .filter(station -> station.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("er bestaat geen station: " + name));
    }
}
