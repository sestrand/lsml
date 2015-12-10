/*
 * @formatter:off
 * Li Song Mechlab - A 'mech building tool for PGI's MechWarrior: Online.
 * Copyright (C) 2013  Li Song
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
//@formatter:on
package org.lisoft.lsml.view_fx.loadout.component;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

/**
 * This control displays a fixed number of rows with equal height where the cells can span multiple rows.
 * 
 * Any custom cell factory used with this list view must return cells of the type {@link Cell} or inheriting from it.
 * 
 * @author Li Song
 * @param <T>
 *            The type to show in the list.
 */
public class ItemView<T> extends ListView<T> {
    /**
     * A custom cell for {@link ItemView}. Makes sure the cells have the correct size.
     * 
     * @author Li Song
     *
     * @param <T>
     *            The type contained in the this cell is for {@link ItemView}.
     */
    public static class Cell<T> extends ListCell<T> {
        public static final int              DEFAULT_SIZE = 1;
        protected final IntegerProperty      rowSpan      = new SimpleIntegerProperty(DEFAULT_SIZE);
        private final ReadOnlyDoubleProperty baseHeight;

        public Cell(ItemView<T> aItemView) {
            baseHeight = aItemView.rowHeight;
            prefHeightProperty().bind(rowSpan.multiply(baseHeight));

            minHeightProperty().bind(prefHeightProperty());
            maxHeightProperty().bind(prefHeightProperty());
        }

        /**
         * @return The size in rows of this cell. By default 1.
         */
        public IntegerProperty rowSpanProperty() {
            return rowSpan;
        }

        /**
         * Sets the {@link #rowSpanProperty()} to the given value.
         * 
         * @param aRows
         *            A new size.
         */
        public void setRowSpan(int aRows) {
            if (!(aRows > 0)) {
                throw new IllegalArgumentException("Size must be larger than 0");
            }
            rowSpan.set(aRows);
        }

        /**
         * @return The current value of the {@link #rowSpanProperty()}.
         */
        public int getRowSpan() {
            return rowSpan.get();
        }
    }

    public static final double    DEFAULT_HEIGHT = 25.0;
    private static final int      DEFAULT_ROWS   = 6;
    private final DoubleProperty  rowHeight      = new SimpleDoubleProperty(DEFAULT_HEIGHT);
    private final IntegerProperty rows           = new SimpleIntegerProperty(DEFAULT_ROWS);

    public ItemView() {
        setCellFactory((ListView<T> aList) -> {
            return new Cell<T>((ItemView<T>) aList);
        });

        prefHeightProperty().bind(rowHeight.multiply(rows).add(2.0));
        maxHeightProperty().bind(prefHeightProperty());
        minHeightProperty().bind(prefHeightProperty());
    }

    public int getVisibleRows() {
        return rows.get();
    }

    public void setVisibleRows(int aNewValue) {
        rows.set(aNewValue);
    }

    /**
     * @return The property for the number of rows that should be shown.
     */
    public IntegerProperty visibleRowsProperty() {
        return rows;
    }

    public double getRowHeight() {
        return rowHeight.get();
    }

    public void setRowHeight(double aNewValue) {
        rowHeight.set(aNewValue);
    }

    public DoubleProperty rowHeightProperty() {
        return rowHeight;
    }
}