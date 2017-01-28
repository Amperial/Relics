/*
 * This file is part of AmpItems.
 *
 * Copyright (c) 2017 <http://github.com/ampayne2/AmpItems//>
 *
 * AmpItems is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AmpItems is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with AmpItems.  If not, see <http://www.gnu.org/licenses/>.
 */
package ninja.amp.items.message;

/**
 * Messages sent by the amp items plugin.
 *
 * @author Austin Payne
 */
public enum AIMessage implements Message {
    PREFIX("Prefix", "&8[&bAmpItems&8] &7"),
    PREFIX_ERROR("ErrorPrefix", "&8[&bAmpItems&8] &4"),
    RELOAD("Reload", "Reloaded %s."),

    COMMAND_NOTAPLAYER("Command.NotAPlayer", "You must be a player to use this command."),
    COMMAND_NOPERMISSION("Command.NoPermission", "You do not have permission to use this command."),
    COMMAND_INVALID("Command.Invalid", "Unknown commad. Type &b/aitem help&4 for help."),
    COMMAND_USAGE("Command.Usage", "Usage: %s");

    private final String path;
    private final String defaultMessage;
    private String message;

    AIMessage(String path, String defaultMessage) {
        this.message = defaultMessage;
        this.path = path;
        this.defaultMessage = defaultMessage;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public String getDefault() {
        return defaultMessage;
    }

    @Override
    public String toString() {
        return message;
    }

}
