package org.coding.exercise.service;

import org.coding.exercise.common.SimulationLog;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class SimulationLogConsumer implements Runnable {

    private BlockingQueue<SimulationLog> messageQueue;

    private OutputStream output;

    public SimulationLogConsumer(BlockingQueue<SimulationLog> messageQueue, OutputStream output) {
        this.messageQueue = messageQueue;
        this.output = output;
    }

    public void run() {
        try {
            this.consumeLog(this.messageQueue, this.output);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    protected void consumeLog(BlockingQueue<SimulationLog> messageQueue, OutputStream output) throws IOException, InterruptedException {
        while (true) {
            SimulationLog
                    simulationLog = messageQueue.poll(5, TimeUnit.MINUTES);
            output.write(this.formatLog(simulationLog).getBytes());

            Thread.sleep(100);
        }
    }

    protected String formatLog(SimulationLog simulationLog) {
        List<String> attributes =
                Arrays.asList(simulationLog.getCoordinates().getName(),
                        String.join(",", Arrays.asList(
                                String.valueOf(simulationLog.getCoordinates().getLatitude()),
                                String.valueOf(simulationLog.getCoordinates().getLongitude()),
                                String.valueOf((int) simulationLog.getCoordinates().getElevation()))),
                        simulationLog.getDateTime().toString(),
                        simulationLog.getCondition().name(),
                        String.valueOf(new BigDecimal(simulationLog.getTemperature()).setScale(1, RoundingMode.HALF_UP).doubleValue()),
                        String.valueOf(new BigDecimal(simulationLog.getPressure()).setScale(1, RoundingMode.HALF_UP).doubleValue()),
                        String.valueOf(new Double(simulationLog.getHumidity()).intValue()));
        return String.join("|", attributes).concat("\n");
    }
}
