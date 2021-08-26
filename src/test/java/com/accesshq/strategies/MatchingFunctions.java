package com.accesshq.strategies;

import com.accesshq.tests.PlanetTestSuite;
import com.accesshq.ui.Planet;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class MatchingFunctions {

    public static Predicate<Planet> isDistanceGreaterThan(long distance) {
        return new Predicate<Planet>() {
            @Override
            public boolean test(Planet planet) {
                return planet.getDistance() > distance;
            }
        };
    }

    public static Predicate<Planet> doesNameMatch(String name) {
        return new Predicate<Planet>() {
            @Override
            public boolean test(Planet planet) {
                return planet.getName().equals(name);
            }
        };
    }

    //public static BiFunction<Planet, Planet, Planet>

    public static Matchable nameMatchable(String name) {
        return planet -> planet.getName().equals(name);
    }
}
