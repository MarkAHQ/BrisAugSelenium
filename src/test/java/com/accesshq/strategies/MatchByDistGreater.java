package com.accesshq.strategies;

import com.accesshq.ui.Planet;

public class MatchByDistGreater implements Matchable {
    private long targetDistance;

    public MatchByDistGreater(long l) {
        this.targetDistance = l;
    }

    @Override
    public boolean match(Planet planet) {
        return planet.getDistance() > targetDistance;
    }
}
