package com.accesshq.ui;

import com.accesshq.strategies.Matchable;
import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class PlanetPage {
    private final WebDriver driver;

    public PlanetPage(WebDriver driver) {
        this.driver = driver;
    }

    public float getRadius(Matchable matchingStrategy) throws Exception {
        return getFarthestPlanet(matchingStrategy).getRadius();
    }

    public long getDistance(Matchable matchingStrategy) throws Exception {
        return getFarthestPlanet(matchingStrategy).getDistance();
    }

    public Planet getFarthestPlanet(Predicate<Planet> matcher) {
        for (var planetElement : driver.findElements(By.className("planet"))) {
            var planet = new Planet(planetElement);
            if(matcher.test(planet)) {
                return planet;
            }
        }

        throw new NotFoundException();
    }

    public String getName(Matchable matchingStrategy) throws Exception {
        return getFarthestPlanet(matchingStrategy).getName();
    }

    private Planet getFarthestPlanet(Matchable matchingStrategy) throws Exception {
        for (var planetElement : driver.findElements(By.className("planet"))) {
            var planet = new Planet(planetElement);
            if(matchingStrategy.match(planet)) {
                return planet;
            }
        }

        throw new Exception("Couldn't match using: " + matchingStrategy.getClass().toGenericString());
    }

    public Planet getPlanet(Predicate<Planet> matcher) {
        for(Planet planet : getPlanets()) {
            if (matcher.test(planet)) {
                return planet;
            }
        }

        throw new NotFoundException();
    }

    public List<Planet> getPlanets() {
        ArrayList<Planet> planets = new ArrayList<>();

        for(var planet : driver.findElements(By.className("planet"))) {
            planets.add(new Planet(planet));
        }

        return planets;
    }

    public Planet getPlanet(Matchable matchingStrategy) {
        for(Planet planet : getPlanets()) {
            if (matchingStrategy.match(planet)) {
                return planet;
            }
        }

        throw new NotFoundException();
    }
}
