package org.coding.exercise.common;

import org.joda.time.LocalDateTime;

public class SimulationLog {

    private Condition condition;
    private LocalDateTime dateTime;
    private Coordinates coordinates;

    private double pressure;
    private double humidity;
    private double temperature;

    public SimulationLog(Coordinates coordinates, LocalDateTime dateTime, Condition condition, double pressure, double humidity, double temperature) {
        this.dateTime = dateTime;
        this.coordinates = coordinates;
        this.condition = condition;
        this.pressure = pressure;
        this.humidity = humidity;
        this.temperature = temperature;
    }

    public Condition getCondition() {
        return condition;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public double getPressure() {
        return pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getTemperature() {
        return temperature;
    }
}
