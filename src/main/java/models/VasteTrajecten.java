package models;

import java.util.List;

public class VasteTrajecten {

    public static List<Trajectory> getAlleTrajecten() {
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
}


