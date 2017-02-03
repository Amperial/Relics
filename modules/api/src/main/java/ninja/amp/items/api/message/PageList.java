/*
 * This file is part of AmpItems API.
 *
 * Copyright (c) 2017 <http://github.com/ampayne2/AmpItems//>
 *
 * AmpItems API is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AmpItems API is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with AmpItems API.  If not, see <http://www.gnu.org/licenses/>.
 */
package ninja.amp.items.api.message;

import java.util.ArrayList;
import java.util.List;

/**
 * Organizes a list of strings into multiple pages.
 *
 * @author Austin Payne
 */
public class PageList {

    private final String name;
    private final int pageSize;
    private List<List<String>> pages;

    /**
     * Creates a new page list.
     *
     * @param name     The name of the page list
     * @param pageSize The number of strings that should be displayed per page
     * @param lines    The lines to be initially added to the page list
     */
    public PageList(String name, int pageSize, String... lines) {
        this.name = name;
        this.pageSize = pageSize;
        this.pages = new ArrayList<>();

        pages.add(new ArrayList<>());
        pages.get(0).add(getHeader(1));
        add(lines);
    }

    /**
     * Gets the amount of pages in the page list.
     *
     * @return The amount of pages
     */
    public int getTotalPages() {
        return pages.size();
    }

    /**
     * Gets the lines of a specified page in the page list.
     *
     * @param pageNumber The page number of the lines
     * @return The lines of the specified page
     */
    public List<String> getPage(int pageNumber) {
        pageNumber = Math.max(1, Math.min(pageNumber, pages.size()));
        return pages.get(pageNumber - 1);
    }

    /**
     * Gets the lines of the last page in the page list.
     *
     * @return The lines of the last page
     */
    public List<String> getLastPage() {
        return pages.get(pages.size() - 1);
    }

    /**
     * Gets the line at the specified location in the page list.
     *
     * @param pageNumber The page number of the line
     * @param lineNumber The line number of the line
     * @return The line at the specified location
     */
    public String getLine(int pageNumber, int lineNumber) {
        List<String> page = getPage(pageNumber);
        lineNumber = Math.max(1, Math.min(lineNumber, page.size() - 1));
        return page.get(lineNumber);
    }

    /**
     * Adds one or more lines to the page list.
     *
     * @param lines The lines to add
     */
    public void add(String... lines) {
        for (String line : lines) {
            if (getLastPage().size() > pageSize) {
                pages.add(new ArrayList<>());
                getLastPage().add(getHeader(pages.size()));
                for (int i = 1; i < pages.size(); i++) {
                    pages.get(i - 1).set(0, getHeader(i));
                }
            }
            getLastPage().add(line);
        }
    }

    private String getHeader(int pageNumber) {
        return Messenger.HIGHLIGHT_COLOR + "<-------<| " + Messenger.PRIMARY_COLOR + name + ": Page " + pageNumber + "/" + pages.size() + " " + Messenger.HIGHLIGHT_COLOR + "|>------->";
    }

    /**
     * Gets the int page number from a string, 1 if not an int.
     *
     * @param pageNumber The string
     * @return The page number
     */
    public static int getPageNumber(String pageNumber) {
        int page;
        try {
            page = Integer.parseInt(pageNumber);
        } catch (NumberFormatException e) {
            page = 1;
        }
        return page;
    }

}
