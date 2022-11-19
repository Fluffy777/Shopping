package com.fluffy.format;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.IntStream;

public class Table<T> {
    private final Collection<T> objects;
    private final Collection<TableColumn<T, ?>> tableColumns;
    private final Separator horizontalSeparator;
    private final Separator verticalSeparator;

    public Table(Collection<T> objects,
                 Collection<TableColumn<T, ?>> tableColumns,
                 Separator horizontalSeparator,
                 Separator verticalSeparator) {
        this.objects = objects;
        this.tableColumns = tableColumns;
        this.horizontalSeparator = horizontalSeparator;
        this.verticalSeparator = verticalSeparator;
    }

    private <E, V> int evaluateTableColumnWidth(TableColumn<T, V> tableColumn) {
        int width = tableColumn.getName().length();
        Function<T, V> accessor = tableColumn.getAccessor();
        Function<V, String> formatter = tableColumn.getFormatter();
        for (T object : objects) {
            width = Math.max(width, formatter.apply(accessor.apply(object)).length());
        }

        BiFunction<V, V, V> aggregationFunction = tableColumn.getAggregationFunction();
        if (aggregationFunction != null) {
            V aggregatedValue = null;
            for (T object : objects) {
                aggregatedValue = aggregationFunction.apply(accessor.apply(object), aggregatedValue);
            }

            Function<V, String> aggregationFormatter = tableColumn.getAggregationFormatter();
            if (aggregationFormatter != null) {
                width = Math.max(width, aggregationFormatter.apply(aggregatedValue).length());
            }
        }
        return width;
    }

    private String getHorizontalSeparatorString(int width) {
        StringBuilder representation = new StringBuilder();
        char style = horizontalSeparator.getStyle();
        for (int i = 0; i < width; i++) {
            representation.append(style);
        }
        representation.append(System.lineSeparator());
        return representation.toString();
    }

    @SuppressWarnings("rawtypes")
    private void drawTableRow(StringBuilder representation, int[] widths, Function<TableColumn, String> renderFunction) {
        Iterator tableColumnIterator = tableColumns.iterator();
        TableColumn tableColumn;
        int index = 0;
        while (tableColumnIterator.hasNext()) {
            tableColumn = (TableColumn) tableColumnIterator.next();
            representation.append(tableColumn.getTextAlignment().apply(renderFunction.apply(tableColumn), widths[index++]));
            if (tableColumnIterator.hasNext()) {
                representation.append(verticalSeparator.getStyle());
            }
        }
        representation.append(System.lineSeparator());
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public String toString() {
        int[] widths = tableColumns.stream().map(this::evaluateTableColumnWidth).mapToInt(i -> i).toArray();
        int totalWidth = IntStream.of(widths).sum() + tableColumns.size() - 1;
        StringBuilder representation = new StringBuilder();
        String horizontalSeparatorString = getHorizontalSeparatorString(totalWidth);

        drawTableRow(representation, widths, TableColumn::getName);
        representation.append(horizontalSeparatorString);

        for (T object : objects) {
            drawTableRow(representation, widths, tableColumn -> (String) tableColumn.getFormatter().apply(tableColumn.getAccessor().apply(object)));
        }
        representation.append(horizontalSeparatorString);

        drawTableRow(representation, widths, tableColumn -> {
            Function accessor = tableColumn.getAccessor();
            BiFunction aggregationFunction = tableColumn.getAggregationFunction();
            Object aggregatedValue = null;
            if (aggregationFunction != null) {
                for (Object object : objects) {
                    aggregatedValue = aggregationFunction.apply(accessor.apply(object), aggregatedValue);
                }
                return (String) tableColumn.getAggregationFormatter().apply(aggregatedValue);
            } else {
                return "";
            }
        });

        int lineSeparatorLength = System.lineSeparator().length();
        representation.delete(representation.length() - lineSeparatorLength, representation.length() + lineSeparatorLength);

        return representation.toString();
    }
}
