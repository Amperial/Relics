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
package com.herocraftonline.items.api.item.attribute.attributes;

import com.herocraftonline.items.api.item.attribute.ItemAttribute;

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

    boolean hasAttribute(Class<?> clazz);

    boolean hasAttributeDeep(Class<?> clazz);

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

    <T extends ItemAttribute> Optional<T> getAttribute(Class<T> clazz);

    <T extends ItemAttribute> Optional<T> getAttribute(String name, Class<T> clazz);

    <T extends ItemAttribute> Optional<T> getAttributeDeep(Class<T> clazz);

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
