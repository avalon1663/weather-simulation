package org.coding.exercise;

import org.coding.exercise.common.Condition;
import org.coding.exercise.service.DefaultMarkovChainSimulation;
import org.joda.time.LocalDateTime;

import java.util.Random;

public class WeatherDataService {

    public static void main(String[] args) throws InterruptedException {
        DefaultMarkovChainSimulation
                service = new DefaultMarkovChainSimulation();
        Random random = new Random();

        Condition init = Condition.values()[random.nextInt(3)];

        System.out.println(String.format("Initial condition %s", init.name()));

        for (int i = 0; i < 100; i++) {
            Condition next =
                    service.start(LocalDateTime.now(), init);
            System.out.println(String.format("Round %s, condition %s", i + 1, next.name()));

            Thread.sleep(random.nextInt(1000));
        }
    }
}
