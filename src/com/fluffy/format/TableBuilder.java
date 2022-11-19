package com.fluffy.format;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class TableBuilder<T> {
    private static final Separator defaultSeparator = new Separator(' ');
    private final List<T> objects;
    private final List<TableColumn<T, ?>> tableColumns;
    private Separator horizontalSeparator;
    private Separator verticalSeparator;

    public TableBuilder() {
        tableColumns = new LinkedList<>();
        objects = new LinkedList<>();
        horizontalSeparator = defaultSeparator;
        verticalSeparator = defaultSeparator;
    }

    public TableBuilder<T> append(TableColumn<T, ?> tableColumn) {
        tableColumns.add(tableColumn);
        return this;
    }

    public TableBuilder<T> appendAll(Collection<TableColumn<T, ?>> tableColumns) {
        this.tableColumns.addAll(tableColumns);
        return this;
    }

    public TableBuilder<T> setObjects(Collection<T> objects) {
        this.objects.addAll(objects);
        return this;
    }

    public TableBuilder<T> setHorizontalSeparator(Separator separator) {
        horizontalSeparator = separator;
        return this;
    }

    public TableBuilder<T> setVerticalSeparator(Separator separator) {
        verticalSeparator = separator;
        return this;
    }

    public Table<T> build() {
        return new Table<>(objects, tableColumns, horizontalSeparator, verticalSeparator);
    }
}
