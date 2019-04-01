package org.coding.exercise.simulation;

import org.apache.commons.math3.distribution.EnumeratedIntegerDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.UniformRealDistribution;
import org.coding.exercise.common.Condition;
import org.coding.exercise.common.Coordinates;
import org.coding.exercise.common.ParameterRange;
import org.coding.exercise.common.SimulationLog;
import org.joda.time.LocalDateTime;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DefaultMarkovChainSimulator {

    public Condition randomCondition() {
        Random random = new Random();
        return Condition.values()[random.nextInt(Condition.values().length)];
    }

    public SimulationLog simulate(Coordinates coordinates, LocalDateTime dateTime, Condition prev) {
        DefaultMarkovChainFeature feature =
                this.findMarkovChainFeature(dateTime);
        Condition next =
                this.simulateCondition(feature, prev);
        double pressure = this.simulatePressure(feature);
        double humidity = this.simulateHumidity(feature);
        double temperature = this.simulateTemperature(feature, dateTime);

        return new SimulationLog(coordinates, dateTime, next, pressure, humidity, temperature);
    }

    public Condition simulateCondition(DefaultMarkovChainFeature feature, Condition prev) {
        double[] matrix = feature.getTransitionMatrix()[prev.ordinal()];

        EnumeratedIntegerDistribution dist =
                new EnumeratedIntegerDistribution(
                        IntStream.rangeClosed(0, Condition.values().length - 1).toArray(), matrix);
        int nextIndex = dist.sample();

        return Condition.values()[nextIndex];
    }

    public double simulatePressure(DefaultMarkovChainFeature feature) {
        ParameterRange
                parameterRange = feature.getPressureRange();
        UniformRealDistribution
                dist = new UniformRealDistribution(parameterRange.getMinimum(), parameterRange.getMaximum());
        return dist.sample();
    }

    public double simulateHumidity(DefaultMarkovChainFeature feature) {
        ParameterRange
                parameterRange = feature.getHumidityRange();
        double mean = (parameterRange.getMinimum() + parameterRange.getMaximum()) / 2;

        NormalDistribution
                dist = new NormalDistribution(mean, 1);
        return dist.sample();
    }

    public double simulateTemperature(DefaultMarkovChainFeature feature, LocalDateTime dateTime) {
        ParameterRange
                parameterRange = feature.getTemperatureRange();
        double mean = (parameterRange.getMinimum() + parameterRange.getMaximum()) / 2;

        int hourOfDay = dateTime.getHourOfDay();

        if (hourOfDay >= 8 && hourOfDay <= 20) {
            return new NormalDistribution(mean, 5).sample();
        } else
            return new NormalDistribution(parameterRange.getMinimum(), 5).sample();
    }

    public DefaultMarkovChainFeature findMarkovChainFeature(LocalDateTime dateTime) {
        int monthOtYear =
                dateTime.getMonthOfYear();
        List<DefaultMarkovChainFeature> featureList =
                this.predefinedProbabilities().stream()
                        .filter(each -> each.getSeasonRange().contains(monthOtYear))
                        .collect(Collectors.toList());
        if (featureList.isEmpty()) {
            throw new UnsupportedOperationException(String.format("Invalid DateTime %s", dateTime));
        }
        return featureList.get(0);
    }

    public List<DefaultMarkovChainFeature> predefinedProbabilities() {
        return Arrays.asList(
                new DefaultMarkovChainFeature("spring",
                        Arrays.asList(9, 10, 11), new ParameterRange(900D, 1000D),
                        new ParameterRange(90D, 100D), new ParameterRange(5D, 20D),
                        new double[][]{
                                new double[]{0.45D, 0.5D, 0.05D},
                                new double[]{0.55D, 0.45D, 0D},
                                new double[]{0.9D, 0D, 0.1D},
                        }),
                new DefaultMarkovChainFeature("summer",
                        Arrays.asList(12, 1, 2), new ParameterRange(900D, 1000D),
                        new ParameterRange(90D, 100D), new ParameterRange(25D, 40D),
                        new double[][]{
                                new double[]{0.55D, 0.45D, 0D},
                                new double[]{0.45D, 0.55D, 0D},
                                new double[]{0.9D, 0.05D, 0.05D},
                        }),
                new DefaultMarkovChainFeature("autumn",
                        Arrays.asList(3, 4, 5), new ParameterRange(900D, 1000D),
                        new ParameterRange(90D, 100D), new ParameterRange(10D, 25D),
                        new double[][]{
                                new double[]{0.35D, 0.65D, 0D},
                                new double[]{0.65D, 0.35D, 0D},
                                new double[]{0.9D, 0D, 0.1D},
                        }),
                new DefaultMarkovChainFeature("winter",
                        Arrays.asList(6, 7, 8), new ParameterRange(900D, 1000D),
                        new ParameterRange(90D, 100D), new ParameterRange(-5D, 10D),
                        new double[][]{
                                new double[]{0.35D, 0.2D, 0.45D},
                                new double[]{0.4D, 0.4D, 0.2D},
                                new double[]{0.45D, 0.1D, 0.45D},
                        }));
    }
}
