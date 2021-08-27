package com.accesshq.strategies;

import com.accesshq.ui.Planet;

import java.text.ParseException;
import java.util.function.Predicate;

public class MatchingFunctionsExample {

    public static Predicate<Planet> getPlanetByName(String planetName) {
        return planet -> planet.getName().equals("Earth");
    }

    public static Predicate<Planet> getPlanetWithRadiusGT4000() {
        return planet -> planet.getRadius() > 4000;
    }
}
