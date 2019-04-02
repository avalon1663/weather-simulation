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
        LocalDateTime startDateTime =
                new LocalDateTime().withYear(2017).withMonthOfYear(1).withDayOfMonth(1);
        LocalDateTime endDateTime = startDateTime.plusDays(30);

        List<Coordinates> cityList = cityList();

        ExecutorService pool =
                Executors.newFixedThreadPool(cityList.size() + 1);
        Runtime.getRuntime().addShutdownHook(new Thread(pool::shutdown));

        BlockingQueue<SimulationLog>
                messageQueue = new ArrayBlockingQueue<>(32 * 1024);
        cityList.stream()
                .map(each -> new SimulationLogProducer(messageQueue, each, startDateTime, endDateTime))
                .forEach(pool::submit);
        SimulationLogConsumer consumer =
                new SimulationLogConsumer(messageQueue, System.out);
        pool.submit(consumer);
    }

    public static List<Coordinates> cityList() {
        return Arrays.asList(
                new Coordinates("Sydney", -33.86, 151.21, 39),
                new Coordinates("Melbourne", -37.83, 144.98, 7),
                new Coordinates("Adelaide", -34.92, 138.62, 48),
                new Coordinates("Launceston", -41.429825, 147.157135, 42),
                new Coordinates("Townsville", -19.258965, 146.816956, 35),
                new Coordinates("Cairns", -16.925491, 145.754120, 45),
                new Coordinates("Perth", -31.953512, 115.857048, 52),
                new Coordinates("Bendigo", -36.757786, 144.278702, 40),
                new Coordinates("Brisbane", -27.470125, 153.021072, 43),
                new Coordinates("Wollongong", -34.425072, 150.893143, 38)
        );
    }
}
