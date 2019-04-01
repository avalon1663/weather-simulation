package org.coding.exercise.simulation;

import org.coding.exercise.common.Condition;
import org.coding.exercise.common.Coordinates;
import org.coding.exercise.common.SimulationLog;
import org.joda.time.LocalDateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class DefaultMarkovChainSimulatorTest {

    @Test
    public void randomCondition() {
        DefaultMarkovChainSimulator
                simulator = new DefaultMarkovChainSimulator();
        Assert.assertNotNull(simulator.randomCondition());
    }

    @Test
    public void simulate() {
        DefaultMarkovChainSimulator
                simulator = new DefaultMarkovChainSimulator();
        Coordinates coordinates =
                new Coordinates("name", 1D, 2D, 3D);
        LocalDateTime dateTime = LocalDateTime.now();
        Condition condition = Condition.SUNNY;

        SimulationLog simulationLog =
                simulator.simulate(coordinates, dateTime, condition);
        Assert.assertNotNull(simulationLog);
    }

    @Test
    public void findMarkovChainFeature() {
        DefaultMarkovChainSimulator
                simulator = new DefaultMarkovChainSimulator();

        LocalDateTime springDateTime =
                new LocalDateTime().withYear(2017).withMonthOfYear(10).withDayOfMonth(1);
        DefaultMarkovChainFeature f1 = simulator.findMarkovChainFeature(springDateTime);

        Assert.assertEquals("spring", f1.getName());

        LocalDateTime summerDateTime =
                new LocalDateTime().withYear(2017).withMonthOfYear(1).withDayOfMonth(1);
        DefaultMarkovChainFeature f2 = simulator.findMarkovChainFeature(summerDateTime);

        Assert.assertEquals("summer", f2.getName());

        LocalDateTime autumnDateTime =
                new LocalDateTime().withYear(2017).withMonthOfYear(4).withDayOfMonth(1);
        DefaultMarkovChainFeature f3 = simulator.findMarkovChainFeature(autumnDateTime);

        Assert.assertEquals("autumn", f3.getName());


        LocalDateTime winterDateTime =
                new LocalDateTime().withYear(2017).withMonthOfYear(7).withDayOfMonth(1);
        DefaultMarkovChainFeature f4 = simulator.findMarkovChainFeature(winterDateTime);

        Assert.assertEquals("winter", f4.getName());
    }
}