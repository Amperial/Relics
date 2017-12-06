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

import com.google.gson.JsonObject;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonWriter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

/**
 * Utilities to interact with json.
 *
 * @author Austin Payne
 */
public final class JsonUtil {

    private JsonUtil() {
    }

    /**
     * Creates an input stream from the given json object.
     *
     * @param json the json object
     * @return the input stream
     */
    public static InputStream toStream(JsonObject json) throws IOException {
        StringWriter stringWriter = new StringWriter();

        // Write json to string
        JsonWriter jsonWriter = new JsonWriter(stringWriter);
        jsonWriter.setLenient(true);
        jsonWriter.setIndent("    ");
        Streams.write(json, jsonWriter);

        // Create input stream from string bytes
        return new ByteArrayInputStream(stringWriter.toString().getBytes());
    }

}
