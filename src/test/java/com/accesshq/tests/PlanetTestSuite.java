package com.accesshq.tests;

import com.accesshq.strategies.MatchByDistGreater;
import com.accesshq.strategies.MatchByName;
import com.accesshq.strategies.Matchable;
import com.accesshq.strategies.MatchingFunctions;
import com.accesshq.ui.*;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.text.ParseException;
import java.util.function.Predicate;

public class PlanetTestSuite {

    WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://d1iw6mb9di5l9r.cloudfront.net/");
    }

    @Test
    public void planetsPage_Earth_RadiusTest() throws Exception {
        new HomePage(driver).clickPlanetButton();

        Assertions.assertEquals(new PlanetPage(driver).getRadius(new MatchByName("Earth")), 6371);
    }

    @Test
    public void planetPage_Mercury_DistanceTest() throws Exception {
        new HomePage(driver).clickPlanetButton();

        Assertions.assertEquals(new PlanetPage(driver).getDistance(new MatchByName("Mercury")), 57910000);
    }

    @Test
    public void PlanetsPage_NeptuneFurthestPlanet() {
        // Arrange
        new HomePage(driver).clickPlanetButton();
        var planetPage = new PlanetPage(driver);

        // Act
        Planet currentPlanet = null;
        for(var planet : planetPage.getPlanets()) {
            if(isFurtherFromSun(currentPlanet, planet)) {
                currentPlanet = planet;
            }
        }

        Assertions.assertEquals("Neptune", currentPlanet.getName());
    }

    @Test
    public void PlanetPage_AssertEarthRadius_NewPred() throws ParseException {
        Assertions.assertEquals(6371,
                new PlanetPage(driver).getPlanet(new Predicate<Planet>() {
                    @Override
                    public boolean test(Planet planet) {
                        return planet.getName().equals("Earth");
                    }
                }).getRadius());
    }

    @Test
    public void PlanetPage_AssertEarthRadius_newMatchingStrat() throws ParseException {
        Assertions.assertEquals(6371,
                                new PlanetPage(driver).getPlanet(new Matchable() {
                                    @Override
                                    public boolean match(Planet planet) {
                                        return planet.getName().equals("Earth");
                                    }
                                }).getRadius());
    }

    @Test
    public void PlanetPage_AssertEarthRadius_MatchingStratLam() throws ParseException {
        Assertions.assertEquals(6371,
                                new PlanetPage(driver)
                                        .getPlanet((Matchable) planet -> planet.getName().equals("Earth")).getRadius());
    }

    @Test
    public void PlanetPage_AssertEarthRadius_MatchingStratStatic() throws ParseException {
        Assertions.assertEquals(6371,
                                new PlanetPage(driver).getPlanet(MatchingFunctions.nameMatchable("Earth")));
    }

    private boolean isFurtherFromSun(Planet currentFurthest, Planet planet) {
        return new MatchByDistGreater(getTargetDistance(currentFurthest)).match(planet);
    }

    private long getTargetDistance(Planet currentPlanet) {
        return currentPlanet == null ? 0 : currentPlanet.getDistance();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}