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
package ninja.amp.items.api.item.attribute.attributes;

import ninja.amp.items.api.item.attribute.ItemAttribute;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Contains various methods to simplify working with attributes that could be nested in many different ways.
 *
 * @author Austin Payne
 */
public interface AttributeContainer {

    boolean hasAttribute(String name);

    boolean hasAttributeDeep(String name);

    boolean hasAttribute(Predicate<ItemAttribute> predicate);

    boolean hasAttributeDeep(Predicate<ItemAttribute> predicate);

    Optional<ItemAttribute> getAttribute(String name);

    Optional<ItemAttribute> getAttributeDeep(String name);

    Optional<ItemAttribute> getAttribute(Predicate<ItemAttribute> predicate);

    Optional<ItemAttribute> getAttributeDeep(Predicate<ItemAttribute> predicate);

    Collection<ItemAttribute> getAttributes();

    Collection<ItemAttribute> getAttributesDeep();

    Collection<ItemAttribute> getAttributes(Predicate<ItemAttribute> predicate);

    Collection<ItemAttribute> getAttributesDeep(Predicate<ItemAttribute> predicate);

    <T extends ItemAttribute> Optional<T> getAttribute(String name, Class<T> clazz);

    <T extends ItemAttribute> Optional<T> getAttributeDeep(String name, Class<T> clazz);

    <T extends ItemAttribute> Optional<T> getAttribute(Predicate<T> predicate, Class<T> clazz);

    <T extends ItemAttribute> Optional<T> getAttributeDeep(Predicate<T> predicate, Class<T> clazz);

    <T extends ItemAttribute> Collection<T> getAttributes(Class<T> clazz);

    <T extends ItemAttribute> Collection<T> getAttributesDeep(Class<T> clazz);

    <T extends ItemAttribute> Collection<T> getAttributes(Predicate<T> predicate, Class<T> clazz);

    <T extends ItemAttribute> Collection<T> getAttributesDeep(Predicate<T> predicate, Class<T> clazz);

    void forEach(Consumer<ItemAttribute> action);

    void forEachDeep(Consumer<ItemAttribute> action);

    void forEach(Consumer<ItemAttribute> action, Predicate<ItemAttribute> predicate);

    void forEachDeep(Consumer<ItemAttribute> action, Predicate<ItemAttribute> predicate);

}
