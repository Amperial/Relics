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
import java.util.Objects;
import java.util.Set;

/**
 * Utilities to define and use inventory dimensions, slots, and positions.
 *
 * @author Austin Payne
 */
public final class InventoryUtil {

    private InventoryUtil() {
    }

    /**
     * Gets the dimensions of a chest with a certain amount of rows.
     *
     * @param rows the amount of rows
     * @return the chest dimensions
     */
    public static Dimensions getChestDimensions(int rows) {
        return new Dimensions(9, rows);
    }

    /**
     * Represents a rectangular inventory of specific width and height.
     */
    public static class Dimensions {
        public static final Dimensions SINGLE_CHEST = getChestDimensions(3);
        public static final Dimensions DOUBLE_CHEST = getChestDimensions(6);

        private int width;
        private int height;

        public Dimensions(int width, int height) {
            this.width = width < 1 ? 1 : width;
            this.height = height < 1 ? 1 : height;
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
         * Gets the dimension's amount of columns.
         *
         * @return the dimension's width
         */
        public int getWidth() {
            return width;
        }

        /**
         * Gets the dimension's maximum amount of positions.
         *
         * @return the dimension's size
         */
        public int size() {
            return height * width;
        }

        /**
         * Gets the set of all positions within the dimensions.
         *
         * @return all of the dimension's positions
         */
        public Set<Position> getAllPositions(Position offset) {
            Set<Position> positions = new HashSet<>();
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    positions.add(new Position(x, y).add(offset));
                }
            }
            return positions;
        }

        /**
         * Checks if an inventory of these dimensions contains a given slot.
         *
         * @param slot the slot
         * @return {@code true} if the slot is within the dimensions, else {@code false}
         */
        public boolean contains(Slot slot) {
            return slot.index < width * height;
        }

        /**
         * Checks if an inventory of these dimensions contains a given position.
         *
         * @param position the position
         * @return {@code true} if the position is within the dimensions, else {@code false}
         */
        public boolean contains(Position position) {
            return position.x < width && position.y < height;
        }

        /**
         * Checks if the given dimensions fit within these dimensions.
         *
         * @param dimensions the dimensions
         * @return {@code true} if these dimensions can contain the given dimensions, else {@code false}
         */
        public boolean contains(Dimensions dimensions) {
            return width >= dimensions.width && height >= dimensions.height;
        }

        /**
         * Expansion modes.
         */
        public enum Expansion {
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
        public boolean expand(Slot slot, Expansion mode, Dimensions max) {
            if (slot.index == max.size()) {
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
        public boolean expand(Position position, Expansion mode, Dimensions max) {
            if (!max.contains(position)) {
                return false;
            }

            while (!contains(position)) {
                expand(mode, max);
            }
            return true;
        }

        private void expand(Expansion mode, Dimensions max) {
            boolean expandWidth = width < max.width && (mode == Expansion.WIDE || mode == Expansion.SQUARE || height == max.height);
            boolean expandHeight = height < max.height && (mode == Expansion.TALL || mode == Expansion.SQUARE || width == max.width);
            if (expandHeight) {
                height++;
            }
            if (expandWidth) {
                width++;
            }
        }
    }

    /**
     * Represents an inventory slot x and y position.
     */
    public static class Position {
        public static final Position ZERO = new Position(0, 0);

        public int x;
        public int y;

        public Position(int x, int y) {
            this.x = x < 0 ? 0 : x;
            this.y = y < 0 ? 0 : y;
        }

        /**
         * Gets the result of adding a position to this position.
         *
         * @param position the position to add
         * @return the new position
         */
        public Position add(Position position) {
            return new Position(x + position.x, y + position.y);
        }

        /**
         * Gets the result of subtracting a position from this position.
         *
         * @param position the position to subtract
         * @return the new position
         */
        public Position subtract(Position position) {
            return new Position(x - position.x, y - position.y);
        }

        /**
         * Gets the slot of the position in an inventory of the given dimensions.
         *
         * @param dimensions the dimensions
         * @return the position's equivalent slot
         */
        public Slot getSlot(Dimensions dimensions) {
            return new Slot(y * dimensions.width + x);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Position)) return false;
            Position position = (Position) o;
            return x == position.x &&
                    y == position.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
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
            return new Position(index % dimensions.width, index / dimensions.width);
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
            Position position = getPosition(from);
            position.x += fromToOffset.x;
            position.y += fromToOffset.y;
            return position.getSlot(to);
        }
    }

}
