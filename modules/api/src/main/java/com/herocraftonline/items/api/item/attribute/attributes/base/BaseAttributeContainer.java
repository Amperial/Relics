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
package com.herocraftonline.items.api.item.attribute.attributes.base;

import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.attribute.Attribute;
import com.herocraftonline.items.api.item.attribute.AttributeContainer;
import com.herocraftonline.items.api.item.attribute.AttributeType;

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
 * {@link #hasAttribute(Predicate)} and {@link #getAttribute(Class, Predicate)}
 * </p>
 * <p>
 * If {@link #getAttributesDeep(Class)} is not overriden:<br>
 * {@link #hasAttributeDeep(Predicate)} and {@link #getAttributeDeep(Class, Predicate)}
 * </p>
 * The goal is to avoid constructing new collections when a collection is already available to use,
 * when only a single item is needed, or when a collection is not needed, while working with nested attributes
 * whose data and implementations are unknown.
 *
 * @param <T> the type of attribute
 * @author Austin Payne
 */
public class BaseAttributeContainer<T extends Attribute<T>> extends BaseAttribute<T> implements AttributeContainer {

    public BaseAttributeContainer(Item item, String name, AttributeType<T> type) {
        super(item, name, type);
    }

    @Override
    public boolean hasAttribute(String name) {
        return hasAttribute(attribute -> attribute.getName().equals(name));
    }

    @Override
    public boolean hasAttribute(Class<? extends Attribute> type) {
        return hasAttribute(Attribute.predicate(type));
    }

    @Override
    public boolean hasAttribute(Predicate<Attribute> predicate) {
        return getAttributes().stream().anyMatch(predicate);
    }

    @Override
    public Optional<Attribute> getAttribute(String name) {
        return getAttribute(attribute -> attribute.getName().equals(name));
    }

    @Override
    public Optional<Attribute> getAttribute(Predicate<Attribute> predicate) {
        return getAttribute(Attribute.class, predicate);
    }

    @Override
    public <A extends Attribute> Optional<A> getAttribute(Class<A> type) {
        return getAttribute(type, attribute -> true);
    }

    @Override
    public <A extends Attribute> Optional<A> getAttribute(Class<A> type, String name) {
        return getAttribute(type, attribute -> attribute.getName().equals(name));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <A extends Attribute> Optional<A> getAttribute(Class<A> type, Predicate<A> predicate) {
        return getAttributes().stream()
                .filter(Attribute.predicate(type))
                .map(attribute -> (A) attribute)
                .filter(predicate).findFirst();
    }

    @Override
    public Collection<Attribute> getAttributes() {
        Collection<Attribute> attributes = new ArrayList<>();
        forEach(attributes::add);
        return attributes;
    }

    @Override
    public Collection<Attribute> getAttributes(Predicate<Attribute> predicate) {
        return getAttributes(Attribute.class, predicate);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <A extends Attribute> Collection<A> getAttributes(Class<A> type) {
        return getAttributes().stream()
                .filter(Attribute.predicate(type))
                .map(attribute -> (A) attribute)
                .collect(Collectors.toList());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <A extends Attribute> Collection<A> getAttributes(Class<A> type, Predicate<A> predicate) {
        Collection<A> attributes = new ArrayList<>();
        forEach(Attribute.predicate(type), attribute -> {
            if (predicate.test((A) attribute)) {
                attributes.add((A) attribute);
            }
        });
        return attributes;
    }

    @Override
    public void forEach(Consumer<Attribute> action) {
        getAttributes().forEach(action);
    }

    @Override
    public void forEach(Predicate<Attribute> predicate, Consumer<Attribute> action) {
        forEach(attribute -> {
            if (predicate.test(attribute)) {
                action.accept(attribute);
            }
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    public <A extends Attribute> void forEach(Class<A> type, Consumer<A> action) {
        Predicate<Attribute> predicate = Attribute.predicate(type);
        forEach(attribute -> {
            if (predicate.test(attribute)) {
                action.accept((A) attribute);
            }
        });
    }

    @Override
    public <A extends Attribute> void forEach(Class<A> type, Predicate<A> predicate, Consumer<A> action) {
        forEach(type, attribute -> {
            if (predicate.test(attribute)) {
                action.accept(attribute);
            }
        });
    }

    @Override
    public boolean hasAttributeDeep(String name) {
        return hasAttributeDeep(attribute -> attribute.getName().equals(name));
    }

    @Override
    public boolean hasAttributeDeep(Class<? extends Attribute> type) {
        return hasAttributeDeep(Attribute.predicate(type));
    }

    @Override
    public boolean hasAttributeDeep(Predicate<Attribute> predicate) {
        return getAttributesDeep().stream().anyMatch(predicate);
    }

    @Override
    public Optional<Attribute> getAttributeDeep(String name) {
        return getAttributeDeep(attribute -> attribute.getName().equals(name));
    }

    @Override
    public Optional<Attribute> getAttributeDeep(Predicate<Attribute> predicate) {
        return getAttributeDeep(Attribute.class, predicate);
    }

    @Override
    public <A extends Attribute> Optional<A> getAttributeDeep(Class<A> type) {
        return getAttributeDeep(type, attribute -> true);
    }

    @Override
    public <A extends Attribute> Optional<A> getAttributeDeep(Class<A> type, String name) {
        return getAttributeDeep(type, attribute -> attribute.getName().equals(name));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <A extends Attribute> Optional<A> getAttributeDeep(Class<A> type, Predicate<A> predicate) {
        return getAttributesDeep(type).stream().filter(predicate).findFirst();
    }

    @Override
    public Collection<Attribute> getAttributesDeep() {
        return getAttributesDeep(Attribute.class);
    }

    @Override
    public Collection<Attribute> getAttributesDeep(Predicate<Attribute> predicate) {
        return getAttributesDeep(Attribute.class, predicate);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <A extends Attribute> Collection<A> getAttributesDeep(Class<A> type) {
        Collection<A> attributes = new ArrayList<>();
        forEachDeep(Attribute.predicate(type), attribute -> attributes.add((A) attribute));
        return attributes;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <A extends Attribute> Collection<A> getAttributesDeep(Class<A> type, Predicate<A> predicate) {
        Collection<A> attributes = new ArrayList<>();
        forEachDeep(Attribute.predicate(type), attribute -> {
            if (predicate.test((A) attribute)) {
                attributes.add((A) attribute);
            }
        });
        return attributes;
    }

    @Override
    public void forEachDeep(Consumer<Attribute> action) {
        getAttributesDeep().forEach(action);
    }

    @Override
    public void forEachDeep(Predicate<Attribute> predicate, Consumer<Attribute> action) {
        forEachDeep(attribute -> {
            if (predicate.test(attribute)) {
                action.accept(attribute);
            }
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    public <A extends Attribute> void forEachDeep(Class<A> type, Consumer<A> action) {
        Predicate<Attribute> predicate = Attribute.predicate(type);
        forEachDeep(attribute -> {
            if (predicate.test(attribute)) {
                action.accept((A) attribute);
            }
        });
    }

    @Override
    public <A extends Attribute> void forEachDeep(Class<A> type, Predicate<A> predicate, Consumer<A> action) {
        forEachDeep(type, attribute -> {
            if (predicate.test(attribute)) {
                action.accept(attribute);
            }
        });
    }

}
