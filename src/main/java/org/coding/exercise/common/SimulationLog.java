package org.coding.exercise.common;

import org.joda.time.LocalDateTime;

import java.util.Arrays;
import java.util.List;

public class SimulationLog {

    private Coordinates coordinates;

    private LocalDateTime dateTime;

    private Condition condition;

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

    public String toString() {
        List<String> attributes =
                Arrays.asList(this.getCoordinates().getName(),
                        String.valueOf(this.getCoordinates().getLatitude()),
                        String.valueOf(this.getCoordinates().getLongitude()),
                        String.valueOf(this.getCoordinates().getElevation()),
                        this.getDateTime().toString(),
                        this.getCondition().name(),
                        String.valueOf(this.getPressure()),
                        String.valueOf(this.getHumidity()),
                        String.valueOf(this.getTemperature()));
        return String.join("|", attributes);
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
