package com.example.demo.model;

import java.util.List;

public class FixedRoutes {

    public static List<Trajectory> getAllRoutes() {
        return List.of(
                new Trajectory(
                        "Zwolle",
                        "Utrecht",
                        List.of("08:30", "10:15", "17:35"),
                        55
                ),
                new Trajectory(
                        "Amsterdam",
                        "Rotterdam",
                        List.of("07:05", "12:40", "18:20"),
                        93
                ),
                new Trajectory(
                        "Groningen",
                        "Leeuwarden",
                        List.of("09:10", "13:55", "20:05"),
                        100
                )
        );
    }
}


