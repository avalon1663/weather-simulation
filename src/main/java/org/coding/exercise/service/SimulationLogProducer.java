package org.coding.exercise.service;

import org.apache.commons.math3.distribution.UniformIntegerDistribution;
import org.coding.exercise.common.Condition;
import org.coding.exercise.common.Coordinates;
import org.coding.exercise.common.SimulationLog;
import org.coding.exercise.simulation.DefaultMarkovChainSimulator;
import org.joda.time.LocalDateTime;

import java.util.concurrent.BlockingQueue;

public class SimulationLogProducer implements Runnable {

    private Coordinates coordinates;

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    private DefaultMarkovChainSimulator simulator;

    private BlockingQueue<SimulationLog> messageQueue;

    private static final int SECS_IN_DAY = 24 * 60 * 60;

    public SimulationLogProducer(BlockingQueue<SimulationLog> messageQueue, Coordinates coordinates, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this(messageQueue, coordinates, startDateTime, endDateTime, new DefaultMarkovChainSimulator());
    }

    public SimulationLogProducer(BlockingQueue<SimulationLog> messageQueue, Coordinates coordinates, LocalDateTime startDateTime, LocalDateTime endDateTime, DefaultMarkovChainSimulator simulator) {
        this.messageQueue = messageQueue;
        this.coordinates = coordinates;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.simulator = simulator;
    }

    public void run() {
        LocalDateTime
                currDateTime = this.startDateTime;
        Condition initCondition = this.getSimulator().randomCondition();
        try {
            this.produceLog(currDateTime, this.endDateTime, initCondition);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void produceLog(LocalDateTime prevDateTime, LocalDateTime endDateTime, Condition prevCondition) throws InterruptedException {
        UniformIntegerDistribution forwardTimeDist =
                new UniformIntegerDistribution(SECS_IN_DAY, SECS_IN_DAY * 2);

        while (prevDateTime.isBefore(endDateTime)) {
            SimulationLog simulationLog =
                    this.getSimulator().simulate(this.getCoordinates(), prevDateTime, prevCondition);
            this.getMessageQueue().put(simulationLog);

            Thread.sleep(2000);

            prevCondition = simulationLog.getCondition();
            prevDateTime = prevDateTime.plusSeconds(forwardTimeDist.sample());
        }
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public DefaultMarkovChainSimulator getSimulator() {
        return simulator;
    }

    public BlockingQueue<SimulationLog> getMessageQueue() {
        return messageQueue;
    }
}
