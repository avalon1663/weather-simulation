package org.coding.exercise.service;

import org.coding.exercise.common.Condition;
import org.coding.exercise.common.Coordinates;
import org.coding.exercise.common.SimulationLog;
import org.coding.exercise.simulation.DefaultMarkovChainSimulator;
import org.joda.time.LocalDateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.concurrent.BlockingQueue;

import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class SimulationLogProducerTest {

    @Test
    @SuppressWarnings("unchecked")
    public void constructor() {
        BlockingQueue<SimulationLog>
                messageQueue = mock(BlockingQueue.class);
        Coordinates coordinates = mock(Coordinates.class);
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime finish = start.plusDays(30);

        SimulationLogProducer producer =
                new SimulationLogProducer(messageQueue, coordinates, start, finish);
        Assert.assertEquals(messageQueue, producer.getMessageQueue());
        Assert.assertEquals(coordinates, producer.getCoordinates());
        Assert.assertEquals(start, producer.getStartDateTime());
        Assert.assertEquals(finish, producer.getEndDateTime());
        Assert.assertNotNull(producer.getSimulator());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void run() throws InterruptedException {
        BlockingQueue<SimulationLog>
                messageQueue = mock(BlockingQueue.class);
        Coordinates coordinates = mock(Coordinates.class);
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime finish = start.plusSeconds(1);
        DefaultMarkovChainSimulator simulator = mock(DefaultMarkovChainSimulator.class);

        SimulationLogProducer producer =
                new SimulationLogProducer(messageQueue, coordinates, start, finish, simulator);
        Condition condition = Condition.SUNNY;
        SimulationLog simulationLog = mock(SimulationLog.class);

        when(simulator.simulate(coordinates, start, condition)).thenReturn(simulationLog);
        doNothing().when(messageQueue).put(simulationLog);
        when(simulator.randomCondition()).thenReturn(condition);

        producer.run();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void produceLog() throws InterruptedException {
        BlockingQueue<SimulationLog>
                messageQueue = mock(BlockingQueue.class);
        Coordinates coordinates = mock(Coordinates.class);
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime finish = start.plusSeconds(1);
        DefaultMarkovChainSimulator simulator = mock(DefaultMarkovChainSimulator.class);

        SimulationLogProducer producer =
                new SimulationLogProducer(messageQueue, coordinates, start, finish, simulator);
        Condition condition = Condition.SUNNY;
        SimulationLog simulationLog = mock(SimulationLog.class);

        when(simulator.simulate(coordinates, start, condition)).thenReturn(simulationLog);
        doNothing().when(messageQueue).put(simulationLog);

        producer.produceLog(start, finish, condition);
    }

    @Test(expected = InterruptedException.class)
    @SuppressWarnings("unchecked")
    public void produceLogInterruptedException() throws InterruptedException {
        BlockingQueue<SimulationLog>
                messageQueue = mock(BlockingQueue.class);
        Coordinates coordinates = mock(Coordinates.class);
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime finish = start.plusSeconds(1);
        DefaultMarkovChainSimulator simulator = mock(DefaultMarkovChainSimulator.class);

        SimulationLogProducer producer =
                new SimulationLogProducer(messageQueue, coordinates, start, finish, simulator);
        Condition condition = Condition.SUNNY;

        SimulationLog simulationLog = mock(SimulationLog.class);

        when(simulator.simulate(coordinates, start, condition)).thenReturn(simulationLog);
        doThrow(new InterruptedException()).when(messageQueue).put(simulationLog);

        producer.produceLog(start, finish, condition);
    }
}