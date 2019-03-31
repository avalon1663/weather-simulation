package org.coding.exercise.common;

public class Range<T> {

    private T minimum;
    private T maximum;

    public Range(T minimum, T maximum) {
        this.setMinimum(minimum);
        this.setMaximum(maximum);
    }

    public T getMinimum() {
        return minimum;
    }

    public void setMinimum(T minimum) {
        this.minimum = minimum;
    }

    public T getMaximum() {
        return maximum;
    }

    public void setMaximum(T maximum) {
        this.maximum = maximum;
    }
}
