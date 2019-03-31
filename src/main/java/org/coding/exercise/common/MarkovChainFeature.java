package org.coding.exercise.common;

import java.util.Random;

public class MarkovChainFeature {

    private String name;

    private Range<Integer> season;

    private Range<Double> pressure;
    private Range<Double> humidity;
    private Range<Double> temperature;

    private double[][] transitionMatrix;

    public MarkovChainFeature(String name, Range<Integer> season, Range<Double> pressure, Range<Double> humidity, Range<Double> temperature, double[][] transitionMatrix) {
        this.name = name;
        this.season = season;
        this.pressure = pressure;
        this.humidity = humidity;
        this.temperature = temperature;
        this.transitionMatrix = transitionMatrix;
    }

    public Range<Integer> getSeason() {
        return this.season;
    }

    public Range<Double> getPressure() {
        return this.pressure;
    }

    public Range<Double> getHumidity() {
        return this.humidity;
    }

    public Range<Double> getTemperature() {
        return this.temperature;
    }

    public double[][] getTransitionMatrix() {
        return this.transitionMatrix;
    }
}
