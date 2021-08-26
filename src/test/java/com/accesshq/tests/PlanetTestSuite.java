package com.accesshq.tests;

import com.accesshq.strategies.MatchByDistGreater;
import com.accesshq.strategies.MatchByName;
import com.accesshq.strategies.MatchingFunctions;
import com.accesshq.ui.*;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.text.ParseException;

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
    public void PlanetsPage_NeptuneFurthestPlanet2() {
        // Arrange
        new HomePage(driver).clickPlanetButton();
        var planetPage = new PlanetPage(driver);
        Planet currPlanet = null;

        for(Planet planet : planetPage.getPlanets()) {
            if (isFurtherFromSunThan(currPlanet, planet)) {
                currPlanet = planet;
            }
        }

        // Act
        Assertions.assertEquals("Neptune", currPlanet.getName());
    }

    @Test
    public void PlanetPage_AssertEarthRadius() throws ParseException {
        Assertions.assertEquals(6371,
                new PlanetPage(driver).getPlanet(p -> p.getName() == "Earth").getRadius());
    }

    private boolean isFurtherFromSunThan(Planet currPlanet, Planet planet) {
        return MatchingFunctions.isDistanceGreaterThan(getDistanceWithNullHandling(currPlanet)).test(planet);
    }

    private boolean isFurtherFromSun(Planet currentFurthest, Planet planet) {
        return new MatchByDistGreater(getDistanceWithNullHandling(currentFurthest)).match(planet);
    }

    private long getDistanceWithNullHandling(Planet currentPlanet) {
        return currentPlanet == null ? 0 : currentPlanet.getDistance();
    }

    private double getTargetDistance(Planet currentFarthest) {
        return currentFarthest == null ? 0 : currentFarthest.getDistance();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}