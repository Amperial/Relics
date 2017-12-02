/*
 * This file is part of Relics.
 *
 * Copyright (c) 2017, Austin Payne <amperialdev@gmail.com - http://github.com/Amperial>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * Utilities to interact with zip files.
 *
 * @author Austin Payne
 */
public final class ZipUtil {

    /**
     * Size of the byte buffer used to write data to a zip output stream.
     */
    private static final int BUFFER_SIZE = 1024;

    private ZipUtil() {
    }

    /**
     * Gets an input stream from an entry at the given path in either the input or default zip file.
     *
     * @param input the input zip file
     * @param def   the default zip file
     * @param path  the entry path
     * @return the entry input stream
     */
    public static InputStream getEntryStream(ZipFile input, ZipFile def, String path) throws IOException {
        ZipEntry e = input.getEntry(path);
        return e == null ? ((e = def.getEntry(path)) == null ? null : def.getInputStream(e)) : input.getInputStream(e);
    }

    /**
     * Writes data from the given input stream to a zip entry in the output zip.
     *
     * @param output the output zip stream
     * @param input  the input zip stream
     * @param entry  the zip entry
     */
    public static void writeZipEntry(ZipOutputStream output, InputStream input, ZipEntry entry) throws IOException {
        output.putNextEntry(entry);
        byte[] buf = new byte[BUFFER_SIZE];
        int len;
        while ((len = input.read(buf)) > 0) {
            output.write(buf, 0, len);
        }
        output.closeEntry();
    }

    /**
     * Writes data from the given input stream to a new zip entry in the output zip.
     *
     * @param output the output zip stream
     * @param input  the input zip stream
     * @param path   the zip entry destination path
     */
    public static void newZipEntry(ZipOutputStream output, InputStream input, String path) throws IOException {
        writeZipEntry(output, input, new ZipEntry(path));
    }

    /**
     * Writes the given zip entry to the output zip, copying from the given input zip.
     *
     * @param output the output zip stream
     * @param input  the input zip file
     * @param entry  the zip entry to copy
     */
    public static void copyZipEntry(ZipOutputStream output, ZipFile input, ZipEntry entry) throws IOException {
        writeZipEntry(output, input.getInputStream(entry), new ZipEntry(entry.getName()));
    }

    /**
     * Copies all zip entries from the given input zip to the output zip that haven't already been written.
     *
     * @param output  the output zip stream
     * @param input   the input zip file
     * @param entries the entries that have already been written
     */
    public static void copyZipEntries(ZipOutputStream output, ZipFile input, Set<String> entries) throws IOException {
        Enumeration<? extends ZipEntry> e = input.entries();
        while (e.hasMoreElements()) {
            ZipEntry entry = e.nextElement();
            if (!entries.contains(entry.getName())) {
                copyZipEntry(output, input, entry);
            }
        }
    }

}
