package org.coding.exercise.service;

import org.coding.exercise.common.Condition;
import org.coding.exercise.common.Coordinates;
import org.coding.exercise.common.SimulationLog;
import org.joda.time.LocalDateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.concurrent.BlockingQueue;

import static org.mockito.Mockito.mock;

@RunWith(JUnit4.class)
public class SimulationLogConsumerTest {

    @Test
    @SuppressWarnings("unchecked")
    public void constructor() {
        BlockingQueue<SimulationLog>
                blockingQueue = mock(BlockingQueue.class);
        OutputStream output = new ByteArrayOutputStream();

        SimulationLogConsumer consumer =
                new SimulationLogConsumer(blockingQueue, output);
        Assert.assertEquals(blockingQueue, consumer.getMessageQueue());
        Assert.assertEquals(output, consumer.getOutput());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void formatLog() {
        BlockingQueue<SimulationLog>
                messageQueue = mock(BlockingQueue.class);
        OutputStream output = new ByteArrayOutputStream();
        LocalDateTime now = LocalDateTime.now();
        Condition condition = Condition.SUNNY;

        SimulationLogConsumer consumer =
                new SimulationLogConsumer(messageQueue, output);
        SimulationLog simulationLog =
                new SimulationLog(new Coordinates("name", 1D, 2D, 3D), now, condition, 4D, 5D, 6D);
        String serialized = consumer.formatLog(simulationLog);

        Assert.assertEquals(String.format("name|1.0,2.0,3|%s|%s|6.0|4.0|5\n", now, condition), serialized);
    }
}