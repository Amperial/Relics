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

import com.herocraftonline.items.api.item.attribute.AttributeContainer;
import com.herocraftonline.items.api.item.attribute.AttributeType;
import com.herocraftonline.items.api.item.attribute.ItemAttribute;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * An implementation of AttributeContainer to help subclasses avoid implementing the full interface.<br>
 * Subclasses are required to override the following methods:
 * <p>
 * {@link #getAttributes()} or {@link #forEach(Consumer)},<br>
 * {@link #getAttributesDeep(Class)} or {@link #forEachDeep(Consumer)}
 * </p>
 * In addition, they are encouraged to override any methods that could achieve increased performance.<br>
 * The following methods are the most likely to achieve a higher performance if overriden:
 * <p>
 * Even if {@link #forEach(Consumer)} is overriden:<br>
 * {@link #getAttributes()}
 * </p>
 * <p>
 * Even if {@link #getAttributesDeep(Class)} is overriden:<br>
 * {@link #forEachDeep(Consumer)}
 * </p>
 * <p>
 * If {@link #getAttributes()} is not overriden:<br>
 * {@link #hasAttribute(Predicate)} and {@link #getAttribute(Predicate, Class)}
 * </p>
 * <p>
 * If {@link #getAttributesDeep(Class)} is not overriden:<br>
 * {@link #hasAttributeDeep(Predicate)} and {@link #getAttributeDeep(Predicate, Class)}
 * </p>
 * The goal is to avoid constructing new collections when a collection is already available to use,
 * when only a single item is needed, or when a collection is not needed, while working with nested attributes
 * whose data and implementations are unknown.
 *
 * @author Austin Payne
 */
public class BasicAttributeContainer extends BasicAttribute implements AttributeContainer {

    public BasicAttributeContainer(String name, AttributeType type) {
        super(name, type);
    }

    @Override
    public boolean hasAttribute(Class<?> clazz) {
        return hasAttribute(ItemAttribute.type(clazz));
    }

    @Override
    public boolean hasAttributeDeep(Class<?> clazz) {
        return hasAttributeDeep(ItemAttribute.type(clazz));
    }

    @Override
    public boolean hasAttribute(String name) {
        return hasAttribute(attribute -> attribute.getName().equals(name));
    }

    @Override
    public boolean hasAttributeDeep(String name) {
        return hasAttributeDeep(attribute -> attribute.getName().equals(name));
    }

    @Override
    public boolean hasAttribute(Predicate<ItemAttribute> predicate) {
        return getAttributes().stream().anyMatch(predicate);
    }

    @Override
    public boolean hasAttributeDeep(Predicate<ItemAttribute> predicate) {
        return getAttributesDeep().stream().anyMatch(predicate);
    }

    @Override
    public Optional<ItemAttribute> getAttribute(String name) {
        return getAttribute(attribute -> attribute.getName().equals(name));
    }

    @Override
    public Optional<ItemAttribute> getAttributeDeep(String name) {
        return getAttributeDeep(attribute -> attribute.getName().equals(name));
    }

    @Override
    public Optional<ItemAttribute> getAttribute(Predicate<ItemAttribute> predicate) {
        return getAttribute(predicate, ItemAttribute.class);
    }

    @Override
    public Optional<ItemAttribute> getAttributeDeep(Predicate<ItemAttribute> predicate) {
        return getAttributeDeep(predicate, ItemAttribute.class);
    }

    @Override
    public Collection<ItemAttribute> getAttributes() {
        Collection<ItemAttribute> attributes = new ArrayList<>();
        forEach(attributes::add);
        return attributes;
    }

    @Override
    public Collection<ItemAttribute> getAttributesDeep() {
        return getAttributesDeep(ItemAttribute.class);
    }

    @Override
    public Collection<ItemAttribute> getAttributes(Predicate<ItemAttribute> predicate) {
        return getAttributes(predicate, ItemAttribute.class);
    }

    @Override
    public Collection<ItemAttribute> getAttributesDeep(Predicate<ItemAttribute> predicate) {
        return getAttributesDeep(predicate, ItemAttribute.class);
    }

    @Override
    public <T extends ItemAttribute> Optional<T> getAttribute(Class<T> clazz) {
        return getAttribute(attribute -> true, clazz);
    }

    @Override
    public <T extends ItemAttribute> Optional<T> getAttribute(String name, Class<T> clazz) {
        return getAttribute(attribute -> attribute.getName().equals(name), clazz);
    }

    @Override
    public <T extends ItemAttribute> Optional<T> getAttributeDeep(Class<T> clazz) {
        return getAttributeDeep(attribute -> true, clazz);
    }

    @Override
    public <T extends ItemAttribute> Optional<T> getAttributeDeep(String name, Class<T> clazz) {
        return getAttributeDeep(attribute -> attribute.getName().equals(name), clazz);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends ItemAttribute> Optional<T> getAttribute(Predicate<T> predicate, Class<T> clazz) {
        return getAttributes().stream()
                .filter(ItemAttribute.type(clazz))
                .map(attribute -> (T) attribute)
                .filter(predicate).findFirst();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends ItemAttribute> Optional<T> getAttributeDeep(Predicate<T> predicate, Class<T> clazz) {
        return getAttributesDeep(clazz).stream().filter(predicate).findFirst();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends ItemAttribute> Collection<T> getAttributes(Class<T> clazz) {
        return getAttributes().stream()
                .filter(ItemAttribute.type(clazz))
                .map(attribute -> (T) attribute)
                .collect(Collectors.toList());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends ItemAttribute> Collection<T> getAttributesDeep(Class<T> clazz) {
        Collection<T> attributes = new ArrayList<>();
        forEachDeep(attribute -> attributes.add((T) attribute), ItemAttribute.type(clazz));
        return attributes;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends ItemAttribute> Collection<T> getAttributes(Predicate<T> predicate, Class<T> clazz) {
        Collection<T> attributes = new ArrayList<>();
        forEach(attribute -> {
            if (predicate.test((T) attribute)) {
                attributes.add((T) attribute);
            }
        }, ItemAttribute.type(clazz));
        return attributes;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends ItemAttribute> Collection<T> getAttributesDeep(Predicate<T> predicate, Class<T> clazz) {
        Collection<T> attributes = new ArrayList<>();
        forEachDeep(attribute -> {
            if (predicate.test((T) attribute)) {
                attributes.add((T) attribute);
            }
        }, ItemAttribute.type(clazz));
        return attributes;
    }

    @Override
    public void forEach(Consumer<ItemAttribute> action) {
        getAttributes().forEach(action);
    }

    @Override
    public void forEachDeep(Consumer<ItemAttribute> action) {
        getAttributesDeep().forEach(action);
    }

    @Override
    public void forEach(Consumer<ItemAttribute> action, Predicate<ItemAttribute> predicate) {
        forEach(attribute -> {
            if (predicate.test(attribute)) {
                action.accept(attribute);
            }
        });
    }

    @Override
    public void forEachDeep(Consumer<ItemAttribute> action, Predicate<ItemAttribute> predicate) {
        forEachDeep(attribute -> {
            if (predicate.test(attribute)) {
                action.accept(attribute);
            }
        });
    }

}
