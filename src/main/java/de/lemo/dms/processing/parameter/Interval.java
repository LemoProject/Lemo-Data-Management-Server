package de.lemo.dms.processing.parameter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A parameter whose argument must be contained in an interval.
 * 
 * @author Leonard Kappe
 * 
 * @param <T>
 *            Type of the argument. Must be comparable to {@link T}.
 */
@XmlRootElement
public class Interval<T extends Comparable<T>> extends MetaParam<T> {

    @XmlElement
    private T min;
    @XmlElement
    private T max;

    public static <T extends Comparable<T>> Interval<T> create(Class<T> type, String id, String name,
            String description, T min, T max, T defaultValue) {
        return new Interval<T>(type, id, name, description, min, max, defaultValue);
    }

    protected Interval() {
        /* JAXB no-arg default constructor */
    }

    protected Interval(Class<T> type, String id, String name, String description, T min, T max, T defaultValue) {
        super(type, id, name, description, defaultValue);
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean validate(T argument) {
        return argument.compareTo(min) >= 0 && argument.compareTo(max) <= 0;
    }

    public T getMin() {
        return min;
    }

    public T getMax() {
        return max;
    }

}
