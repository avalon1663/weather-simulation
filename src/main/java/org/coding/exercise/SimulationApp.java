package org.coding.exercise;

import org.coding.exercise.common.Coordinates;
import org.coding.exercise.common.SimulationLog;
import org.coding.exercise.service.SimulationLogConsumer;
import org.coding.exercise.service.SimulationLogProducer;
import org.joda.time.LocalDateTime;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimulationApp {

    public static void main(String[] args) {
        LocalDateTime dateTime =
                new LocalDateTime().withYear(2017).withMonthOfYear(1).withDayOfMonth(1);
        ExecutorService pool =
                Executors.newFixedThreadPool(10);
        Runtime.getRuntime().addShutdownHook(new Thread(pool::shutdown));

        BlockingQueue<SimulationLog>
                messageQueue = new ArrayBlockingQueue<>(100);
        cityList().stream()
                .map(each -> new SimulationLogProducer(messageQueue, each, dateTime, dateTime.plusDays(30)))
                .forEach(pool::submit);
        SimulationLogConsumer consumer =
                new SimulationLogConsumer(messageQueue, System.out);
        pool.submit(consumer);
    }

    public static List<Coordinates> cityList() {
        return Arrays.asList(
                new Coordinates("Sydney", -33.86, 151.21, 39),
                new Coordinates("Melbourne", -37.83, 144.98, 7),
                new Coordinates("Adelaide", -34.92, 138.62, 48)
        );
    }
}
