package org.coding.exercise.common;

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

    public double[][] getTransitionMatrix() {
        return this.transitionMatrix;
    }
}
