package com.example.demo.model;

import java.util.List;

public class FixedRoutes {

    public static List<Trajectory> getAllRoutes() {
        return List.of(
                //Bus routes
                new Trajectory("Zwolle", "Utrecht", List.of("08:30", "10:15", "17:35"), 55, "Bus"),
                new Trajectory("Utrecht", "Zwolle", List.of("09:05", "12:05", "18:10"), 55, "Bus"),
                //Trein routes
                new Trajectory("Zwolle", "Utrecht", List.of("7:00", "11:30", "16:45"), 45, "Trein"),
                new Trajectory("Utrecht", "Zwolle", List.of("8:15", "13:00", "17:20"), 45, "Trein"),

                //Bus
                new Trajectory("Amsterdam", "Rotterdam", List.of("07:05", "12:40", "18:20"), 95, "Bus"),
                new Trajectory("Rotterdam", "Amsterdam", List.of("08:10", "14:05", "19:40"), 95, "Bus"),
                //Trein
                new Trajectory("Amsterdam", "Rotterdam", List.of("7:45", "13:00", "19:00"), 50, "Trein"),
                new Trajectory("Rotterdam", "Amsterdam", List.of("9:00", "11:15", "20:00"), 50, "Trein"),

                //Bus
                new Trajectory("Groningen", "Leeuwarden", List.of("09:10", "13:55", "20:05"), 100,"Bus"),
                new Trajectory("Leeuwarden", "Groningen", List.of("07:35", "15:10", "21:15"), 100, "Bus"),

                //trein
                new Trajectory("Groningen", "Leeuwarden", List.of("08:00", "13:00", "19:45"), 85,"Trein"),
                new Trajectory("Leeuwarden", "Groningen", List.of("09:35", "14:00", "21:40"), 85, "Trein")

        );
    }
}


