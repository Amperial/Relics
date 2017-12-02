/*
 * This file is part of Relics API.
 *
 * Copyright (c) 2017, Austin Payne <amperialdev@gmail.com - http://github.com/Amperial>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics API,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.api.message;

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
     * @param name     the name of the page list
     * @param pageSize the number of strings that should be displayed per page
     * @param lines    the lines to be initially added to the page list
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
     * @return the amount of pages
     */
    public int getTotalPages() {
        return pages.size();
    }

    /**
     * Gets the lines of a specified page in the page list.
     *
     * @param pageNumber the page number of the lines
     * @return the lines of the specified page
     */
    public List<String> getPage(int pageNumber) {
        pageNumber = Math.max(1, Math.min(pageNumber, pages.size()));
        return pages.get(pageNumber - 1);
    }

    /**
     * Gets the lines of the last page in the page list.
     *
     * @return the lines of the last page
     */
    public List<String> getLastPage() {
        return pages.get(pages.size() - 1);
    }

    /**
     * Gets the line at the specified location in the page list.
     *
     * @param pageNumber the page number of the line
     * @param lineNumber the line number of the line
     * @return the line at the specified location
     */
    public String getLine(int pageNumber, int lineNumber) {
        List<String> page = getPage(pageNumber);
        lineNumber = Math.max(1, Math.min(lineNumber, page.size() - 1));
        return page.get(lineNumber);
    }

    /**
     * Adds one or more lines to the page list.
     *
     * @param lines the lines to add
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
     * @param pageNumber the string
     * @return the page number
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
