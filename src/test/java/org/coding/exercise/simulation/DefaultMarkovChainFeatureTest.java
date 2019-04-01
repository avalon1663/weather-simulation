package org.coding.exercise.simulation;

import org.coding.exercise.common.ParameterRange;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RunWith(JUnit4.class)
public class DefaultMarkovChainFeatureTest {

    @Test
    public void constructor() {
        String name = "feature1";
        List<Integer> seasonRange = IntStream.rangeClosed(1, 12).boxed().collect(Collectors.toList());
        ParameterRange pressureRange = new ParameterRange(1D, 10D);
        ParameterRange humidityRange = new ParameterRange(1D, 10D);
        ParameterRange temperatureRange = new ParameterRange(1D, 10D);

        double[][] transitionMatrix = new double[0][0];

        DefaultMarkovChainFeature feature =
                new DefaultMarkovChainFeature(name, seasonRange, pressureRange, humidityRange, temperatureRange, transitionMatrix);
        Assert.assertEquals(name, feature.getName());
        Assert.assertEquals(seasonRange, feature.getSeasonRange());
        Assert.assertEquals(pressureRange, feature.getPressureRange());
        Assert.assertEquals(humidityRange, feature.getHumidityRange());
        Assert.assertEquals(temperatureRange, feature.getTemperatureRange());
        Assert.assertArrayEquals(transitionMatrix, feature.getTransitionMatrix());
    }
}