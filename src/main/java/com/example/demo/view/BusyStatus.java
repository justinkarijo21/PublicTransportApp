package com.example.demo.view;

import javafx.scene.paint.Color;

public enum BusyStatus {
    SLOW(1, Color.GREEN, "RUSTIG"),
    MEDIUM(2, Color.ORANGE, "GEMIDDELD"),
    FAST(3, Color.RED, "DRUK");


     private final int amountIcons;
     private final Color color;
     private final String description;

     BusyStatus(int amountIcons, Color color, String description) {
         this.amountIcons = amountIcons;
         this.color = color;
         this.description = description;
     }

     public int getAmountIcons() {
         return amountIcons;
     }

     public Color getColor() {
         return color;
     }

     public String getDescription() {
         return description;
     }
}
