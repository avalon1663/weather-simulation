package org.coding.exercise.common;

import org.joda.time.LocalDateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.mockito.Mockito.mock;

@RunWith(JUnit4.class)
public class SimulationLogTest {

    @Test
    public void constructor() {
        Coordinates coordinates = mock(Coordinates.class);
        LocalDateTime dateTime = LocalDateTime.now();
        Condition condition = Condition.SUNNY;

        SimulationLog simulationLog =
                new SimulationLog(coordinates, dateTime, condition, 1D, 2D, 3D);
        Assert.assertEquals(coordinates, simulationLog.getCoordinates());
        Assert.assertEquals(dateTime, simulationLog.getDateTime());
        Assert.assertEquals(condition, simulationLog.getCondition());
        Assert.assertEquals(1D, simulationLog.getPressure(), 0D);
        Assert.assertEquals(2D, simulationLog.getHumidity(), 0D);
        Assert.assertEquals(3D, simulationLog.getTemperature(), 0D);
    }
}