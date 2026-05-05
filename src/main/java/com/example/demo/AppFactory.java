package com.example.demo;
import java.util.List;

import models.Trajectory;
import models.VasteTrajecten;

public class AppFactory {


        public static List<Trajectory> getTrajecten() {
            return VasteTrajecten.getAlleTrajecten();

        }
    }


