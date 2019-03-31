package org.coding.exercise;

import org.apache.commons.math3.distribution.UniformIntegerDistribution;
import org.coding.exercise.common.Condition;
import org.coding.exercise.common.Coordinates;
import org.coding.exercise.common.SimulationLog;
import org.coding.exercise.service.DefaultMarkovChainSimulation;
import org.joda.time.LocalDateTime;

import java.util.Random;

public class WeatherDataService {

    public static void main(String[] args) throws InterruptedException {
        DefaultMarkovChainSimulation
                service = new DefaultMarkovChainSimulation();
        Random random = new Random();

        Condition prev =
                Condition.values()[random.nextInt(3)];
        LocalDateTime dateTime = LocalDateTime.now();

        Coordinates coordinates =
                new Coordinates("Melbourne", -37.83, 144.98, 7);

        System.out.println(String.format("Initial condition %s", prev.name()));

        for (int i = 0; i < 100; i++) {
            SimulationLog next =
                    service.start(coordinates, dateTime, prev);
            System.out.println(String.format("Round %s, %s", i + 1, next));

            Thread.sleep(1000);

            dateTime = dateTime.plusDays(new UniformIntegerDistribution(5, 10).sample());
        }
    }
}
