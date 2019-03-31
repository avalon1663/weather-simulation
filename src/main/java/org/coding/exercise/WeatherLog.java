package org.coding.exercise;

import org.coding.exercise.common.Condition;

public class WeatherLog {

    private Condition condition;

    public WeatherLog(Condition condition) {
        this.setCondition(condition);
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }
}
