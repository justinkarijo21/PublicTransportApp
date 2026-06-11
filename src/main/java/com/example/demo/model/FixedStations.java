package com.example.demo.model;

import java.util.List;

public class FixedStations {
    public static List<Station> getAllStations() {
        return List.of(
                new Station("Amersfoort", 6, true, "Amersfoort"),
                new Station("Zwolle", 8, true, "Zwolle"),
                new Station("Amsterdam", 15, true, "Amsterdam"),
                new Station("Rotterdam", 13, true, "Rotterdam"),
                new Station("Groningen", 6, true, "Groningen"),
                new Station("Leeuwarden", 4, true, "Leeuwarden"),
                new Station("Den Haag", 12, true, "Den Haag"),
                new Station("Eindhoven", 6, true, "Eindhoven"),
                new Station("Maastricht", 5, false, "Maastricht"),
                new Station("Nijmegen", 4, false, "Nijmegen"),
                new Station("Utrecht", 12, true, "Utrecht" )
        );
    }
}
