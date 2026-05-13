package com.example.demo.app;
import java.util.List;

import com.example.demo.model.FixedRoutes;
import com.example.demo.model.Trajectory;

public class AppFactory {


        public static List<Trajectory> getTrajectory() {
            return FixedRoutes.getAllRoutes();

        }
    }


