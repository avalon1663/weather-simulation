package org.coding.exercise.service;

import org.apache.commons.math3.distribution.EnumeratedIntegerDistribution;
import org.coding.exercise.common.Condition;
import org.coding.exercise.common.MarkovChainFeature;
import org.coding.exercise.common.Range;
import org.joda.time.LocalDateTime;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class DefaultMarkovChainSimulation {

    public Condition start(LocalDateTime dateTime, Condition initialCondition) {
        MarkovChainFeature feature =
                this.findMarkovChainFeature(dateTime);
        double[] matrix = feature.getTransitionMatrix()[initialCondition.ordinal()];

        EnumeratedIntegerDistribution dist =
                new EnumeratedIntegerDistribution(
                        IntStream.rangeClosed(0, Condition.values().length - 1).toArray(), matrix);
        int nextIndex = dist.sample();

        return Condition.values()[nextIndex];
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
                                new double[]{0.35D, 0.1D, 0.55D},
                        }));
    }
}
