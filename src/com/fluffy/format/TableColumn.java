package com.fluffy.format;

import java.util.function.BiFunction;
import java.util.function.Function;

public class TableColumn<T, V> {
    private final String name;
    private final TextAlignment textAlignment;
    private final Function<T, V> accessor;
    private final BiFunction<V, V, V> aggregationFunction;
    private final Function<V, String> formatter;
    private final Function<V, String> aggregationFormatter;

    public TableColumn(String name,
                       TextAlignment textAlignment,
                       Function<T, V> accessor,
                       BiFunction<V, V, V> aggregationFunction,
                       Function<V, String> formatter) {
        this(name, textAlignment, accessor, aggregationFunction, formatter, formatter);
    }

    public TableColumn(String name,
                       TextAlignment textAlignment,
                       Function<T, V> accessor,
                       BiFunction<V, V, V> aggregationFunction,
                       Function<V, String> formatter,
                       Function<V, String> aggregationFormatter) {
        this.name = name;
        this.textAlignment = textAlignment;
        this.accessor = accessor;
        this.aggregationFunction = aggregationFunction;
        this.formatter = formatter;
        this.aggregationFormatter = aggregationFormatter;
    }

    public String getName() {
        return name;
    }

    public TextAlignment getTextAlignment() {
        return textAlignment;
    }

    public Function<T, V> getAccessor() {
        return accessor;
    }

    public BiFunction<V, V, V> getAggregationFunction() {
        return aggregationFunction;
    }

    public Function<V, String> getFormatter() {
        return formatter;
    }

    public Function<V, String> getAggregationFormatter() {
        return aggregationFormatter;
    }
}
