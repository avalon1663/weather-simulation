package org.coding.exercise.simulation;

import org.coding.exercise.common.ParameterRange;

import java.util.List;

public class DefaultMarkovChainFeature {

    private String name;

    private List<Integer> seasonRange;

    private ParameterRange pressureRange;
    private ParameterRange humidityRange;
    private ParameterRange temperatureRange;

    private double[][] transitionMatrix;

    public DefaultMarkovChainFeature(String name, List<Integer> seasonRange, ParameterRange pressureRange, ParameterRange humidityRange, ParameterRange temperatureRange, double[][] transitionMatrix) {
        this.name = name;
        this.seasonRange = seasonRange;
        this.pressureRange = pressureRange;
        this.humidityRange = humidityRange;
        this.temperatureRange = temperatureRange;
        this.transitionMatrix = transitionMatrix;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getSeasonRange() {
        return this.seasonRange;
    }

    public ParameterRange getPressureRange() {
        return this.pressureRange;
    }

    public ParameterRange getHumidityRange() {
        return this.humidityRange;
    }

    public ParameterRange getTemperatureRange() {
        return this.temperatureRange;
    }

    public double[][] getTransitionMatrix() {
        return this.transitionMatrix;
    }
}
