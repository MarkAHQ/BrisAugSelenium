package com.accesshq.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.text.NumberFormat;
import java.text.ParseException;

public class Planet {

    private float radius;
    private int distance;
    private String name;
    private WebElement img;
    private WebElement planetElement;

    public Planet(WebElement planetElement) {

        this.planetElement = planetElement;
    }

    public float getRadius() {
        var radiusString = planetElement.findElement(By.className("radius")).getText();
        try {
            var result = NumberFormat.getNumberInstance().parse(radiusString.split(" ")[0]).floatValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        throw new NotFoundException();
    }

    public String getName() {
        return planetElement.findElement(By.className("name")).getText();
    }

    public long getDistance() {
        var distanceString = planetElement.findElement(By.className("distance")).getText();
        try {
            return NumberFormat.getNumberInstance().parse(distanceString.split(" ")[0]).longValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        throw new NotFoundException();
    }

    public void clickExplore() {
        planetElement.findElement(By.className("v-btn-content")).click();
    }
}