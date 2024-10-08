package main.java.DomainModel.Impianto;

public class Range<T extends Comparable<T>> {
    private T min;
    private T max;

    public Range(T min, T max) {
        this.min = min;
        this.max = max;
    }

    public T getMin() {
        return min;
    }

    public T getMax() {
        return max;
    }
}
