package org.coding.exercise.service;

import org.apache.commons.math3.distribution.EnumeratedIntegerDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.UniformRealDistribution;
import org.coding.exercise.common.*;
import org.joda.time.LocalDateTime;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class DefaultMarkovChainSimulation {

    public SimulationLog start(Coordinates coordinates, LocalDateTime dateTime, Condition prev) {
        MarkovChainFeature feature =
                this.findMarkovChainFeature(dateTime);
        Condition next =
                this.simulateCondition(feature, prev);
        double pressure = this.simulatePressure(feature);
        double humidity = this.simulateHumidity(feature);
        double temperature = this.simulateTemperature(feature);

        return new SimulationLog(coordinates, dateTime, next, pressure, humidity, temperature);
    }

    public Condition simulateCondition(MarkovChainFeature feature, Condition prev) {
        double[] matrix = feature.getTransitionMatrix()[prev.ordinal()];

        EnumeratedIntegerDistribution dist =
                new EnumeratedIntegerDistribution(
                        IntStream.rangeClosed(0, Condition.values().length - 1).toArray(), matrix);
        int nextIndex = dist.sample();

        return Condition.values()[nextIndex];
    }

    public double simulatePressure(MarkovChainFeature feature) {
        Range<Double>
                range = feature.getHumidity();
        UniformRealDistribution
                dist = new UniformRealDistribution(range.getMinimum(), range.getMaximum());
        return dist.sample();
    }

    public double simulateHumidity(MarkovChainFeature feature) {
        Range<Double>
                range = feature.getHumidity();
        double mean = (range.getMinimum() + range.getMaximum()) / 2;

        NormalDistribution
                dist = new NormalDistribution(mean, range.getMaximum() - mean);
        return dist.sample();
    }

    public double simulateTemperature(MarkovChainFeature feature) {
        Range<Double>
                range = feature.getTemperature();
        double mean = (range.getMinimum() + range.getMaximum()) / 2;

        NormalDistribution
                dist = new NormalDistribution(mean, range.getMaximum() - mean);
        return dist.sample();
    }

    public MarkovChainFeature findMarkovChainFeature(LocalDateTime dateTime) {
        int monthOtYear =
                dateTime.getMonthOfYear();
        return this.predefinedProbabilities().stream()
                .filter(each -> monthOtYear >= each.getSeason().getMinimum() && monthOtYear <= each.getSeason().getMaximum())
                .findFirst()
                .get();
    }

    public List<MarkovChainFeature> predefinedProbabilities() {
        return Arrays.asList(
                new MarkovChainFeature("spring",
                        new Range<>(1, 3), new Range<>(900D, 1000D),
                        new Range<>(90D, 100D), new Range<>(5D, 20D),
                        new double[][]{
                                new double[]{0.45D, 0.55D, 0D},
                                new double[]{0.55D, 0.45D, 0D},
                                new double[]{0D, 0D, 1D},
                        }),
                new MarkovChainFeature("summer",
                        new Range<>(4, 6), new Range<>(900D, 1000D),
                        new Range<>(90D, 100D), new Range<>(25D, 40D),
                        new double[][]{
                                new double[]{0.55D, 0.45D, 0D},
                                new double[]{0.45D, 0.55D, 0D},
                                new double[]{0D, 0D, 1D},
                        }),
                new MarkovChainFeature("autumn",
                        new Range<>(7, 9), new Range<>(900D, 1000D),
                        new Range<>(90D, 100D), new Range<>(10D, 25D),
                        new double[][]{
                                new double[]{0.35D, 0.65D, 0D},
                                new double[]{0.65D, 0.35D, 0D},
                                new double[]{0D, 0D, 1D},
                        }),
                new MarkovChainFeature("winter",
                        new Range<>(10, 12), new Range<>(900D, 1000D),
                        new Range<>(90D, 100D), new Range<>(-5D, 10D),
                        new double[][]{
                                new double[]{0.35D, 0.2D, 0.45D},
                                new double[]{0.4D, 0.4D, 0.2D},
                                new double[]{0.45D, 0.1D, 0.45D},
                        }));
    }
}
