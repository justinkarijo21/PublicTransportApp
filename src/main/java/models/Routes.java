package models;

import java.util.List;

public class Routes {

    public static List<Trajectory> getAllRoutes() {
        return List.of(
                new Trajectory(
                        "Utrecht",
                        "Amsterdam",
                        List.of("08:15", "12:30", "17:45")
                ),
                new Trajectory(
                        "Zwolle",
                        "Groningen",
                        List.of("09:00", "13:15", "18:20")
                ),
                new Trajectory(
                        "Arnhem",
                        "Nijmegen",
                        List.of("07:50", "11:10", "16:40")
                )
        );
    }

    public static List<String> getStartLocations() {
        return List.of(
                "Utrecht",
                "Zwolle",
                "Arnhem"
        );
    }

    public static List<String> getEndLocations() {
        return List.of(
                "Amsterdam",
                "Groningen",
                "Nijmegen"
        );
    }
}


