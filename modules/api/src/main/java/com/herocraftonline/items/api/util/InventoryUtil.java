/*
 * This file is part of Relics API.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics API,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.api.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.function.BinaryOperator;

/**
 * Utilities to define and use inventory dimensions, slots, and positions.
 *
 * @author Austin Payne
 */
public final class InventoryUtil {

    private InventoryUtil() {
    }

    /**
     * Represents a rectangular inventory of specific width and height.
     */
    public static class Dimensions implements Iterable<Position> {
        public static final Dimensions SINGLE_CHEST = ofChest(3);
        public static final Dimensions DOUBLE_CHEST = ofChest(6);

        private int width;
        private int height;

        public Dimensions(int width, int height) {
            this.width = width < 1 ? 1 : width;
            this.height = height < 1 ? 1 : height;
        }

        /**
         * Gets the dimension's amount of columns.
         *
         * @return the dimension's width
         */
        public int getWidth() {
            return width;
        }

        /**
         * Gets the dimension's amount of rows.
         *
         * @return the dimension's height
         */
        public int getHeight() {
            return height;
        }

        /**
         * Gets the dimension's maximum amount of positions.
         *
         * @return the dimension's size
         */
        public int size() {
            return getWidth() * getHeight();
        }

        /**
         * Gets the set of all positions within the dimensions.
         *
         * @return all of the dimension's positions
         */
        public Set<Position> getPositions(Position offset) {
            Set<Position> positions = new HashSet<>();
            for (int x = 0; x < getWidth(); x++) {
                for (int y = 0; y < getHeight(); y++) {
                    positions.add(new Position(x, y).add(offset));
                }
            }
            return positions;
        }

        @Override
        public Iterator<Position> iterator() {
            return getPositions(Position.ZERO).iterator();
        }

        /**
         * Checks if an inventory of these dimensions contains a given slot.
         *
         * @param slot the slot
         * @return {@code true} if the slot is within the dimensions, else {@code false}
         */
        public boolean contains(Slot slot) {
            return slot.getIndex() < size();
        }

        /**
         * Checks if an inventory of these dimensions contains a given position.
         *
         * @param position the position
         * @return {@code true} if the position is within the dimensions, else {@code false}
         */
        public boolean contains(Position position) {
            return position.getX() < getWidth() && position.getY() < getHeight();
        }

        /**
         * Checks if the given dimensions fit within these dimensions.
         *
         * @param dimensions the dimensions
         * @return {@code true} if these dimensions can contain the given dimensions, else {@code false}
         */
        public boolean contains(Dimensions dimensions) {
            return getWidth() >= dimensions.getWidth() && getHeight() >= dimensions.getHeight();
        }

        /**
         * Expansion modes.
         */
        public enum ExpandMode {
            WIDE,
            TALL,
            SQUARE
        }

        /**
         * Expands the dimensions to be able to contain the given slot.
         *
         * @param slot the slot to contain
         * @param mode the expansion mode
         * @param max  the maximum dimensions
         * @return {@code true} if the dimensions were successfully expanded, else {@code false}
         */
        public boolean expand(Slot slot, ExpandMode mode, Dimensions max) {
            if (slot.getIndex() == max.size()) {
                return false;
            }

            while (!contains(slot)) {
                expand(mode, max);
            }
            return true;
        }

        /**
         * Expands the dimensions to be able to contain the given position.
         *
         * @param position the position to contain
         * @param mode     the expansion mode to follow
         * @param max      the maximum dimensions
         * @return {@code true} if the dimensions were successfully expanded, else {@code false}
         */
        public boolean expand(Position position, ExpandMode mode, Dimensions max) {
            if (!max.contains(position)) {
                return false;
            }

            while (!contains(position)) {
                expand(mode, max);
            }
            return true;
        }

        private void expand(ExpandMode mode, Dimensions max) {
            boolean expandWidth = getWidth() < max.getWidth() && (mode == ExpandMode.WIDE || mode == ExpandMode.SQUARE || getHeight() == max.getHeight());
            boolean expandHeight = getHeight() < max.getHeight() && (mode == ExpandMode.TALL || mode == ExpandMode.SQUARE || getWidth() == max.getWidth());
            if (expandHeight) {
                height++;
            }
            if (expandWidth) {
                width++;
            }
        }

        /**
         * Gets the dimensions of a chest with a certain amount of rows.
         *
         * @param rows the amount of rows
         * @return the chest dimensions
         */
        public static Dimensions ofChest(int rows) {
            return new Dimensions(9, rows);
        }
    }

    /**
     * Represents an inventory slot x and y position.
     */
    public static class Position {
        public static final Position ZERO = new Position(0, 0);
        public static final BinaryOperator<Position> MIN = (p1, p2) ->
                new Position(Integer.min(p1.getX(), p2.getX()), Integer.min(p1.getY(), p2.getY()));

        private int x;
        private int y;

        public Position(int x, int y) {
            this.x = x < 0 ? 0 : x;
            this.y = y < 0 ? 0 : y;
        }

        /**
         * Gets the x value of the position.
         *
         * @return the position's x value
         */
        public int getX() {
            return x;
        }

        /**
         * Gets the y value of the position.
         *
         * @return the position's y value
         */
        public int getY() {
            return y;
        }

        /**
         * Gets the result of adding a position to this position.
         *
         * @param position the position to add
         * @return the new position
         */
        public Position add(Position position) {
            return new Position(getX() + position.getX(), getY() + position.getY());
        }

        /**
         * Gets the result of subtracting a position from this position.
         *
         * @param position the position to subtract
         * @return the new position
         */
        public Position subtract(Position position) {
            return new Position(getX() - position.getX(), getY() - position.getY());
        }

        /**
         * Gets the slot of the position in an inventory of the given dimensions.
         *
         * @param dimensions the dimensions
         * @return the position's equivalent slot
         */
        public Slot getSlot(Dimensions dimensions) {
            return new Slot(getY() * dimensions.getWidth() + getX());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Position)) return false;
            Position position = (Position) o;
            return getX() == position.getX() &&
                    getY() == position.getY();
        }

        @Override
        public int hashCode() {
            return Objects.hash(getX(), getY());
        }
    }

    /**
     * Represents an inventory slot index.
     */
    public static class Slot {
        private int index;

        public Slot(int index) {
            this.index = index < 0 ? 0 : index;
        }

        /**
         * Gets the index of the slot.
         *
         * @return the slot's index
         */
        public int getIndex() {
            return index;
        }

        /**
         * Gets the position of the slot in an inventory of the given dimensions.
         *
         * @param dimensions the dimensions
         * @return the slot's equivalent position
         */
        public Position getPosition(Dimensions dimensions) {
            return new Position(getIndex() % dimensions.getWidth(), getIndex() / dimensions.getWidth());
        }

        /**
         * Gets the equivalent slot when moving between two inventories.
         *
         * @param from         the initial inventory dimensions
         * @param to           the new inventory dimensions
         * @param fromToOffset the position offset between the inventories
         * @return the equivalent slot index in the new inventory
         */
        public Slot transform(Dimensions from, Dimensions to, Position fromToOffset) {
            return getPosition(from).add(fromToOffset).getSlot(to);
        }
    }

}
