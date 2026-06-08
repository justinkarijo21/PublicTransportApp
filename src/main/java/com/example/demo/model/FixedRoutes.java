package com.example.demo.model;

import java.util.List;

public class FixedRoutes {

    public static List<Trajectory> getAllRoutes() {
        return List.of(
                //Bus routes
                new Trajectory("Zwolle", "Utrecht", List.of("08:30", "10:15", "17:35"), 55, TransportType.BUS),
                new Trajectory("Utrecht", "Zwolle", List.of("09:05", "12:05", "18:10"), 55, TransportType.BUS),
                //Trein routes
                new Trajectory("Zwolle", "Utrecht", List.of("07:00", "11:30", "16:45"), 45, TransportType.TREIN),
                new Trajectory("Utrecht", "Zwolle", List.of("08:15", "13:00", "17:20"), 45, TransportType.TREIN),

                //Bus
                new Trajectory("Amsterdam", "Rotterdam", List.of("07:05", "12:40", "18:20"), 95, TransportType.BUS),
                new Trajectory("Rotterdam", "Amsterdam", List.of("08:10", "14:05", "19:40"), 95, TransportType.BUS),
                //Trein
                new Trajectory("Amsterdam", "Rotterdam", List.of("07:45", "13:00", "19:00"), 50, TransportType.TREIN),
                new Trajectory("Rotterdam", "Amsterdam", List.of("09:00", "11:15", "20:00"), 50, TransportType.TREIN),

                //Bus
                new Trajectory("Groningen", "Leeuwarden", List.of("09:10", "13:55", "20:05"), 100,TransportType.BUS),
                new Trajectory("Leeuwarden", "Groningen", List.of("07:35", "15:10", "21:15"), 100, TransportType.BUS),

                //trein
                new Trajectory("Groningen", "Leeuwarden", List.of("08:00", "13:00", "19:45"), 85,TransportType.TREIN),
                new Trajectory("Leeuwarden", "Groningen", List.of("09:35", "14:00", "21:40"), 85,TransportType.TREIN),

                new Trajectory("Den Haag", "Eindhoven", List.of("07:30", "12:00", "18:00"), 90, TransportType.BUS),
                new Trajectory("Eindhoven", "Den Haag", List.of("08:45", "13:30", "19:30"), 90, TransportType.BUS),
                new Trajectory("Den Haag", "Eindhoven", List.of("08:00", "13:00", "19:00"), 60, TransportType.TREIN),
                new Trajectory("Eindhoven", "Den Haag", List.of("09:00", "14:00", "20:00"), 60, TransportType.TREIN),

                new Trajectory("Maastricht", "Nijmegen", List.of("07:20", "12:30", "18:15"), 80, TransportType.BUS),
                new Trajectory("Nijmegen", "Maastricht", List.of("08:30", "13:45", "19:25"), 80, TransportType.BUS),
                new Trajectory("Maastricht", "Nijmegen", List.of("08:55", "13:55", "19:55"), 55, TransportType.TREIN),
                new Trajectory("Nijmegen", "Maastricht", List.of("09:10", "14:10", "20:10"), 55, TransportType.TREIN),

                new Trajectory("Utrecht", "Eindhoven", List.of("07:15", "12:45", "18:30"), 70, TransportType.BUS),
                new Trajectory("Eindhoven", "Utrecht", List.of("08:30", "13:30", "19:15"), 70, TransportType.BUS),
                new Trajectory("Utrecht", "Eindhoven", List.of("08:45", "13:25", "19:45"), 50, TransportType.TREIN),
                new Trajectory("Eindhoven", "Utrecht", List.of("09:05", "14:00", "20:00"), 50, TransportType.TREIN),

                new Trajectory("Rotterdam", "Den Haag", List.of("07:00", "12:30", "18:00"), 30, TransportType.BUS),
                new Trajectory("Den Haag", "Rotterdam", List.of("08:15", "13:45", "19:30"), 30, TransportType.BUS),
                new Trajectory("Rotterdam", "Den Haag", List.of("07:40", "11:30", "19:50"), 20, TransportType.TREIN),
                new Trajectory("Den Haag", "Rotterdam", List.of("09:50", "14:30", "20:20"), 20, TransportType.TREIN),

                new Trajectory("Groningen", "Amsterdam", List.of("07:00", "12:00", "18:00"), 120, TransportType.BUS),
                new Trajectory("Amsterdam", "Groningen", List.of("08:30", "13:30", "19:30"), 120, TransportType.BUS),
                new Trajectory("Groningen", "Amsterdam", List.of("08:35", "13:35", "19:10"), 90, TransportType.TREIN),
                new Trajectory("Amsterdam", "Groningen", List.of("09:10", "14:30", "20:45"), 90, TransportType.TREIN)
        );
    }
}


