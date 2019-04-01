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
    
    static class Message {

        String msg;

        public Message(String msg) {
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }
    }

    static class Producer implements Runnable {

        private String name;

        private BlockingQueue<Message> blockingQueue;

        private UniformIntegerDistribution random = new UniformIntegerDistribution(3000, 5000);

        public Producer(String name, BlockingQueue<Message> blockingQueue) {
            this.name = name;
            this.blockingQueue = blockingQueue;
        }

        public void run() {
            for (int i = 0; i < 20; i++) {
                try {
                    this.blockingQueue.put(
                            new Message(String.format("Producer %s send message at round %s at %s", this.name, i, System.currentTimeMillis())));
                    Thread.sleep(this.random.sample());

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            try {
                this.blockingQueue.put(new Message("exit"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Consumer implements Runnable {

        private BlockingQueue<Message> blockingQueue;

        public Consumer(BlockingQueue<Message> blockingQueue) {
            this.blockingQueue = blockingQueue;
        }

        public void run() {
            try {
                Message msg;

                while (!(msg = this.blockingQueue.take()).getMsg().equals("exit")) {
                    System.out.println(msg.getMsg());
                    Thread.sleep(10);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
