package com.accesshq.strategies;

import com.accesshq.strategies.Matchable;
import com.accesshq.ui.Planet;

public class MatchByName implements Matchable {
    private String planetName;

    public MatchByName(String planetName) {
        this.planetName = planetName;
    }

    @Override
    public boolean match(Planet planet) {
        return planet.getName().equals(planetName);
    }
}
