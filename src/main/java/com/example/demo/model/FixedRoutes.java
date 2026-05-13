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
                        "Utrecht",
                        "Zwolle",
                        List.of("09:05", "12:05", "18:10"),
                        55
                ),
                new Trajectory(
                        "Amsterdam",
                        "Rotterdam",
                        List.of("07:05", "12:40", "18:20"),
                        93
                ),
                new Trajectory(
                        "Rotterdam",
                        "Amsterdam",
                        List.of("08:10", "14:05", "19:40"),
                        93
                ),
                new Trajectory(
                        "Groningen",
                        "Leeuwarden",
                        List.of("09:10", "13:55", "20:05"),
                        100
                ),
                new Trajectory(
                        "Leeuwarden",
                        "Groningen",
                        List.of("07:35", "15:10", "21:15"),
                        100
                )
        );
    }
}


